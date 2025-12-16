<template>
    <Modal title="About TrialMaple" v-model="isOpen">
        <div class="flex flex-col gap-2 mt-3 text-sm text-neutral-300">
            <p><strong>TrialMaple is a daily guessing game</strong> based on TMNF's "<a :href="tmrpgtrialWebsiteLink" target="_blank"><u>Hardest Trials List</u></a>".</p>
            <p>Note that <strong>unfinished maps</strong> are not included.</p>
            <hr class="opacity-20" />
            <p>Pick a map and click <strong>"Guess"</strong> to get hints: does today's map have more or fewer points? Which authors match? Repeat until you find it!</p>
            <p>You can either play without any help, relying only on your own knowledge, or use the trial <a :href="tmrpgtrialWebsiteLink" target="_blank"><u>website</u></a> list. I recommand the first option for a better experience, and the second one if you get stuck.</p>
            <hr class="opacity-20" />
            <p>A new map becomes available every day at <strong>00:00 CET/CEST</strong>.</p>
            <p>Maps data last updated on <strong>16 December 2025</strong>.</p>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '#/components/modal/Modal.vue';

const isOpen = defineModel<boolean>();
const tmrpgtrialWebsiteLink = 'https://tmrpgtrial.com/list/cm7z509dr0f91mph5e47sftz0';

</script>
