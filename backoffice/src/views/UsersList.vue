<template>
    <div class="p-6 bg-app-background">
        <div class="mb-4 flex justify-between items-center">
            <H1>Users</H1>
            <!-- Search bar -->
            <span class="relative">
                <i class="pi pi-search absolute left-3 top-1/2 -translate-y-1/2 text-text-muted" />
                <InputText v-model="filters['global'].value"
                           placeholder="Search a player..."
                           class="pl-10 py-2 border border-app-border rounded-lg focus:ring-2 focus:ring-ring-focus" />
            </span>
        </div>

        <DataTable :value="isLoading ? skeletonUsers : users"
                   :loading="isLoading"
                   paginator
                   :rows="10"
                   data-key="discordId"
                   v-model:filters="filters"
                   filter-display="menu"
                   :global-filter-fields="['username']"
                   selection-mode="single"
                   @rowSelect="onRowClick"
                   class="border border-app-border rounded-lg overflow-hidden"
                   row-hover>

            <template #empty>No player found.</template>

            <!-- Username column -->
            <Column field="username" header="Player" sortable>
                <template #body="{ data }">
                    <div v-if="isLoading" class="flex items-center gap-2">
                        <Skeleton width="150px" height="1.25rem" />
                    </div>
                    <span v-else class="font-medium">
                        {{ data.username }}
                    </span>
                </template>
            </Column>

            <!-- UserType column with filter -->
            <Column field="userType" header="Role" :show-filter-match-modes="false">
                <template #body="{ data }">
                    <Skeleton v-if="isLoading" width="60px" height="1.5rem" border-radius="9999px" />
                    <span v-else class="px-2.5 py-1 text-xs font-semibold rounded-full"
                          :class="{
                              'bg-blue-100 text-blue-700': data.userType === 'USER',
                              'bg-purple-100 text-purple-700': data.userType === 'ADMIN'
                          }">
                        {{ data.userType }}
                    </span>
                </template>

                <template #filter="{ filterModel, filterCallback }">
                    <div class="flex flex-col gap-1">
                        <label for="user-role-filter" class="sr-only">Filter by role</label>
                        <Select id="user-role-filter"
                                input-id="user-role-filter"
                                v-model="filterModel.value"
                                @change="filterCallback()"
                                :options="[...USER_TYPES]"
                                placeholder="All roles"
                                class="p-column-filter" />
                    </div>
                </template>
            </Column>

        </DataTable>
    </div>
</template>

<script setup lang="ts">
import H1 from '#/components/H1.vue';
import { useAdminUserApi } from '#/composables/api/useAdminUserApi';
import { Route } from '#/router/Route';
import type { User } from '#/types/api/user';
import { FilterMatchMode } from '@primevue/core/api';
import { USER_TYPES } from '@tm-trialmaple/shared/types/api/user';
import Column from 'primevue/column';
import type { DataTableRowSelectEvent } from 'primevue/datatable';
import DataTable from 'primevue/datatable';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';
import Skeleton from 'primevue/skeleton';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const users = ref<User[]>([]);
const isLoading = ref(true);
const skeletonUsers = ref<unknown[]>(new Array(5).fill({}));

const filters = ref({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    userType: { value: null, matchMode: FilterMatchMode.EQUALS }
});

const adminUserApi = useAdminUserApi();
onMounted(async () => {
    isLoading.value = true;
    try {
        users.value = await adminUserApi.getAllUsers();
    } catch (error) {
        console.error('Error fetching users:', error);
    } finally {
        isLoading.value = false;
    }
});

const onRowClick = (event: DataTableRowSelectEvent) => {
    const selectedUser: User = event.data;
    router.push({
        name: Route.USER_DETAIL,
        params: { userId: selectedUser.discordId }
    });
};
</script>
