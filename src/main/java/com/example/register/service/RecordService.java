package com.example.register.service;

import com.example.register.model.Record;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface RecordService {

    Record save(Record record);

    Record delete(Long idNumber);

    Record update(Long idNumber, Map<String, Object> fields);

    Page<Record> getRecords(Long idNumber, String status, Pageable pageable);

}