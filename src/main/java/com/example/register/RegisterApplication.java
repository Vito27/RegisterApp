package com.example.register;

import com.example.register.model.Record;
import com.example.register.model.repo.RecordRepository;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegisterApplication implements ApplicationRunner {

    @Autowired
    RecordRepository recordRepository;

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/main/resources/RecordData.json"));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray recordsJsonList = (JSONArray) jsonObject.get("content");
        Iterator<JSONObject> iterator = recordsJsonList.iterator();
        List<Record> recordList = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject jsonValue = iterator.next();
            Record record = new Record();
            record.setTotalFaceValue((Long) jsonValue.get("totalFaceValue"));
            record.setFaceValue((Long) jsonValue.get("faceValue"));
            record.setReleaseDate(LocalDateTime.now());
            record.setAmount((Long) jsonValue.get("amount"));
            record.setIdNumber((Long) jsonValue.get("idNumber"));
            record.setStatus((String) jsonValue.get("status"));
            record.setComment((String) jsonValue.get("comment"));
            System.out.println(record);
            recordList.add(record);
        }
        recordRepository.saveAll(recordList);
    }
}