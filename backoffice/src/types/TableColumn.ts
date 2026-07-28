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

    // Règle de validation (utilisée pour ajouter la classe 'p-invalid')
    validationRule?(fieldValue: T[keyof T]): boolean;

    // Fonction utilitaire pour formater l'affichage dans le #body (ex: join(',') pour un tableau d'auteurs)
    format?(value: T[keyof T], row: T): string | number;

    // Props spécifiques pour 'select' et 'autocomplete'
    options?: unknown[];
    suggestions?: unknown[];
    optionLabel?: string;

    // Callbacks
    // Utilisé par l'autocomplete
    onSearch?(query: string): void;
    // Utile pour déclencher des actions manuelles (ex: markAsModified)
    onValueChange?(row: T): void;
};
