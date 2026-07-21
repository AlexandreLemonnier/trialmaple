<template>
    <Dialog v-model:visible="isVisible"
            modal
            header="Add New Map"
            :style="{ width: '45rem' }"
            class="p-fluid">

        <div class="flex flex-col gap-6 mt-2">
            <div class="flex gap-4 items-center">
                <div class="flex-1 flex flex-col gap-2">
                    <label for="new-name" class="font-semibold text-sm">Name *</label>
                    <InputText id="new-name" v-model="newMap.name" autofocus placeholder="Type map name..." />
                </div>
                <div class="flex items-center gap-2 mt-6">
                    <Checkbox id="new-active" v-model="newMap.active" :binary="true" />
                    <label for="new-active" class="font-semibold text-sm cursor-pointer">Active</label>
                </div>
            </div>

            <div class="flex flex-col gap-2">
                <label for="new-authors" class="font-semibold text-sm">Author(s)</label>
                <InputChips id="new-authors" v-model="newMap.authors" placeholder="Type a name and press Enter" separator="," />
            </div>

            <div class="grid grid-cols-3 gap-4">
                <div class="flex flex-col gap-2">
                    <label for="new-difficulty" class="font-semibold text-sm">Difficulty</label>
                    <Select id="new-difficulty" v-model="newMap.difficulty" :options="[...DIFFICULTY_CATEGORIES]" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="new-points" class="font-semibold text-sm">Points</label>
                    <InputNumber id="new-points" v-model="newMap.points" :min="0" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="new-cp" class="font-semibold text-sm">Checkpoints</label>
                    <InputNumber id="new-cp" v-model="newMap.checkpointCount" :min="0" />
                </div>
            </div>

            <div class="grid grid-cols-2 gap-4">
                <div class="flex flex-col gap-2">
                    <label for="new-year" class="font-semibold text-sm">Release Year</label>
                    <InputNumber id="new-year" v-model="newMap.releaseYear" :use-grouping="false" :min="2000" :max="currentYear" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="new-finishers" class="font-semibold text-sm">Finisher Count</label>
                    <InputNumber id="new-finishers" v-model="newMap.finisherCount" :min="0" />
                </div>
            </div>

            <div class="grid grid-cols-2 gap-4 border-t border-app-border pt-4">
                <div class="flex flex-col gap-2">
                    <label for="new-wr-time" class="font-semibold text-sm">WR Time</label>
                    <InputText id="new-wr-time"
                               v-model="newMap.wrTime"
                               placeholder="00:00.000"
                               :class="{ 'p-invalid': newMap.wrTime && !isValidTimeFormat(newMap.wrTime) }" />
                </div>
                <div class="flex flex-col gap-2">
                    <label for="new-wr-holder" class="font-semibold text-sm">WR Holder</label>
                    <AutoComplete id="new-wr-holder"
                                  v-model="newMap.wrHolder"
                                  :suggestions="filteredTmUsers"
                                  @complete="$emit('searchUsers', $event)"
                                  option-label="login"
                                  :force-selection="false"
                                  placeholder="Select or type new..." />
                </div>
            </div>
        </div>

        <template #footer>
            <div class="flex justify-end gap-2 mt-4">
                <Button label="Cancel" icon="pi pi-times" text @click="closeAddModal" />
                <Button label="Create Map" icon="pi pi-check" :loading="isLoading" @click="submit" />
            </div>
        </template>
    </Dialog>
</template>

<script lang="ts" setup>
import { useToast } from '#/composables/useToast';
import { DIFFICULTY_CATEGORIES } from '#/types/api/difficultyCategory';
import type { CreateTmnfTrialMap } from '#/types/api/tmmap/tmnfTrialMap';
import type { TmUser } from '#/types/api/tmUser';
import { formatTimeToMs } from '#/utils/formatTimeToMs';
import { isValidTimeFormat } from '#/utils/isValidTimeFormat';
import type { AutoCompleteCompleteEvent } from 'primevue';
import { AutoComplete, Button, Checkbox, Dialog, InputChips, InputNumber, InputText, Select } from 'primevue';
import { ref, watch } from 'vue';

defineProps<{
    isLoading: boolean;
    filteredTmUsers: TmUser[];
}>();

const emit = defineEmits<{
    (e: 'submit', payload: CreateTmnfTrialMap): void;
    (e: 'searchUsers', event: AutoCompleteCompleteEvent): void;
}>();

const isVisible = defineModel<boolean>('isVisible', { required: true });

const toast = useToast();

const currentYear = new Date().getFullYear();

// Type for creation form, since WR Holder can be a string if a new one is typed
type NewMapFormState = Omit<CreateTmnfTrialMap, 'wrHolder'> & {
    wrHolder?: TmUser | string;
};

function getDefaultNewMap(): NewMapFormState {
    return {
        active: true,
        name: '',
        difficulty: DIFFICULTY_CATEGORIES[0],
        points: 5,
        checkpointCount: 0,
        authors: [],
        releaseYear: currentYear,
        finisherCount: 0,
        wrTime: '',
        wrHolder: undefined
    };
}

const newMap = ref<NewMapFormState>(getDefaultNewMap());

watch(isVisible, () => {
    if (isVisible.value) {
        newMap.value = getDefaultNewMap();
    }
});

const closeAddModal = () => {
    isVisible.value = false;
};

const submit = () => {
    // Basic fields validation
    if (!newMap.value.name) {
        toast.add({ severity: 'warn', summary: 'Missing Info', detail: 'Map name is required.' });
        return;
    }

    if (!newMap.value.authors || newMap.value.authors.length === 0) {
        toast.add({ severity: 'warn', summary: 'Missing Info', detail: 'At least one author is required.' });
        return;
    }

    if (!newMap.value.wrHolder) {
        toast.add({ severity: 'warn', summary: 'Missing Info', detail: 'Map WR Holder is required.' });
        return;
    }

    // WR Time validation
    if (!newMap.value.wrTime) {
        toast.add({ severity: 'warn', summary: 'Missing Info', detail: 'Map WR Time is required.' });
        return;
    }

    if (!isValidTimeFormat(newMap.value.wrTime)) {
        toast.add({ severity: 'warn', summary: 'Invalid Time', detail: 'WR Time format is incorrect.' });
        return;
    }

    const wrHolder = newMap.value.wrHolder;
    let finalWrHolder: TmUser | undefined;
    if (typeof wrHolder === 'string') {
        const login = wrHolder.trim();
        if (login.length) {
            finalWrHolder = {
                login,
                displayName: login,
                game: 'TMNF'
            };
        } else {
            finalWrHolder = undefined;
        }
    } else {
        finalWrHolder = wrHolder;
    }

    if (!finalWrHolder) {
        toast.add({ severity: 'warn', summary: 'Missing Info', detail: 'WR Holder is required.' });
        return;
    }

    // Build payload
    const payload: CreateTmnfTrialMap = {
        ...newMap.value,
        wrTime: formatTimeToMs(newMap.value.wrTime).toString(),
        wrHolder: finalWrHolder
    };

    emit('submit', payload);
};
</script>
