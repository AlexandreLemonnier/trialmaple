package com.trialmaple.service.maps;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.maps.update.IMapUpdateStrategy;

@Service
public class TmMapService {
    private final TmMapRepository repository;
    private final List<IMapUpdateStrategy> updateStrategies;

    public TmMapService(TmMapRepository repository, List<IMapUpdateStrategy> updateStrategies) {
        this.repository = repository;
        this.updateStrategies = updateStrategies;
    }

    public List<String> getAllMapNames(boolean finished) {
        return repository.findAllMapNames(finished);
    }

    public void fetchAndUpdateMaps() {
        updateStrategies.forEach(IMapUpdateStrategy::fetchAndUpdate);
    }

}
