package com.itis.timetable.data.models.schedule;

import com.itis.timetable.data.models.group.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Schedule {
    private ScheduleEntityKt schedule;
    private Group group;
    private List<DailySchedule> dailySchedules;

    public Schedule(ScheduleEntityKt schedule, Group group, List<DailySchedule> dailySchedules) {
        this.schedule = schedule;
        this.group = group;
        this.dailySchedules = dailySchedules;
    }
}

