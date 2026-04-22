import confetti from 'canvas-confetti';

function triggerConfetti() {
    const duration = 2000;
    const end = Date.now() + duration;
    (function frame() {
        confetti({
            particleCount: 10,
            spread: 70,
            startVelocity: 30,
            ticks: 60,
            origin: {
                x: Math.random(),
                y: Math.random() - 0.2
            }
        });

        if (Date.now() < end) {
            requestAnimationFrame(frame);
        }
    })();
}

export function useConfetti() {
    return { triggerConfetti };
}
