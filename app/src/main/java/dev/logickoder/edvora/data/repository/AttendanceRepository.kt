package dev.logickoder.edvora.data.repository

import dev.logickoder.edvora.data.Attendance
import dev.logickoder.edvora.data.Classroom
import dev.logickoder.edvora.data.User
import java.time.Instant
import kotlin.random.Random

/**
 *
 */
object AttendanceRepository : Repository {
    private val attendance = buildMap {
        val random = Random(Instant.now().epochSecond)
        ClassroomRepository.getAll().forEach {
            val total = random.nextInt(1, 100)
            val attended = random.nextInt(total)
            val missed = random.nextInt(total - attended)
            put(it, Attendance(total, attended, missed))
        }
        put(ClassroomRepository.getAll()[0], Attendance(70, 6, 34))
    }

    fun getAttendanceFor(user: User, classroom: Classroom) = attendance[classroom]
}