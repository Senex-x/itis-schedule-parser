package com.itis.timetable.usecase

import com.itis.timetable.data.entity.subject.VariedSubject
import com.itis.timetable.data.repositories.VariedSubjectRepository
import org.springframework.stereotype.Component

@Component
class SaveAllVariedSubjects(
    private val variedSubjectRepository: VariedSubjectRepository,
) {
    operator fun invoke(
        variedSubjects: List<VariedSubject>
    ) = variedSubjects.forEach {
        variedSubjectRepository.save(it)
    }
}