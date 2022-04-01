package com.itis.timetable.data.models.subject;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "subjects", schema = "public")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "daily_schedule_id")
    private long dailyScheduleId;
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

    public Subject(long id, long dailyScheduleId, int numberInDay, String startTime, String endTime, String name, String room, SubjectType type, Boolean isOnEvenWeeks, Boolean isOnOddWeeks, String teacherName, String teacherSurname, String teacherPatronymic) {
        this.id = id;
        this.dailyScheduleId = dailyScheduleId;
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
}
