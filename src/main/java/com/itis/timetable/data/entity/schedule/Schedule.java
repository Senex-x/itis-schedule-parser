package com.itis.timetable.data.entity.schedule;

import com.itis.timetable.data.entity.group.Group;
import lombok.ToString;

import java.util.List;

@ToString
public class Schedule {
    private final ScheduleEntity scheduleInfoEntity;
    private final Group groupEntity;
    private final List<DailySchedule> dailyScheduleEntities;

    public Schedule(ScheduleEntity schedule, Group groupEntity, List<DailySchedule> dailySchedules) {
        this.scheduleInfoEntity = schedule;
        this.groupEntity = groupEntity;
        this.dailyScheduleEntities = dailySchedules;
    }

    public ScheduleEntity getScheduleInfoEntity() {
        return scheduleInfoEntity;
    }

    public Group getGroupEntity() {
        return groupEntity;
    }

    public List<DailySchedule> getDailyScheduleEntities() {
        return dailyScheduleEntities;
    }
}

