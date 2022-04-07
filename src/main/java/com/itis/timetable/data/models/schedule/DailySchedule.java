package com.itis.timetable.data.models.schedule;

import com.itis.timetable.data.models.subject.Subject;
import lombok.ToString;

import java.util.List;

@ToString
public class DailySchedule {
    private final DailyScheduleEntity dailyScheduleInfoEntity;
    private final List<Subject> subjectEntities;

    public DailySchedule(DailyScheduleEntity dailyScheduleInfoEntity, List<Subject> subjectEntities) {
        this.dailyScheduleInfoEntity = dailyScheduleInfoEntity;
        this.subjectEntities = subjectEntities;
    }

    public DailyScheduleEntity getDailyScheduleInfoEntity() {
        return dailyScheduleInfoEntity;
    }

    public List<Subject> getSubjectEntities() {
        return subjectEntities;
    }
}
