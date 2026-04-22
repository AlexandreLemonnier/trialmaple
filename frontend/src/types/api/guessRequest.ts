export type GuessRequest =
  (
    | {
        guessedMapUuid: string;
    }
    | {
        guessedMapName: string;
    }
  ) & {
      guessNumber: number;
      dailyMapUuid: string;
  };
