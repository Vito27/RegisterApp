package com.example.register.model;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;


@Getter
@Setter
@Entity
@Table(name = "record", schema = "register")
@ToString
@EqualsAndHashCode(exclude = "comment", callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "record_id"))
public class Record extends BaseEntity {

    @Column(name = "comment")
    @BatchSize(size = 255)
    private String comment;

    @NotNull
    @Column(name = "id_number", unique = true)
    private Long idNumber;

    @NotNull
    @Column(name = "amount")
    private Long amount;

    @NotNull
    @Column(name = "total_face_value")
    private Long totalFaceValue;

    @NotNull
    @Column(name = "face_value")
    private Long faceValue;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @NotNull
    @Column(name = "status")
    @BatchSize(size = 20)
    private String status;
}