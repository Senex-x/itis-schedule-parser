package com.itis.timetable.data.models.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyScheduleEntity {
    private long id;
    private long scheduleId;
    private String dayName;
    private int indexInWeek;

    public DailyScheduleEntity(long id, long scheduleId, String dayName, int indexInWeek) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.dayName = dayName;
        this.indexInWeek = indexInWeek;
    }
}
