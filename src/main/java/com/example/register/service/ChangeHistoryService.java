package com.example.register.service;


import com.example.register.model.ChangeHistory;
import java.util.List;

public interface ChangeHistoryService {

    void saveAll(List<ChangeHistory> changeHistoryList);

    void save(ChangeHistory changeHistory);

}