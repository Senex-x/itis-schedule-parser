package com.itis.timetable.data.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

@ToString
public class SubjectDto {
    private long id;
    private long dailyScheduleId;
    private Long electiveSubjectId;
    private Long englishSubjectId;
    private int indexInDay;
    private String startTime;
    private String endTime;
    private String name;
    private String room;
    private Type type;
    private Kind kind;
    private String teacherName; // Make nullable?
    private String teacherSurname; // Pass as a separately declared entity?
    private String teacherPatronymic;

    public SubjectDto() {}

    public SubjectDto(long id, long dailyScheduleId, @Nullable Long electiveSubjectId, @Nullable Long englishSubjectId, int indexInDay, String startTime, String endTime, String name, String room, Type type, Kind kind, String teacherName, String teacherSurname, String teacherPatronymic) {
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