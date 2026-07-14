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

        <DataTable :value="users"
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
                    <span class="font-medium">
                        {{ data.username }}
                        <span v-if="data.discriminator" class="text-sm text-text-muted/70">#{{ data.discriminator }}</span>
                    </span>
                </template>
            </Column>

            <!-- UserType column with filter -->
            <Column field="userType" header="Role" :show-filter-match-modes="false">
                <template #body="{ data }">
                    <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
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
import type { User } from '#/types/api/user';
import { FilterMatchMode } from '@primevue/core/api';
import { USER_TYPES } from '@tm-trialmaple/shared/types/api/user';
import Column from 'primevue/column';
import type { DataTableRowSelectEvent } from 'primevue/datatable';
import DataTable from 'primevue/datatable';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const users = ref<User[]>([]);

const filters = ref({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    userType: { value: null, matchMode: FilterMatchMode.EQUALS }
});

onMounted(() => {
    users.value = [
        { discordId: '123456789', username: 'Wirtual', avatar: null, discriminator: '0001', userType: 'USER' },
        { discordId: '987654321', username: 'Massa', avatar: null, discriminator: null, userType: 'USER' },
        { discordId: '111222333', username: 'AdminZer', avatar: null, discriminator: '9999', userType: 'ADMIN' },
        { discordId: '123426789', username: 'Wirtual2', avatar: null, discriminator: '0001', userType: 'USER' },
        { discordId: '987664321', username: 'Massa2', avatar: null, discriminator: null, userType: 'USER' },
        { discordId: '110222333', username: 'AdminZer2', avatar: null, discriminator: '9999', userType: 'ADMIN' },
        { discordId: '123459969', username: 'Wirtual3', avatar: null, discriminator: '0001', userType: 'USER' },
        { discordId: '987654331', username: 'Massa3', avatar: null, discriminator: null, userType: 'USER' },
        { discordId: '101222333', username: 'AdminZer3', avatar: null, discriminator: '9999', userType: 'ADMIN' },
        { discordId: '123455969', username: 'Wirtual4', avatar: null, discriminator: '0001', userType: 'USER' },
        { discordId: '987654551', username: 'Massa4', avatar: null, discriminator: null, userType: 'USER' },
        { discordId: '179222333', username: 'AdminZer4', avatar: null, discriminator: '9999', userType: 'ADMIN' }
    ];
});

const onRowClick = (event: DataTableRowSelectEvent) => {
    const selectedUser: User = event.data;
    router.push(`/admin/users/${selectedUser.discordId}`);
};
</script>
