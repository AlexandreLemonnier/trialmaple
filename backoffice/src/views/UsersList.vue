<template>
    <!-- 1. h-screen, flex et flex-col sur le parent -->
    <div class="p-6 bg-app-background h-screen flex flex-col">

        <!-- 2. shrink-0 sur le header (et j'ai gardé votre mb-4) -->
        <div class="mb-4 flex justify-between items-center shrink-0">
            <H1>Users</H1>
            <!-- Search bar -->
            <span class="relative">
                <i class="pi pi-search absolute left-3 top-1/2 -translate-y-1/2 text-text-muted" />
                <InputText v-model="filters['global'].value"
                           placeholder="Search a player..."
                           class="pl-10 py-2 border border-app-border rounded-lg focus:ring-2 focus:ring-ring-focus" />
            </span>
        </div>

        <!-- 3. Wrapper flex-1 et min-h-0 avec vos classes de style -->
        <div class="flex-1 flex flex-col min-h-0 border border-app-border rounded-lg overflow-hidden">

            <!-- 4. Ajout de scrollable, scroll-height="flex" et class="flex-1" -->
            <DataTable :value="isLoading ? skeletonUsers : users"
                       scrollable
                       scroll-height="flex"
                       removable-sort
                       data-key="discordId"
                       v-model:filters="filters"
                       filter-display="menu"
                       :global-filter-fields="['username']"
                       selection-mode="single"
                       @rowSelect="onRowClick"
                       class="flex-1"
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
                        <RolePill v-else :user-type="data.userType" />
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
    </div>
</template>

<script setup lang="ts">
import H1 from '#/components/H1.vue';
import RolePill from '#/components/RolePill.vue';
import { useAdminUserApi } from '#/composables/api/useAdminUserApi';
import { useToast } from '#/composables/useToast';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
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

const toast = useToast();
const appStore = useAppStore();
const router = useRouter();
const users = ref<User[]>([]);
const isLoading = ref(true);
const skeletonUsers = ref<unknown[]>(new Array(10).fill({}));

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
        toast.add({ severity: 'error', summary: 'Error while fetching users', error });
    } finally {
        isLoading.value = false;
    }
});

const onRowClick = (event: DataTableRowSelectEvent) => {
    const selectedUser: User = event.data;
    appStore.currentSelectedUser = selectedUser;
    router.push({
        name: Route.USER_DETAIL,
        params: { userId: selectedUser.discordId }
    });
};
</script>
