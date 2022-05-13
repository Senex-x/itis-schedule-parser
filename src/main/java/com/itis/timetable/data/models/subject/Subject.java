package com.itis.timetable.data.models.subject;

import lombok.*;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "subjects", schema = "public")
public class Subject {
    @Id
    private long id;
    @Column(name = "daily_schedule_id")
    private long dailyScheduleId;
    @Nullable
    @Column(name = "varied_subject_id")
    private Long electiveSubjectId;
    @Nullable
    @Column(name = "english_subject_id")
    private Long englishSubjectId;
    @Column(name = "index_in_day", nullable = false)
    private int indexInDay;
    @Column(name = "start_time", nullable = false)
    private String startTime;
    @Column(name = "end_time", nullable = false)
    private String endTime;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String room;
    @Column(nullable = false)
    private Type type;
    @Column(nullable = false)
    private Kind kind;
    @Column(name = "teacher_name")
    private String teacherName; // Make nullable?
    @Column(name = "teacher_surname")
    private String teacherSurname; // Pass as a separately declared entity?
    @Column(name = "teacher_patronymic")
    private String teacherPatronymic;

    public Subject(long id, long dailyScheduleId, @Nullable Long electiveSubjectId, @Nullable Long englishSubjectId, int indexInDay, String startTime, String endTime, String name, String room, Type type, Kind kind, String teacherName, String teacherSurname, String teacherPatronymic) {
        this.id = id;
        this.dailyScheduleId = dailyScheduleId;
        this.electiveSubjectId = electiveSubjectId;
        this.englishSubjectId = englishSubjectId;
        this.indexInDay = indexInDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.room = room;
        this.type = type;
        this.kind = kind;
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
        this.teacherPatronymic = teacherPatronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDailyScheduleId() {
        return dailyScheduleId;
    }

    public void setDailyScheduleId(long dailyScheduleId) {
        this.dailyScheduleId = dailyScheduleId;
    }

    @Nullable
    public Long getElectiveSubjectId() {
        return electiveSubjectId;
    }

    public void setElectiveSubjectId(@Nullable Long variedSubjectId) {
        this.electiveSubjectId = variedSubjectId;
    }

    @Nullable
    public Long getEnglishSubjectId() {
        return englishSubjectId;
    }

    public void setEnglishSubjectId(@Nullable Long englishSubjectId) {
        this.englishSubjectId = englishSubjectId;
    }

    public int getIndexInDay() {
        return indexInDay;
    }

    public void setIndexInDay(int numberInDay) {
        this.indexInDay = numberInDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public String getTeacherPatronymic() {
        return teacherPatronymic;
    }

    public void setTeacherPatronymic(String teacherPatronymic) {
        this.teacherPatronymic = teacherPatronymic;
    }

    public enum Type {
        LECTURE,
        SEMINAR,
        UNDEFINED,
    }

    public enum Kind {
        ORDINARY,
        PHYSICAL,
        ENGLISH,
        ELECTIVE,
        BLOCK,
        EMPTY,
    }
}