package com.trialmaple.service.maps;

import com.trialmaple.TmMapleConstant;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.enums.GameMode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class PictureService {

    @Value("${game.pictures-path}")
    private String mapsPath;

    private final Random random = new Random();

    // Game mode -> (map name -> (difficulty level -> list of pictures))
    private final Map<String, Map<String, Map<Integer, List<String>>>> index = new HashMap<>();

    /**
     * Initialize the structure containing all maps pictures for each game mode
     */
    @PostConstruct
    public void init() throws IOException {
        Path rootPath = Paths.get(mapsPath);
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

    public DailyPictures getRandomMap(String gameMode) {
        Map<String, Map<Integer, List<String>>> gameMaps = index.get(gameMode);
        if (gameMaps.isEmpty()) {
            log.error("No maps folder found for game mode {}", gameMode);
            return null;
        }

        List<String> maps = new ArrayList<>(gameMaps.keySet());

        String selectedMap = maps.get(random.nextInt(maps.size()));
        Map<Integer, List<String>> subFolders = gameMaps.get(selectedMap);

        List<String> pictures = new ArrayList<>();
        for (int i = 1; i <= TmMapleConstant.GEOGUESSR_PICTURES_COUNT; i++) {
            String picture = pickRandom(subFolders.get(i));
            if (picture == null) {
                log.error("No picture found for subfolder {} for map {} for game {}", i, selectedMap, gameMode);
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

    public Path getTodayPicturePath(GameMode gameMode, DailyMap dailyMap, int attempt) {
        DailyPictures dailyPictures = dailyMap.getDailyPictures();

        String gameFolder = gameMode.getPicturesFolderName();
        String dailyMapName = dailyPictures.getMapName();
        String pictureName = dailyPictures.getPicturesName().get(attempt - 1);

        return getPicturePath(gameFolder, dailyMapName, attempt, pictureName);
    }

    public Path getPicturePath(String game, String mapName, int attempt, String pictureName) {
        return Paths.get(mapsPath, game, mapName, String.valueOf(attempt), pictureName);
    }
}
