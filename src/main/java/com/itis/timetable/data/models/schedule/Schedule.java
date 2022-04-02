package com.itis.timetable.data.models.schedule;

import com.itis.timetable.data.models.group.Group;
import lombok.ToString;

import java.util.List;

@ToString
public class Schedule {
    private final ScheduleEntity schedule;
    private final Group group;
    private final List<DailySchedule> dailySchedules;

    public Schedule(ScheduleEntity schedule, Group group, List<DailySchedule> dailySchedules) {
        this.schedule = schedule;
        this.group = group;
        this.dailySchedules = dailySchedules;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public Group getGroup() {
        return group;
    }

    public List<DailySchedule> getDailySchedules() {
        return dailySchedules;
    }
}

