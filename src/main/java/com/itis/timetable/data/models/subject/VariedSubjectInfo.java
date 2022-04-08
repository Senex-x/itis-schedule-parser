package com.itis.timetable.data.models.subject;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "varied_subjects")
public class VariedSubjectInfo {
    @Id
    private long id;
    @Column(nullable = false)
    private String type;

    public VariedSubjectInfo(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
