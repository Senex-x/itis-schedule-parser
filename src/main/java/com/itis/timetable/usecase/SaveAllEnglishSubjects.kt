package com.itis.timetable.usecase

import com.itis.timetable.data.entity.subject.EnglishSubject
import com.itis.timetable.data.repositories.EnglishSubjectRepository
import org.springframework.stereotype.Component

@Component
class SaveAllEnglishSubjects(
    private val englishSubjectRepository: EnglishSubjectRepository,
) {
    operator fun invoke(
        englishSubjects: List<EnglishSubject>,
    ) = englishSubjects.forEach {
        englishSubjectRepository.save(it)
    }
}