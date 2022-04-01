package com.itis.timetable.data.models.schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleEntity {
    private long id;
    private long groupId;

    public ScheduleEntity(long id, long groupId) {
        this.id = id;
        this.groupId = groupId;
    }
}
