package com.trialmaple.service.maps;

import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.entities.GeoguessrDailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.DailyMapRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class BlurPictureService {

    private final static String GAME_MODE_PATH_NAME = "BLUR";

    private final DailyMapRepository dailyMapRepository;

    @Value("${game.pictures-path}")
    private String rootPathName;

    private final Random random = new Random();

    // Game mode -> (map name -> pictures)
    private final Map<String, Map<String, List<String>>> index = new HashMap<>();

    public BlurPictureService(DailyMapRepository dailyMapRepository) {
        this.dailyMapRepository = dailyMapRepository;
    }

    /**
     * Initialize the structure containing all maps pictures for each game mode
     */
    @PostConstruct
    public void init() throws IOException {
        Path rootPath = Paths.get(rootPathName + File.separator + GAME_MODE_PATH_NAME);
        try (DirectoryStream<Path> gameModes = Files.newDirectoryStream(rootPath)) {
            // Loop over game modes
            for (Path gameModeDir : gameModes) {
                if (!Files.isDirectory(gameModeDir)) continue;

                String gameMode = gameModeDir.getFileName().toString();

                Map<String, List<String>> maps = new HashMap<>();

                try (DirectoryStream<Path> mapDirs = Files.newDirectoryStream(gameModeDir)) {
                    // Loop over maps
                    for (Path mapDir : mapDirs) {
                        if (!Files.isDirectory(mapDir)) continue;

                        String mapName = mapDir.getFileName().toString();

                        List<String> pictures = new ArrayList<>();

                        try (DirectoryStream<Path> files = Files.newDirectoryStream(mapDir)) {
                            for (Path file : files) {
                                if (Files.isRegularFile(file)) {
                                    pictures.add(file.getFileName().toString());
                                }
                            }
                        }

                        maps.put(mapName, pictures);
                    }
                }

                index.put(gameMode, maps);
            }
        }
    }

    public DailyPictures getRandomMap(String picturesFolder, GameMode gameMode) {
        Map<String, List<String>> gameMaps = index.get(picturesFolder);
        if (gameMaps.isEmpty()) {
            log.error("No maps folder found for game mode {}", picturesFolder);
            return null;
        }

        List<String> maps = new ArrayList<>(gameMaps.keySet());

        // Remove recently picked maps from map pool to avoid repeats
        LocalDate startDate = LocalDate.now().minusDays(30);
        Set<String> recentlyPickedMaps = new HashSet<>(dailyMapRepository.findGeoguessrMapNameByGameModeAndStartDate(gameMode, startDate));
        if (recentlyPickedMaps.size() < maps.size()) {
            maps = maps.stream()
                    .filter(map -> !recentlyPickedMaps.contains(map))
                    .toList();
        }

        String selectedMap = maps.get(random.nextInt(maps.size()));
        List<String> pictures = gameMaps.get(selectedMap);

        if (pictures == null || pictures.isEmpty()) {
            log.error("No picture found for map {} for game {}", selectedMap, picturesFolder);
            return null;
        }

        return new DailyPictures(selectedMap, pictures.subList(0, 1));
    }

    /**
     * Returns the path of the picture for the daily map of given game mode
     */
    public Path getTodayPicturePath(GameMode gameMode, GeoguessrDailyMap dailyMap, int attempt) {
        DailyPictures dailyPictures = dailyMap.getDailyPictures();

        String gameFolder = gameMode.getPicturesFolderName();
        String dailyMapName = dailyPictures.getMapName();
        String pictureName = dailyPictures.getPicturesName().getFirst();

        return getPicturePath(gameFolder, dailyMapName, attempt, pictureName);
    }

    private Path getPicturePath(String game, String mapName, int attempt, String pictureName) {
        return Paths.get(rootPathName, game, mapName, String.valueOf(attempt), pictureName);
    }

    /**
     * Get the list of maps name (map pool)
     */
    public List<String> getMapsName(GameMode gameMode) {
        String gameFolder = gameMode.getPicturesFolderName();
        return index.get(gameFolder).keySet().stream().toList();
    }
}
