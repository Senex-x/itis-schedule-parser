package com.itis.timetable.data.models.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "schedules")
public class ScheduleEntity {
    @Id
    private long id;
    @Column(name = "group_id")
    private long groupId;

    public ScheduleEntity(long id, long groupId) {
        this.id = id;
        this.groupId = groupId;
    }
}
