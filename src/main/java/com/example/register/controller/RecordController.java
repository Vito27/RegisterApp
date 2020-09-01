package com.example.register.controller;

import com.example.register.model.Record;
import com.example.register.service.RecordService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/record")
public class RecordController {

    private final RecordService recordService;

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> saveRecord(@RequestBody Record record) {
        return ResponseEntity.ok(recordService.save(record));
    }

    @PatchMapping(value = "/update/{idNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> updateRecord(@PathVariable Long idNumber, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(recordService.update(idNumber, fields));
    }

    @PatchMapping(value = "/remove/{idNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> removeRecord(@PathVariable Long idNumber) {
        return ResponseEntity.ok(recordService.delete(idNumber));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Page<Record>> getRecords(@RequestParam(required = false) Long idNumber,
            @RequestParam(required = false) String status,
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(recordService.getRecords(idNumber, status, pageable));
    }
}
