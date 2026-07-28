<template>
    <DataTable :value="rows"
               :data-key="uniqueId"
               :loading="isLoading"
               edit-mode="cell"
               @cellEditComplete="cellEditCompleteCallback"
               scrollable
               scroll-height="flex"
               v-model:filters="filters"
               :global-filter-fields="globalFilterFields"
               :filter-display="filterDisplay"
               @rowSelect="onRowSelection"
               :selection-mode="onRowSelection ? 'single' : undefined"
               :row-hover="!!onRowSelection"
               removable-sort
               sort-mode="multiple"
               :multi-sort-meta="defaultSort">

        <template #empty>{{ noDataFoundText ?? 'No data.' }}</template>

        <Column v-for="col in cols"
                :key="String(col.field)"
                :field="String(col.field)"
                :header="col.name"
                :sortable="col.sortable"
                :sort-field="col.sortField"
                :body-class="col.editable && 'cursor-pointer hover:bg-black/5 transition-colors'"
                :show-filter-match-modes="col.showFilterMatchModes">

            <!-- Read only or boolean checkbox -->
            <template #body="{ data }">
                <slot :name="`body-${String(col.field)}`" :data="asT(data)" :field="col.field">

                    <!-- Boolean: checkbox -->
                    <div v-if="col.type === 'boolean'">
                        <Checkbox v-model="data[col.field]"
                                  :binary="true"
                                  :disabled="!col.editable"
                                  @change="col.onValueChange?.(data)" />
                    </div>

                    <!-- Standard readonly text -->
                    <span v-else>
                        {{ col.format ? col.format(data[col.field], data) : data[col.field] }}
                    </span>
                </slot>
            </template>

            <!-- Editable cell -->
            <template v-if="col.editable && col.type !== 'boolean'" #editor="{ data }">
                <slot :name="`editor-${String(col.field)}`" :data="asT(data)" :field="col.field">
                    <label v-if="col.type === 'select'" for="select-values" class="sr-only">Select values</label>

                    <InputNumber v-if="col.type === 'number'"
                                 v-model="data[col.field]"
                                 autofocus
                                 class="w-full" />

                    <Select v-else-if="col.type === 'select'"
                            id="select-values"
                            v-model="data[col.field]"
                            :options="col.options"
                            :option-label="col.optionLabel"
                            autofocus
                            append-to="body"
                            class="w-full" />

                    <AutoComplete v-else-if="col.type === 'autocomplete'"
                                  v-model="data[col.field]"
                                  :suggestions="col.suggestions"
                                  @complete="col.onSearch?.($event.query)"
                                  :option-label="col.optionLabel"
                                  :force-selection="false"
                                  complete-on-focus
                                  :placeholder="col.placeHolder"
                                  autofocus
                                  append-to="body"
                                  class="w-full" />

                    <InputText v-else
                               v-model="data[col.field]"
                               autofocus
                               :placeholder="col.placeHolder"
                               class="w-full"
                               :class="{ 'p-invalid': col.validationRule && !col.validationRule(data[col.field]) }" />
                </slot>
            </template>

            <template v-if="$slots[`filter-${String(col.field)}`]" #filter="{ filterModel, filterCallback }">
                <slot :name="`filter-${String(col.field)}`"
                      :filter-model="filterModel"
                      :filter-callback="filterCallback">
                </slot>
            </template>
        </Column>
    </DataTable>
</template>

<script setup lang="ts" generic="T">
import type { TableColumn } from '#/types/TableColumn';
import AutoComplete from 'primevue/autocomplete';
import Checkbox from 'primevue/checkbox';
import Column from 'primevue/column';
import type { DataTableCellEditCompleteEvent, DataTableFilterMeta, DataTableRowSelectEvent, DataTableSortMeta } from 'primevue/datatable';
import DataTable from 'primevue/datatable';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';

defineProps<{
    rows: T[],
    cols: TableColumn<T>[],
    noDataFoundText?: string,
    uniqueId: keyof T,
    isLoading?: boolean,
    cellEditCompleteCallback?(event: DataTableCellEditCompleteEvent): void,
    globalFilterFields?: (keyof T)[],
    filterDisplay?: 'row' | 'menu',
    defaultSort?: DataTableSortMeta[],
    onRowSelection?(event: DataTableRowSelectEvent<T>): void
}>();

// Because using "data as T" breaks the VSCode parser
const asT = (val: unknown): T => val as T;

const filters = defineModel<DataTableFilterMeta>();

</script>
