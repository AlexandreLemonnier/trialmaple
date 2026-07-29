import type { ColumnType } from '#/types/ColumnType';

export type TableColumn<T> = {
    field: keyof T;
    name: string;
    sortable?: boolean;
    sortField?: string;
    editable?: boolean;
    type?: ColumnType;
    placeHolder?: string;
    showFilterMatchModes?: boolean;
    validationRule?(fieldValue: T[keyof T]): boolean;

    // Function if data needs to be formatted to be displayed
    format?(value: T[keyof T], row: T): string | number;

    // Specific props for 'select' and 'autocomplete'
    options?: unknown[];
    suggestions?: unknown[];
    optionLabel?: string;

    // Autocomplete callback
    onSearch?(query: string): void;
    // Callback when value changed
    onValueChange?(row: T): void;
};
