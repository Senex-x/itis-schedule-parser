package com.itis.timetable.data.models.subject;

import lombok.ToString;

import java.util.List;

@ToString
class VariedSubject {
    VariedSubjectInfo variedSubjectInfo;
    List<Subject> subjects;
}