package com.itis.timetable.data.models.schedule;

import com.itis.timetable.data.models.subject.EnglishSubject;
import com.itis.timetable.data.models.subject.Subject;
import com.itis.timetable.data.models.subject.VariedSubject;
import lombok.ToString;

import java.util.List;

@ToString
public class DailySchedule {
    private final DailyScheduleEntity dailyScheduleInfoEntity;
    private final List<VariedSubject> electiveSubjectEntities;
    private final List<EnglishSubject> englishSubjectEntities;
    private final List<Subject> subjectEntities;

    public DailySchedule(DailyScheduleEntity dailyScheduleInfoEntity, List<VariedSubject> electiveSubjectEntities, List<EnglishSubject> englishSubjectEntities, List<Subject> subjectEntities) {
        this.dailyScheduleInfoEntity = dailyScheduleInfoEntity;
        this.electiveSubjectEntities = electiveSubjectEntities;
        this.englishSubjectEntities = englishSubjectEntities;
        this.subjectEntities = subjectEntities;
    }

    public DailyScheduleEntity getDailyScheduleInfoEntity() {
        return dailyScheduleInfoEntity;
    }

    public List<EnglishSubject> getEnglishSubjectEntities() {
        return englishSubjectEntities;
    }

    public List<Subject> getSubjectEntities() {
        return subjectEntities;
    }

    public List<VariedSubject> getElectiveSubjectEntities() {
        return electiveSubjectEntities;
    }
}
