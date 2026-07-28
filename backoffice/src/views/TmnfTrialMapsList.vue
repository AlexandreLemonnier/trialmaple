<template>
    <div class="p-6 bg-app-background h-screen flex flex-col gap-6">

        <div class="flex justify-between items-end shrink-0">
            <H1>TMNF Trial Maps</H1>
            <div class="flex gap-4">
                <Button label="New Map"
                        icon="pi pi-plus"
                        severity="secondary"
                        :action="() => isAddModalVisible = true" />
                <Button label="Save Changes"
                        icon="pi pi-save"
                        :disabled="modifiedMaps.size === 0 || isSaving"
                        :loading="isSaving"
                        :action="saveChanges" />
                <!-- Search bar -->
                <span class="relative">
                    <i class="pi pi-search absolute left-3 top-1/2 -translate-y-1/2 text-text-muted" />
                    <InputText v-model="filters['global'].value"
                               placeholder="Search a map..."
                               class="pl-10 py-2 border border-app-border rounded-lg focus:ring-2 focus:ring-ring-focus" />
                </span>
            </div>
        </div>

        <div class="flex-1 flex flex-col min-h-0 rounded-2xl border border-app-border shadow-lg overflow-hidden">
            <AppTable :rows="maps"
                      :cols="cols"
                      unique-id="uuid"
                      :is-loading="isLoading"
                      v-model:filters="filters"
                      :global-filter-fields="['name']"
                      :default-sort="defaultSort"
                      :cell-edit-complete-callback="onCellEditComplete"
                      class="p-datatable-sm flex-1">
                <template #editor-wrHolder="{ data, field }">
                    <AutoComplete v-model="data[field]"
                                  :suggestions="filteredTmUsers"
                                  @complete="searchUsers"
                                  option-label="login"
                                  :force-selection="false"
                                  complete-on-focus
                                  placeholder="Select or type new..."
                                  autofocus
                                  append-to="body"
                                  class="w-full" />
                </template>
            </AppTable>
        </div>
        <MapAdditionDialog v-model:is-visible="isAddModalVisible"
                           :is-loading="isCreating"
                           :filtered-tm-users="filteredTmUsers"
                           @searchUsers="searchUsers"
                           @submit="handleCreateMap" />
    </div>
</template>

<script setup lang="ts">
import { RequestError } from '#/classes/RequestError';
import AppTable from '#/components/AppTable.vue';
import Button from '#/components/Button.vue';
import H1 from '#/components/H1.vue';
import { useAdminMapsApi } from '#/composables/api/useAdminMapsApi';
import { useToast } from '#/composables/useToast';
import { DIFFICULTY_CATEGORIES } from '#/types/api/difficultyCategory';
import type { CreateTmnfTrialMap, TmnfTrialMap } from '#/types/api/tmmap/tmnfTrialMap';
import type { TmUser } from '#/types/api/tmUser';
import type { TableColumn } from '#/types/TableColumn.js';
import { formatTimeToMs } from '#/utils/formatTimeToMs';
import { isValidTimeFormat } from '#/utils/isValidTimeFormat';
import { FilterMatchMode } from '@primevue/core/api';
import type { AutoCompleteCompleteEvent } from 'primevue/autocomplete';
import AutoComplete from 'primevue/autocomplete';
import type { DataTableCellEditCompleteEvent, DataTableSortMeta } from 'primevue/datatable';
import InputText from 'primevue/inputtext';
import { onMounted, ref } from 'vue';
import MapAdditionDialog from './modals/MapAdditionDialog.vue';

const toast = useToast();

const isAddModalVisible = ref(false);

const isLoading = ref(true);
const isCreating = ref(false);
const isSaving = ref(false);

const maps = ref<TmnfTrialMap[]>([]);
const tmUsers = ref<TmUser[]>([]);
const filteredTmUsers = ref<TmUser[]>([]);
const defaultSort: DataTableSortMeta[] = [
    { field: 'points', order: 1 },
    { field: 'name', order: 1 }
];

const filters = ref({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

// Set to track the modified maps (key: uuid, value: modified mapo)
const modifiedMaps = ref<Map<string, TmnfTrialMap>>(new Map());

const markAsModified = (map: TmnfTrialMap) => {
    modifiedMaps.value.set(map.uuid, map);
};

// Handle AutoComplete search
const searchUsers = (event: AutoCompleteCompleteEvent) => {
    const q = event.query.trim().toLowerCase();

    filteredTmUsers.value = !q
        ? [...tmUsers.value]
        : tmUsers.value.filter((u) =>
            u.displayName.toLowerCase().startsWith(q) ||
              u.login.toLowerCase().startsWith(q)
        );
};

// Triggered once we leave a cell
const onCellEditComplete = (event: DataTableCellEditCompleteEvent) => {
    let { newValue } = event;
    const { data, field, originalEvent } = event;

    // WR Time validation
    if (field === 'wrTime') {
        if (!isValidTimeFormat(newValue)) {
            originalEvent.preventDefault();
            return;
        }
    }

    // Handle new TmUser creation if needed
    if (field === 'wrHolder') {
        if (typeof newValue === 'string') {
            // If it's a string (and not a TmUser object), we create a new TmUser
            const newDisplayName = newValue.trim();
            newValue = {
                login: newDisplayName,
                displayName: newDisplayName,
                game: 'TMNF'
            } satisfies TmUser;
        }
    }

    if (data[field] !== newValue) {
        data[field] = newValue;
        markAsModified(data);
    }
};

const cols: TableColumn<TmnfTrialMap>[] = [
    {
        field: 'active',
        name: 'Active',
        editable: true,
        type: 'boolean',
        onValueChange: markAsModified
    },
    {
        field: 'name',
        name: 'Name',
        sortable: true
    },
    {
        field: 'difficulty',
        name: 'Difficulty',
        editable: true,
        type: 'select',
        options: [...DIFFICULTY_CATEGORIES]
    },
    {
        field: 'points',
        name: 'Points',
        sortable: true,
        editable: true,
        type: 'number'
    },
    {
        field: 'checkpointCount',
        name: 'CPs'
    },
    {
        field: 'authors',
        name: 'Author(s)',
        format: (val) => (val as string[]).join(', ')
    },
    {
        field: 'releaseYear',
        name: 'Release Year'
    },
    {
        field: 'finisherCount',
        name: 'Finishers',
        editable: true,
        type: 'number'
    },
    {
        field: 'wrTime',
        name: 'WR Time',
        editable: true,
        type: 'text',
        placeHolder: '00:00.000',
        validationRule: (val) => isValidTimeFormat(val as string)
    },
    {
        field: 'wrHolder',
        name: 'WR Holder',
        sortable: true,
        sortField: 'wrHolder.login',
        editable: true,
        format: (val) => (val as TmUser)?.login || 'N/A'
    }
];


/* --- API --- */
const adminMapsApi = useAdminMapsApi();
const fetchMaps = async () => {
    try {
        maps.value = await adminMapsApi.getMaps<TmnfTrialMap>('CLASSIC_TMNF_TRIAL');
        tmUsers.value = [...new Map(maps.value.map((map) => [map.wrHolder.login, map.wrHolder])).values()];
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Error while fetching maps', error });
    }
};

const saveChanges = async () => {
    isSaving.value = true;
    try {
        const payload = Array.from(modifiedMaps.value.values()).map((map) => {
            return {
                ...map,
                wrTime: formatTimeToMs(map.wrTime).toString()
            };
        });

        await adminMapsApi.updateMaps<TmnfTrialMap>(payload);
        toast.add({ severity: 'success', summary: 'Successful update!', detail: 'The maps have been updated.' });

        modifiedMaps.value.clear();
        await fetchMaps();
    } catch (error) {
        if (error instanceof RequestError) {
            console.log(error);
            console.log(error.statusCode);
            console.log(error.message);
        }
        toast.add({ severity: 'error', summary: 'Error while saving maps', error });
    } finally {
        isSaving.value = false;
    }
};

const handleCreateMap = async (newMapPayload: CreateTmnfTrialMap) => {
    isCreating.value = true;
    try {
        await adminMapsApi.createMap(newMapPayload, 'CLASSIC_TMNF_TRIAL');

        toast.add({ severity: 'success', summary: 'Success', detail: 'New map created successfully!' });
        isAddModalVisible.value = false;
        await fetchMaps();
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Creation failed', error });
    } finally {
        isCreating.value = false;
    }
};

onMounted(async () => {
    isLoading.value = true;
    await fetchMaps();
    isLoading.value = false;
});
</script>
