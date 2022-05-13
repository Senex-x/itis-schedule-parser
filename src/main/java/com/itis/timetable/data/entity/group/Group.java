package com.itis.timetable.data.entity.group;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "groups", schema = "public")
public class Group {
    public Group(long id, String name, int courseNumber) {
        this.id = id;
        this.name = name;
        this.courseNumber = courseNumber;
    }

    @Id
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "course_number", nullable = false)
    private int courseNumber;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}