import { DailyStats } from "#/types/api/dailyStats"
import { ErrorResponse } from "#/types/api/errorResponse"
import { Guess } from "#/types/api/guess"
import { TrialMap } from "#/types/api/trialMap"

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '/api'


async function handleJson<T>(res: Response): Promise<T> {
    if (!res.ok) {
        let err: ErrorResponse | undefined
        try {
            err = await res.json()
        } catch {
        }
        const msg = err?.message || res.statusText
        const code = err?.error || `HTTP_${res.status}`
        const e = new Error(msg) as Error & { code?: string; status?: number }
        e.code = code
        e.status = res.status
        throw e
    }
    return res.json() as Promise<T>
}


export function useApi() {
    const getMaps = () => {
        fetch(`${API_BASE}/maps`).then(res => handleJson<TrialMap[]>(res))
    };

    const postGuess = (guess: string, guessNumber = 1) => {
        fetch(`${API_BASE}/guess?guess=${encodeURIComponent(guess)}&guessNumber=${guessNumber}`, {
            method: 'POST'
        }).then(res => handleJson<Guess>(res))
    };

    const getDailyStats = () => {
        fetch(`${API_BASE}/stats`).then(res => handleJson<DailyStats>(res))
    };

    return { getMaps, postGuess, getDailyStats }
}