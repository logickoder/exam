package dev.logickoder.edvora.ui.main.exams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.logickoder.edvora.data.Classroom
import dev.logickoder.edvora.data.Examination
import dev.logickoder.edvora.data.repository.ClassroomRepository
import dev.logickoder.edvora.data.repository.ExaminationRepository
import dev.logickoder.edvora.ui.base.BaseViewModel
import dev.logickoder.edvora.ui.main.exams.CreateExamFragment.Companion.PCEI
import java.time.Instant
import java.time.LocalTime
import kotlin.random.Random
import kotlin.random.nextInt

/**
 *
 */
class CreateExamsViewModel : BaseViewModel() {

    private val _classrooms = MutableLiveData<List<Classroom>>()
    val classrooms: LiveData<List<Classroom>> get() = _classrooms

    var classroom: Classroom

    private val store = mutableMapOf<String, List<String>>()

    init {
        _classrooms.value = ClassroomRepository.getAll()
        classroom = _classrooms.value!![0]
    }

    fun save(text: String, list: List<String>) {
        store += text to list
    }

    fun restore(text: String) = store.getOrDefault(text, emptyList())

    fun save() {
        val texts = store[PCEI]!!
        val random = Random(Instant.now().epochSecond)
        val start = texts[2].toInt().let { if (it > 23) random.nextInt(0, 23) else it }
        val end = (texts[3].toInt() + start) % 23
        Examination(
            classroom,
            LocalTime.of(start, 0) to LocalTime.of(end, 0),
            texts.last().ifBlank { random.nextInt(1..100).toString() }.toInt(),
            texts[4].ifBlank { "MCQs" },
            texts[0].ifBlank { "Internal One" }
        ).also { ExaminationRepository.add(it) }
    }
}