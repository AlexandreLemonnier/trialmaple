<template>
    <div class="p-6 bg-app-background h-screen flex flex-col gap-6">

        <div class="flex justify-between items-end shrink-0">
            <H1>TMNF RPG Maps (Classic)</H1>
            <div class="flex gap-4">
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
                      no-data-found-text="No maps found."
                      class="p-datatable-sm flex-1" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { RequestError } from '#/classes/RequestError';
import AppTable from '#/components/AppTable.vue';
import Button from '#/components/Button.vue';
import H1 from '#/components/H1.vue';
import { useAdminMapsApi } from '#/composables/api/useAdminMapsApi';
import { useToast } from '#/composables/useToast';
import type { TmMap } from '#/types/api/tmMap';
import type { TmUser } from '#/types/api/tmUser';
import type { TableColumn } from '#/types/TableColumn';
import { formatTimeToMs } from '#/utils/formatTimeToMs';
import { FilterMatchMode } from '@primevue/core/api';
import type { DataTableCellEditCompleteEvent, DataTableSortMeta } from 'primevue/datatable';
import InputText from 'primevue/inputtext';
import { onMounted, ref } from 'vue';

const toast = useToast();

const isLoading = ref(true);
const isSaving = ref(false);

const maps = ref<TmMap[]>([]);
const defaultSort: DataTableSortMeta[] = [
    { field: 'points', order: -1 },
    { field: 'name', order: 1 }
];

const filters = ref({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

// Set to track the modified maps (key: uuid, value: modified mapo)
const modifiedMaps = ref<Map<string, TmMap>>(new Map());

const markAsModified = (map: TmMap) => {
    modifiedMaps.value.set(map.uuid, map);
};

// Triggered once we leave a cell
const onCellEditComplete = (event: DataTableCellEditCompleteEvent) => {
    const { newValue } = event;
    const { data, field } = event;

    if (data[field] !== newValue) {
        data[field] = newValue;
        markAsModified(data);
    }
};

const cols: TableColumn<TmMap>[] = [
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
        field: 'points',
        name: 'Stars',
        sortable: true,
        type: 'number'
    },
    {
        field: 'checkpointCount',
        name: 'CPs'
    },
    {
        field: 'authors',
        name: 'Author(s)',
        sortable: true,
        editable: true,
        type: 'chips',
        onValueChange: markAsModified,
        format: (val) => (val as string[]).join(', ')
    },
    {
        field: 'releaseYear',
        name: 'Release Year',
        editable: true,
        sortable: true,
        type: 'number'
    },
    {
        field: 'wrTime',
        name: 'WR Time'
    },
    {
        field: 'wrHolder',
        name: 'WR Holder',
        sortable: true,
        sortField: 'wrHolder.login',
        format: (val) => (val as TmUser)?.login || 'N/A'
    },
    {
        field: 'wrYear',
        name: 'WR Year',
        type: 'number'
    }
];


/* --- API --- */
const adminMapsApi = useAdminMapsApi();
const fetchMaps = async () => {
    try {
        maps.value = await adminMapsApi.getMaps('CLASSIC_TMNF_RPG');
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

        await adminMapsApi.updateMaps(payload);
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

onMounted(async () => {
    isLoading.value = true;
    await fetchMaps();
    isLoading.value = false;
});
</script>
