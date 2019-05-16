package com.waes.diff.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.waes.diff.domain.model.Diff;
import com.waes.diff.domain.repository.DiffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiffService {

    private final DiffRepository repository;

    private final JsonHelper jsonHelper;

    @Autowired
    public DiffService(DiffRepository repository, JsonHelper jsonHelper) {
        this.repository = repository;
        this.jsonHelper = jsonHelper;
    }

    public void saveLeft(final String id, final String content) {
        final JsonNode leftJson = jsonHelper.fromBase64(content);
        final Diff diff = findByIdOrGetNew(id);
        diff.setLeft(content);
        if (diff.isComplete())
            diff.setDiff(jsonHelper.compare(leftJson, jsonHelper.fromBase64(diff.getRight())));
        repository.save(diff);
    }

    public void saveRight(final String id, final String content) {
        final JsonNode rightJson = jsonHelper.fromBase64(content);
        final Diff diff = findByIdOrGetNew(id);
        diff.setRight(content);
        if (diff.isComplete())
            diff.setDiff(jsonHelper.compare(jsonHelper.fromBase64(diff.getLeft()), rightJson));
        repository.save(diff);
    }

    public String getDiff(final String id) throws EmptyResultDataAccessException {
        final Optional<Diff> diff = repository.findById(id);
        if (!diff.isPresent() || !diff.get().isComplete())
            throw new EmptyResultDataAccessException(1);
        return diff.get().getDiff();
    }

    private Diff findByIdOrGetNew(final String id) {
        return repository.findById(id).orElse(new Diff(id));
    }

}
