package com.example.register.service.impl;

import com.example.register.model.ChangeHistory;
import com.example.register.model.repo.ChangeHistoryRepository;
import com.example.register.service.ChangeHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeHistoryServiceImpl implements ChangeHistoryService {

    private final ChangeHistoryRepository changeHistoryRepository;

    @Override
    @Transactional
    public void saveAll(List<ChangeHistory> changeHistoryList) {
        changeHistoryRepository.saveAll(changeHistoryList);
    }

    @Override
    public void save(ChangeHistory changeHistory) {
        changeHistoryRepository.save(changeHistory);
    }
}
