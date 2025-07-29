package com.trialmaple.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.TrialMap;
import com.trialmaple.repository.TrialMapRepository;

@Service
public class TrialMapService {
    private final TrialMapRepository repository;

    public TrialMapService(TrialMapRepository repository) {
        this.repository = repository;
    }

    public List<TrialMap> getAllMaps() {
        return repository.findAll();
    }
}
