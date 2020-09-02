package com.example.register.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.register.model.ChangeHistory;
import com.example.register.model.repo.ChangeHistoryRepository;
import com.example.register.service.impl.ChangeHistoryServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

class ChangeHistoryServiceTest {

    private ChangeHistoryService changeHistoryService;
    @Mock
    private ChangeHistoryRepository changeHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        changeHistoryService = new ChangeHistoryServiceImpl(changeHistoryRepository);
    }

    @Test
    void saveAll() {
        ChangeHistory changeHistory = createChangeHistory();
        List<ChangeHistory> changeHistories = Collections.singletonList(changeHistory);

        changeHistoryService.saveAll(changeHistories);
        verify(changeHistoryRepository, times(1)).saveAll(changeHistories);
    }

    @Test
    void save() {
        ChangeHistory changeHistory = createChangeHistory();

        doAnswer((Answer<ChangeHistory>) invocationOnMock -> {
            ChangeHistory result = invocationOnMock.getArgument(0);
            assertEquals(result, changeHistory);
            return result;
        }).when(changeHistoryRepository).save(changeHistory);

        changeHistoryService.save(changeHistory);
    }

    private ChangeHistory createChangeHistory() {
        ChangeHistory changeHistory = new ChangeHistory();
        changeHistory.setId(1L);
        changeHistory.setIdNumber(2L);
        changeHistory.setFieldName("field");
        changeHistory.setOldValue("oldValue");
        changeHistory.setNewValue("newValue");
        return changeHistory;
    }
}