package com.itis.timetable.data.models.subject;

import lombok.*;
import org.hibernate.Hibernate;

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
    @Column(name = "varied_subject_id")
    private Long variedSubjectId;
    @Column(name = "number_in_day")
    private int numberInDay;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    private String name;
    private String room;
    private SubjectType type;
    @Column(name = "is_on_even_weeks")
    private Boolean isOnEvenWeeks;
    @Column(name = "is_on_odd_weeks")
    private Boolean isOnOddWeeks;
    @Column(name = "teacher_name")
    private String teacherName;
    @Column(name = "teacher_surname")
    private String teacherSurname;
    @Column(name = "teacher_patronymic")
    private String teacherPatronymic;

    private Boolean isOnEveryWeek() {
        return isOnEvenWeeks && isOnOddWeeks;
    }

    public Subject(long id, long dailyScheduleId, Long variedSubjectId, int numberInDay, String startTime, String endTime, String name, String room, SubjectType type, Boolean isOnEvenWeeks, Boolean isOnOddWeeks, String teacherName, String teacherSurname, String teacherPatronymic) {
        this.id = id;
        this.dailyScheduleId = dailyScheduleId;
        this.variedSubjectId = variedSubjectId;
        this.numberInDay = numberInDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.room = room;
        this.type = type;
        this.isOnEvenWeeks = isOnEvenWeeks;
        this.isOnOddWeeks = isOnOddWeeks;
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

    public Long getVariedSubjectId() {
        return variedSubjectId;
    }

    public void setVariedSubjectId(Long variedSubjectId) {
        this.variedSubjectId = variedSubjectId;
    }

    public int getNumberInDay() {
        return numberInDay;
    }

    public void setNumberInDay(int numberInDay) {
        this.numberInDay = numberInDay;
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

    public SubjectType getType() {
        return type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public Boolean getOnEvenWeeks() {
        return isOnEvenWeeks;
    }

    public void setOnEvenWeeks(Boolean onEvenWeeks) {
        isOnEvenWeeks = onEvenWeeks;
    }

    public Boolean getOnOddWeeks() {
        return isOnOddWeeks;
    }

    public void setOnOddWeeks(Boolean onOddWeeks) {
        isOnOddWeeks = onOddWeeks;
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
}