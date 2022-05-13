package com.itis.timetable.data.mapper

import com.itis.timetable.data.entity.subject.Subject
import com.itis.timetable.data.model.SubjectDto
import org.mapstruct.Mapper

@Mapper
interface SubjectMapper {
    fun toDto(source: Subject?): SubjectDto?
    fun toEntity(destination: SubjectDto?): Subject?
}