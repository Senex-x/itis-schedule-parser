package com.itis.timetable.data.entity.schedule;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
