package com.itis.timetable.data.models.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "daily_schedules")
public class DailyScheduleEntity {
    @Id
    private long id;
    @Column(name = "schedule_id")
    private long scheduleId;
    @Column(name = "day_name")
    private String dayName;
    @Column(name = "index__in_week")
    private int indexInWeek;

    public DailyScheduleEntity(long id, long scheduleId, String dayName, int indexInWeek) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.dayName = dayName;
        this.indexInWeek = indexInWeek;
    }
}
