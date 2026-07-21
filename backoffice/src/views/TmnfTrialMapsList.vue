<template>
    <div class="p-6 bg-app-background min-h-screen flex flex-col gap-6">

        <div class="flex justify-between items-end">
            <H1>TMNF Trial Maps</H1>
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

        <div class="rounded-2xl border border-app-border shadow-lg overflow-hidden">
            <DataTable :value="maps"
                       data-key="uuid"
                       :loading="isLoading"
                       edit-mode="cell"
                       @cellEditComplete="onCellEditComplete"
                       paginator
                       :rows="50"
                       :rows-per-page-options="[15, 30, 60, 100]"
                       v-model:filters="filters"
                       :global-filter-fields="['name']"
                       removable-sort
                       sort-mode="multiple"
                       :multi-sort-meta="defaultSort"
                       class="p-datatable-sm">

                <Column field="active" header="Active">
                    <template #body="{ data }">
                        <Checkbox v-model="data.active" :binary="true" @change="markAsModified(data)" />
                    </template>
                </Column>

                <Column field="name" header="Name" sortable />

                <Column field="difficulty" header="Difficulty" body-class="cursor-pointer hover:bg-black/5 transition-colors">
                    <template #editor="{ data, field }">
                        <label for="difficulty-values" class="sr-only">Difficulty values</label>
                        <Select id="difficulty-values"
                                v-model="data[field]"
                                :options="[...DIFFICULTY_CATEGORIES]"
                                autofocus
                                append-to="body"
                                class="w-full" />
                    </template>
                </Column>

                <Column field="points" header="Points" sortable body-class="cursor-pointer hover:bg-black/5 transition-colors">
                    <template #editor="{ data, field }">
                        <InputNumber v-model="data[field]" autofocus :min="0" class="w-full" />
                    </template>
                </Column>

                <Column field="checkpointCount" header="CPs" />

                <Column field="authors" header="Author(s)">
                    <template #body="{ data }">
                        {{ data.authors.join(', ') }}
                    </template>
                </Column>

                <Column field="releaseYear" header="Release Year" />

                <Column field="finisherCount" header="Finishers" body-class="cursor-pointer hover:bg-black/5 transition-colors">
                    <template #editor="{ data, field }">
                        <InputNumber v-model="data[field]" autofocus :min="0" class="w-full" />
                    </template>
                </Column>

                <Column field="wrTime" header="WR Time" body-class="cursor-pointer hover:bg-black/5 transition-colors">
                    <template #editor="{ data, field }">
                        <InputText v-model="data[field]"
                                   autofocus
                                   placeholder="00:00.000"
                                   class="w-full"
                                   :class="{ 'p-invalid': !isValidTimeFormat(data[field]) }" />
                    </template>
                </Column>

                <Column field="wrHolder" header="WR Holder" sortable sort-field="wrHolder.login" body-class="cursor-pointer hover:bg-black/5 transition-colors">
                    <template #body="{ data }">
                        {{ data.wrHolder?.login || 'N/A' }}
                    </template>
                    <template #editor="{ data, field }">
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
                </Column>
            </DataTable>
        </div>
    </div>
</template>

<script setup lang="ts">
import { RequestError } from '#/classes/RequestError';
import Button from '#/components/Button.vue';
import H1 from '#/components/H1.vue';
import { useAdminMapsApi } from '#/composables/api/useAdminMapsApi';
import { useToast } from '#/composables/useToast';
import { DIFFICULTY_CATEGORIES } from '#/types/api/difficultyCategory';
import type { TmnfTrialMap } from '#/types/api/tmmap/tmnfTrialMap';
import type { TmUser } from '#/types/api/tmUser';
import { FilterMatchMode } from '@primevue/core/api';
import AutoComplete from 'primevue/autocomplete';
import Checkbox from 'primevue/checkbox';
import Column from 'primevue/column';
import DataTable, { type DataTableCellEditCompleteEvent, type DataTableSortMeta } from 'primevue/datatable';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';
import { onMounted, ref } from 'vue';

const toast = useToast();

const isLoading = ref(true);
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

// Time validation regex : HH:MM:SS.ms or MM:SS.ms
const TIME_REGEX = /^(?:(?:(\d{1,2}):)?([0-5]?\d):)?([0-5]?\d)\.\d{2,3}$/;

const isValidTimeFormat = (timeStr: string) => {
    if (!timeStr) return false;
    return TIME_REGEX.test(timeStr);
};

// Convert human readable time to milliseconds time for backend
const formatTimeToMs = (timeStr: string): number => {
    if (!timeStr) return 0;
    const parts = timeStr.split(':');
    let ms = 0;

    if (parts.length === 3) {
        // HH:MM:SS.ms
        ms += Number.parseInt(parts[0]!) * 3600000;
        ms += Number.parseInt(parts[1]!) * 60000;
        ms += Number.parseFloat(parts[2]!) * 1000;
    } else if (parts.length === 2) {
        // MM:SS.ms
        ms += Number.parseInt(parts[0]!) * 60000;
        ms += Number.parseFloat(parts[1]!) * 1000;
    }

    return Math.round(ms);
};

const markAsModified = (map: TmnfTrialMap) => {
    modifiedMaps.value.set(map.uuid, map);
};

// Handle AutoComplete serach
const searchUsers = ({ query }: { query: string }) => {
    const q = query.trim().toLowerCase();

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

onMounted(async () => {
    isLoading.value = true;
    await fetchMaps();
    isLoading.value = false;
});
</script>
