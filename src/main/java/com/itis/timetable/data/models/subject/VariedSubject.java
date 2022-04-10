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
public class VariedSubject {
    @Id
    private long id;
    @Column(nullable = false)
    private String name;

    public VariedSubject(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String type) {
        this.name = type;
    }
}
