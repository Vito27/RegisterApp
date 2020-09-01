package com.example.register.model.repo;

import com.example.register.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends BaseRepository<Record> {

    Record findFirstByIdNumber(Long idNumber);

    Page<Record> findAll(Pageable pageable);

}