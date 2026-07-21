const TIME_REGEX = /^(?:(?:(\d{1,2}):)?([0-5]?\d):)?([0-5]?\d)\.\d{2,3}$/;

export function isValidTimeFormat(timeStr: string) {
    if (!timeStr) return false;
    return TIME_REGEX.test(timeStr);
}
