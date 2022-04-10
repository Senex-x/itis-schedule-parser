package com.itis.timetable.data.models.subject;

import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public VariedSubject(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
