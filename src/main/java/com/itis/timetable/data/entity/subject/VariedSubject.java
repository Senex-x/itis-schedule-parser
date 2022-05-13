package com.itis.timetable.data.entity.subject;

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
    @Column(name = "daily_schedule_id")
    private long dailyScheduleId;

    public VariedSubject(long id, long dailyScheduleId) {
        this.id = id;
        this.dailyScheduleId = dailyScheduleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDailyScheduleId() {
        return dailyScheduleId;
    }

    public void setDailyScheduleId(long dailyScheduleId) {
        this.dailyScheduleId = dailyScheduleId;
    }
}
