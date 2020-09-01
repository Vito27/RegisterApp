package com.example.register.model;

import com.sun.istack.NotNull;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@Entity
@Table(name = "change_history", schema = "register")
@AttributeOverride(name = "id", column = @Column(name = "change_history_id"))
public class ChangeHistory extends BaseEntity {

    @NotNull
    @JoinColumn(name = "id_number", referencedColumnName = "id_number")
    private Long idNumber;

    @NotNull
    @Column(name = "field_name")
    @BatchSize(size = 50)
    private String fieldName;

    @NotNull
    @Column(name = "old_value")
    @BatchSize(size = 255)
    private String oldValue;

    @NotNull
    @Column(name = "new_value")
    @BatchSize(size = 255)
    private String newValue;

}
