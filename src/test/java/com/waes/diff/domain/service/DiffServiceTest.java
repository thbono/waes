package com.waes.diff.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.diff.domain.model.Diff;
import com.waes.diff.domain.repository.DiffRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

public class DiffServiceTest {

    private DiffService service;

    @Mock
    private DiffRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new DiffService(repository, new JsonHelper(new ObjectMapper()));

        Mockito.when(repository.findById("1")).thenReturn(Optional.of(new Diff("1")));
        Mockito.when(repository.findById("2")).thenReturn(Optional.of(new Diff("2")));
        Mockito.when(repository.findById("3")).thenReturn(Optional.of(new Diff("3")));
        Mockito.when(repository.findById("4")).thenReturn(Optional.empty());
    }

    @Test
    public void saveLeftFirst() {
        service.saveLeft("1", "eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        service.saveRight("1", "eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
    }

    @Test
    public void saveRightFirst() {
        service.saveRight("2", "eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        service.saveLeft("2", "eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
    }

    @Test
    public void getDiff() {
        service.saveLeft("3", "eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        service.saveRight("3", "eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9");
        final String diff = service.getDiff("3");
        Assert.assertEquals("{\"equals\":true,\"equalsInSize\":true,\"diff\":[]}", diff);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldNotGetNonExistentDiff() {
        service.getDiff("4");
    }

}