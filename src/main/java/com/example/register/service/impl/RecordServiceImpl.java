package com.example.register.service.impl;

import com.example.register.model.ChangeHistory;
import com.example.register.model.Record;
import com.example.register.model.repo.RecordRepository;
import com.example.register.service.ChangeHistoryService;
import com.example.register.service.RecordService;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final ChangeHistoryService changeHistoryService;

    @Override
    public Record save(Record record) {
        record.setReleaseDate(LocalDateTime.now());
        record.setTotalFaceValue(record.getFaceValue() * record.getAmount());
        record.setStatus("active");
        return recordRepository.save(record);
    }

    @Override
    public Record delete(Long idNumber) {
        Record deletedRecord = recordRepository.findFirstByIdNumber(idNumber);
        deletedRecord.setStatus("deleted");
        ChangeHistory changeHistory = new ChangeHistory();
        changeHistory.setIdNumber(idNumber);
        changeHistory.setFieldName("status");
        changeHistory.setOldValue("active");
        changeHistory.setNewValue("deleted");
        changeHistoryService.save(changeHistory);
        return recordRepository.save(deletedRecord);
    }

    @Override
    public Record update(Long idNumber, Map<String, Object> fields) {
        Record record = recordRepository.findFirstByIdNumber(idNumber);
        List<ChangeHistory> changeHistories = new ArrayList<>();
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Record.class, k);
            field.setAccessible(true);
            Object oldField = ReflectionUtils.getField(field, record);
            if (v.getClass().equals(Integer.class)) {
                v = ((Integer) v).longValue();
            }
            ReflectionUtils.setField(field, record, v);
            if (field.getName().equals("amount") || field.getName().equals("faceValue")) {
                record.setTotalFaceValue(record.getAmount() * record.getFaceValue());
            }

            ChangeHistory changeHistory = new ChangeHistory();
            changeHistory.setIdNumber(idNumber);
            changeHistory.setFieldName(field.getName());
            changeHistory.setOldValue(oldField.toString());
            changeHistory.setNewValue(v.toString());
            changeHistories.add(changeHistory);
        });
        changeHistoryService.saveAll(changeHistories);
        return recordRepository.save(record);
    }

    @Override
    public Page<Record> getRecords(Long idNumber, String status, Pageable pageable) {
        Page<Record> page;
        if (idNumber != null || status != null) {
            page = findAllByIdNumberOrStatus(idNumber, status);
        } else {
            page = recordRepository.findAll(pageable);
        }
        return page;
    }

    private Page<Record> findAllByIdNumberOrStatus(Long idNumber, String status) {
        List<Record> recordsList = recordRepository.findAll((Specification<Record>) (r, cq, cb) -> {
            Path<Integer> idn = r.get("idNumber");
            Path<String> sn = r.get("status");
            List<Predicate> predicates = new ArrayList<>();
            if (idNumber != null) {
                predicates.add(cb.equal(idn, idNumber));
            }
            if (status != null) {
                predicates.add(cb.equal(sn, status));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        });
        return new PageImpl<>(recordsList);
    }

}
