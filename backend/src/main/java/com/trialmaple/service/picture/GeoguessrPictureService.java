package com.trialmaple.service.picture;

import com.trialmaple.model.dto.projection.PictureUseCount;
import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.entities.dailymap.GeoguessrDailyMap;
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

    /**
     * Pick a random map and its pictures for the given game mode
     */
    public DailyPictures getRandomMap(String picturesFolder, GameMode gameMode) {
        Map<String, Map<Integer, List<String>>> gameMaps = index.get(picturesFolder);
        if (gameMaps == null || gameMaps.isEmpty()) {
            log.error("No maps folder '{}' found for game mode {}", picturesFolder, gameMode);
            return null;
        }

        // Get usage history of each picture from DB
        Map<String, Integer> pictureUsageCounts = new HashMap<>();
        List<PictureUseCount> usageCounts = dailyMapRepository.countPicturesUsageByGameMode(gameMode);
        for (PictureUseCount usageCount : usageCounts) {
            pictureUsageCounts.put(usageCount.pictureName(), usageCount.count().intValue());
        }

        record TrioCandidate(String mapName, String picture1, String picture2, String picture3, int totalSelections) {
        }

        List<TrioCandidate> allCandidates = new ArrayList<>();

        // Generate all possible trios combinations for every map
        for (Map.Entry<String, Map<Integer, List<String>>> mapEntry : gameMaps.entrySet()) {
            String mapName = mapEntry.getKey();
            Map<Integer, List<String>> subFolders = mapEntry.getValue();

            List<String> list1 = subFolders.get(1);
            List<String> list2 = subFolders.get(2);
            List<String> list3 = subFolders.get(3);

            // Security if a difficulty folder is empty or missing
            if (list1 == null || list2 == null || list3 == null || list1.isEmpty() || list2.isEmpty() || list3.isEmpty()) {
                log.error("Map {} skipped: missing images in one of the difficulty folders", mapName);
                continue;
            }

            // Triple loop to link each possible picture 1, 2 and 3
            for (String picture1 : list1) {
                int count1 = pictureUsageCounts.getOrDefault(picture1, 0);
                for (String picture2 : list2) {
                    int count2 = pictureUsageCounts.getOrDefault(picture2, 0);
                    for (String picture3 : list3) {
                        int count3 = pictureUsageCounts.getOrDefault(picture3, 0);
                        TrioCandidate candidate = new TrioCandidate(mapName, picture1, picture2, picture3, count1 + count2 + count3);
                        allCandidates.add(candidate);
                    }
                }
            }
        }

        if (allCandidates.isEmpty()) {
            log.error("No valid picture combinations found for game mode {}", gameMode);
            return null;
        }

        // Find minimal score (total selection) among all combinations
        int minSelections = allCandidates.stream()
                .mapToInt(TrioCandidate::totalSelections)
                .min()
                .orElse(0);

        // Filter to keep only trios with minimal score
        List<TrioCandidate> bestCandidates = allCandidates.stream()
                .filter(c -> c.totalSelections == minSelections)
                .toList();

        // Randomly pick one
        TrioCandidate chosenCandidate = bestCandidates.get(random.nextInt(bestCandidates.size()));

        return new DailyPictures(
                chosenCandidate.mapName,
                List.of(chosenCandidate.picture1, chosenCandidate.picture2, chosenCandidate.picture3)
        );
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
        return Paths.get(rootPathName, GAME_MODE_PATH_NAME, game, mapName, String.valueOf(attempt), pictureName);
    }

    /**
     * Get the list of maps name (map pool)
     */
    public List<String> getMapsName(GameMode gameMode) {
        String gameFolder = gameMode.getPicturesFolderName();
        return index.get(gameFolder).keySet().stream().toList();
    }
}
