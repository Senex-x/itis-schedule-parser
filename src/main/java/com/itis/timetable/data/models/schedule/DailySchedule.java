package com.itis.timetable.data.models.schedule;

import com.itis.timetable.data.models.subject.Subject;
import lombok.ToString;

import java.util.List;

@ToString
public class DailySchedule {
    private final DailyScheduleEntity dailyScheduleEntity;
    private final List<Subject> subjects;

    public DailySchedule(DailyScheduleEntity dailyScheduleEntity, List<Subject> subjects) {
        this.dailyScheduleEntity = dailyScheduleEntity;
        this.subjects = subjects;
    }

    public DailyScheduleEntity getDailyScheduleEntity() {
        return dailyScheduleEntity;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
