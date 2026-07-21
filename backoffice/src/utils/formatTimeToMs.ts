// Convert human readable time to milliseconds time for backend
export function formatTimeToMs(timeStr: string) {
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
}
