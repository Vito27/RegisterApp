package com.example.register.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.register.model.Record;
import com.example.register.model.repo.RecordRepository;
import com.example.register.service.impl.RecordServiceImpl;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public class RecordServiceTest {

    private RecordService recordService;
    @Mock
    private RecordRepository recordRepository;
    @Mock
    private ChangeHistoryService changeHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recordService = new RecordServiceImpl(recordRepository, changeHistoryService);
    }

    @Test
    public void save() {
        Record recordBeforeSave = createRecord();
        recordBeforeSave.setStatus(null);
        recordBeforeSave.setTotalFaceValue(null);
        recordBeforeSave.setReleaseDate(null);

        Record recordAfterSave = createRecord();

        when(recordRepository.save(recordBeforeSave)).thenReturn(recordBeforeSave);

        assertEquals(recordAfterSave.getId(), recordService.save(recordBeforeSave).getId());
        assertEquals(recordAfterSave.getTotalFaceValue(), recordService.save(recordBeforeSave).getTotalFaceValue());
        assertEquals(recordAfterSave.getStatus(), recordService.save(recordBeforeSave).getStatus());
    }

    @Test
    public void delete() {
        Record recordForDelete = createRecord();

        when(recordRepository.findFirstByIdNumber(777L)).thenReturn(recordForDelete);
        when(recordRepository.save(recordForDelete)).thenReturn(recordForDelete);

        assertEquals("deleted", recordService.delete(777L).getStatus());
    }


    @ParameterizedTest
    @MethodSource("fieldsArg")
    public void update(Map<String, Object> fields, Long amount, Long totalFaceValue, String comment) {
        Record record = createRecord();

        when(recordRepository.findFirstByIdNumber(777L)).thenReturn(record);
        when(recordRepository.save(record)).thenReturn(record);

        assertEquals(amount, recordService.update(777L, fields).getAmount());
        assertEquals(totalFaceValue, recordService.update(777L, fields).getTotalFaceValue());
        assertEquals(comment, recordService.update(777L, fields).getComment());
    }

    @ParameterizedTest(name = "{index} => idNumber={0}, status={1}")
    @CsvSource({
            "777, null",
            "777, active"
    })
    public void getRecords(Long idNumber, String status) {
        Record record = createRecord();
        List<Record> recordList = Collections.singletonList(record);

        when(recordRepository.findAll(any(Specification.class))).thenReturn(recordList);

        assertEquals(25L,
                recordService.getRecords(idNumber, status, Pageable.unpaged()).getContent().get(0).getTotalFaceValue());
        assertEquals(1L, recordService.getRecords(idNumber, status, Pageable.unpaged()).getTotalElements());
    }

    private Record createRecord() {
        Record record = new Record();
        record.setId(1L);
        record.setIdNumber(777L);
        record.setComment("Good job");
        record.setAmount(5L);
        record.setFaceValue(5L);
        record.setStatus("active");
        record.setTotalFaceValue(25L);
        record.setReleaseDate(LocalDateTime.now());
        return record;
    }

    static Stream<Arguments> fieldsArg() {
        return Stream.of(
                Arguments.of(new HashMap<String, Long>() {{
                    put("amount", 10L);
                }}, 10L, 50L, "Good job"),
                Arguments.of(new HashMap<String, Long>() {{
                    put("faceValue", 20L);
                }}, 5L, 100L, "Good job"),
                Arguments.of(new HashMap<String, String>() {{
                    put("comment", "Comment");
                }}, 5L, 25L, "Comment")
        );
    }
}