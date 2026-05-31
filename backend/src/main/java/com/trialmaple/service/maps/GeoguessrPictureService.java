package com.trialmaple.service.maps;

import com.trialmaple.TmMapleConstant;
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
public class GeoguessrPictureService {

    private final static String GAME_MODE_PATH_NAME = "GEOGUESSR";

    private final DailyMapRepository dailyMapRepository;

    @Value("${game.pictures-path}")
    private String rootPathName;

    private final Random random = new Random();

    // Game mode -> (map name -> (difficulty level -> list of pictures))
    private final Map<String, Map<String, Map<Integer, List<String>>>> index = new HashMap<>();

    public GeoguessrPictureService(DailyMapRepository dailyMapRepository) {
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

                Map<String, Map<Integer, List<String>>> maps = new HashMap<>();

                try (DirectoryStream<Path> mapDirs = Files.newDirectoryStream(gameModeDir)) {
                    // Loop over maps
                    for (Path mapDir : mapDirs) {
                        if (!Files.isDirectory(mapDir)) continue;

                        String mapName = mapDir.getFileName().toString();

                        Map<Integer, List<String>> subFolders = new TreeMap<>();

                        try (DirectoryStream<Path> subDirs = Files.newDirectoryStream(mapDir)) {
                            // Loop over subfolders (difficulty rating)
                            for (Path subDir : subDirs) {
                                if (!Files.isDirectory(subDir)) continue;

                                String folderName = subDir.getFileName().toString();

                                // Subfolder must be a number (difficulty)
                                int subIndex;
                                try {
                                    subIndex = Integer.parseInt(folderName);
                                } catch (NumberFormatException e) {
                                    log.error("Invalid subfolder found: {} for map {} for game {}", folderName, mapName, gameMode, e);
                                    continue;
                                }

                                List<String> pictures = new ArrayList<>();

                                try (DirectoryStream<Path> files = Files.newDirectoryStream(subDir)) {
                                    for (Path file : files) {
                                        if (Files.isRegularFile(file)) {
                                            pictures.add(file.getFileName().toString());
                                        }
                                    }
                                }

                                subFolders.put(subIndex, pictures);
                            }
                        }

                        maps.put(mapName, subFolders);
                    }
                }

                index.put(gameMode, maps);
            }
        }
    }

    public DailyPictures getRandomMap(String picturesFolder, GameMode gameMode) {
        Map<String, Map<Integer, List<String>>> gameMaps = index.get(picturesFolder);
        if (gameMaps.isEmpty()) {
            log.error("No maps folder found for game mode {}", picturesFolder);
            return null;
        }

        List<String> maps = new ArrayList<>(gameMaps.keySet());

        // Remove recently picked maps from map pool to avoid repeats
        LocalDate startDate = LocalDate.now().minusDays(10);
        Set<String> recentlyPickedMaps = new HashSet<>(dailyMapRepository.findGeoguessrMapNameByGameModeAndStartDate(gameMode, startDate));
        if (recentlyPickedMaps.size() < maps.size()) {
            maps = maps.stream()
                    .filter(map -> !recentlyPickedMaps.contains(map))
                    .toList();
        }

        String selectedMap = maps.get(random.nextInt(maps.size()));
        Map<Integer, List<String>> subFolders = gameMaps.get(selectedMap);

        List<String> pictures = new ArrayList<>();
        for (int i = 1; i <= TmMapleConstant.GEOGUESSR_PICTURES_COUNT; i++) {
            String picture = pickRandom(subFolders.get(i));
            if (picture == null) {
                log.error("No picture found for subfolder {} for map {} for game {}", i, selectedMap, picturesFolder);
                return null;
            }
            pictures.add(picture);
        }

        return new DailyPictures(selectedMap, pictures);
    }

    private String pickRandom(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Returns the path of the picture n°{attempt} for the daily map of given game mode
     */
    public Path getTodayPicturePath(GameMode gameMode, GeoguessrDailyMap dailyMap, int attempt) {
        DailyPictures dailyPictures = dailyMap.getDailyPictures();

        String gameFolder = gameMode.getPicturesFolderName();
        String dailyMapName = dailyPictures.getMapName();
        String pictureName = dailyPictures.getPicturesName().get(attempt - 1);

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
