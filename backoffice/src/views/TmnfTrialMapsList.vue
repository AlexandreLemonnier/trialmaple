<template>
    <div class="p-6 bg-app-background min-h-screen flex flex-col gap-6">

        <div class="flex justify-between items-end">
            <H1>TMNF Trial Maps</H1>
            <Button label="Save Changes"
                    icon="pi pi-save"
                    :disabled="modifiedMaps.size === 0 || isSaving"
                    :loading="isSaving"
                    :action="saveChanges" />
        </div>

        <div class="rounded-2xl border border-app-border shadow-lg overflow-hidden">
            <DataTable :value="maps"
                       :loading="isLoading"
                       edit-mode="cell"
                       @cellEditComplete="onCellEditComplete"
                       paginator
                       :rows="100"
                       :rows-per-page-options="[15, 30, 60, 100]"
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
import Button from '#/components/Button.vue';
import H1 from '#/components/H1.vue';
import { DIFFICULTY_CATEGORIES } from '#/types/api/difficultyCategory';
import type { TmnfTrialMap } from '#/types/api/tmmap/tmnfTrialMap';
import type { TmUser } from '#/types/api/tmUser';
import AutoComplete from 'primevue/autocomplete';
import Checkbox from 'primevue/checkbox';
import Column from 'primevue/column';
import DataTable, { type DataTableCellEditCompleteEvent, type DataTableSortMeta } from 'primevue/datatable';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';
import { onMounted, ref } from 'vue';
import { useAdminMapsApi } from '../composables/api/useAdminMapsApi';

const isLoading = ref(true);
const isSaving = ref(false);
const maps = ref<TmnfTrialMap[]>([]);
const tmUsers = ref<TmUser[]>([]);
const filteredTmUsers = ref<TmUser[]>([]);
const defaultSort: DataTableSortMeta[] = [
    { field: 'points', order: 1 },
    { field: 'name', order: 1 }
];

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

        data[field] = newValue;

        markAsModified(data);
        return;
    }

    data[field] = newValue;
    markAsModified(data);
};


/* --- API --- */
const adminMapsApi = useAdminMapsApi();
const fetchMaps = async () => {
    try {
        maps.value = await adminMapsApi.getTmnfTrialMaps();
        tmUsers.value = [...new Map(maps.value.map((map) => [map.wrHolder.login, map.wrHolder])).values()];
    } catch (e) {
        console.error(e);
    }
};

const saveChanges = async () => {
    isSaving.value = true;
    try {
        // On prépare le payload final à envoyer au backend
        const payload = Array.from(modifiedMaps.value.values()).map((map) => {
            return {
                ...map,
                // On s'assure d'envoyer le wrTime en millisecondes
                wrTime: map.wrTime ? formatTimeToMs(map.wrTime).toString() : null
            };
        });

        console.log('Données envoyées à l\'API :', payload);

        // TODO: Remplacer par ton appel fetch (ex: PUT /api/maps/batch)
        // await api.updateMaps(payload);

        // Succès ! On vide le cache des modifications
        modifiedMaps.value.clear();

        // Optionnel: Recharger les données depuis l'API pour être sûr
        // await fetchMaps();
    } catch (error) {
        console.error('Erreur lors de la sauvegarde :', error);
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
