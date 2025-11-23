<template>
    <div class="text-sm italic">
        Time before reset: {{ remainingTime }}
    </div>
</template>

<script setup lang="ts">
import dayjs from 'dayjs';
import tz from 'dayjs/plugin/timezone';
import utc from 'dayjs/plugin/utc';
import { ref } from 'vue';

dayjs.extend(utc);
dayjs.extend(tz);

const remainingTime = ref('');

function getMsUntilReset() {
    const now = dayjs();
    const next = dayjs().tz('Europe/Paris').startOf('day').add(1, 'day');

    return next.valueOf() - now.valueOf();
}

setInterval(() => {
    const ms = getMsUntilReset();
    const totalSec = Math.floor(ms / 1000);
    const h = Math.floor(totalSec / 3600);
    const m = Math.floor(totalSec % 3600 / 60);
    const s = totalSec % 60;

    remainingTime.value = `${h}h ${m}m ${s}s`;
}, 1000);

</script>
