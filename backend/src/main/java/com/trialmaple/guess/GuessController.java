package com.trialmaple.guess;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.core.exception.InvalidGameModeException;
import com.trialmaple.core.exception.InvalidMapException;
import com.trialmaple.core.exception.NoDailyMapFoundException;
import com.trialmaple.guess.dto.AnswerDto;
import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.GuessRequestDto;
import com.trialmaple.user.User;
import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.security.annotation.CurrentUser;
import com.trialmaple.dailymap.DailyMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(RouteKey.GUESS_PREFIX)
@CrossOrigin(origins = "*")
public class GuessController {

    private final DailyMapService dailyMapService;
    private final GuessService guessService;

    public GuessController(GuessService guessService, DailyMapService dailyMapService) {
        this.guessService = guessService;
        this.dailyMapService = dailyMapService;
    }

    /**
     * Make a guess for the given game mode
     */
    @PostMapping("")
    public GuessDto guess(@RequestBody GuessRequestDto request, @RequestParam String gameMode, @CurrentUser User user)
            throws NoDailyMapFoundException, InvalidMapException {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            DailyMap dailyMap = dailyMapService.getCurrentDailyMap(gameModeValue);
            return guessService.checkGuess(dailyMap, request, user);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * Give up on finding daily map
     */
    @PostMapping(RouteKey.GIVE_UP)
    public AnswerDto giveUp(@RequestParam String gameMode) throws NoDailyMapFoundException {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            DailyMap dailyMap = dailyMapService.getCurrentDailyMap(gameModeValue);
            return guessService.giveUp(dailyMap);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}