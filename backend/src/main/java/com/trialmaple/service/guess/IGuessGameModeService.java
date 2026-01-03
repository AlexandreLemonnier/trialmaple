package com.trialmaple.service.guess;

import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;

public interface IGuessGameModeService {
    GameMode getGameMode();

    GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapNameException;
}
