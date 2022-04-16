package com.itis.timetable.data.models.schedule;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "daily_schedules")
public class DailyScheduleEntity {
    @Id
    private long id;
    @Column(name = "schedule_id", nullable = false)
    private long scheduleId;
    @Column(name = "day_name", nullable = false)
    private String dayName;
    @Column(name = "index_in_week", nullable = false)
    private int indexInWeek;

    public DailyScheduleEntity(long id, long scheduleId, String dayName, int indexInWeek) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.dayName = dayName;
        this.indexInWeek = indexInWeek;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public int getIndexInWeek() {
        return indexInWeek;
    }

    public void setIndexInWeek(int indexInWeek) {
        this.indexInWeek = indexInWeek;
    }
}
