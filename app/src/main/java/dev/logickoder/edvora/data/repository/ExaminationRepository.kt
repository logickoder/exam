package dev.logickoder.edvora.data.repository

import dev.logickoder.edvora.data.Examination
import java.time.Instant
import java.time.LocalTime
import kotlin.random.Random
import kotlin.random.nextInt

/**
 *
 */
object ExaminationRepository : DataRepository<Examination>() {
    private val examinations = buildList {
        val random = Random(Instant.now().epochSecond)
        ClassroomRepository.getAll().forEach {
            val start = random.nextInt(0, 23)
            val end = if (start == 23) random.nextInt(0, 22) else random.nextInt(start + 1, 23)
            add(
                Examination(
                    it,
                    LocalTime.of(start, 0) to LocalTime.of(end, 0),
                    random.nextInt(1..100),
                    "MCQs",
                    "Internal One"
                )
            )
        }
    }.toMutableList()

    override fun getAll(): List<Examination> = examinations

    override fun add(vararg t: Examination) {
        examinations += t
    }
}