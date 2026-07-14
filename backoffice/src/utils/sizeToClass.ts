import type { Size } from '#/types/Size';

export function maxSizeToClass(size: Size): string {
    const sizeClasses: Record<Size, string> = {
        'sm': 'max-w-sm',
        'md': 'max-w-md',
        'lg': 'max-w-2xl',
        'xl': 'max-w-4xl',
        'full': 'max-w-[95vw] md:max-w-[90vw]'
    };
    return sizeClasses[size];
}

export function sizeToClass(size: Size): string {
    const sizeClasses: Record<Size, string> = {
        'sm': 'size-4 lg:size-5',
        'md': 'size-6 lg:size-7',
        'lg': 'size-8 lg:size-9',
        'xl': 'size-10 lg:size-11',
        'full': 'size-full'
    };
    return sizeClasses[size];
}
