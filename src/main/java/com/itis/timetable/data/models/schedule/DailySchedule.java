package com.itis.timetable.data.models.schedule;

import com.itis.timetable.data.models.subject.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DailySchedule {
    private DailyScheduleEntity dailyScheduleEntity;
    private List<Subject> subjects;

    public DailySchedule(DailyScheduleEntity dailyScheduleEntity, List<Subject> subjects) {
        this.dailyScheduleEntity = dailyScheduleEntity;
        this.subjects = subjects;
    }
}
