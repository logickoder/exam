package dev.logickoder.exams.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.logickoder.exams.data.Attendance
import dev.logickoder.exams.data.Classroom
import dev.logickoder.exams.data.Examination
import dev.logickoder.exams.data.User
import dev.logickoder.exams.data.repository.AttendanceRepository
import dev.logickoder.exams.data.repository.ClassroomRepository
import dev.logickoder.exams.data.repository.ExaminationRepository
import dev.logickoder.exams.data.repository.UserRepository
import dev.logickoder.exams.ui.base.BaseViewModel

/**
 *
 */
class DashboardViewModel : BaseViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _examination = MutableLiveData<List<Examination>>()
    val examinations: LiveData<List<Examination>> get() = _examination

    private val _attendance = MutableLiveData<List<Pair<Classroom, Attendance?>>>()
    val attendance: LiveData<List<Pair<Classroom, Attendance?>>> get() = _attendance

    init {
        _user.value = UserRepository.currentUser
        _attendance.value = ClassroomRepository.getAll().map {
            it to AttendanceRepository.getAttendanceFor(UserRepository.currentUser, it)
        }
    }

    fun getExamination() {
        _examination.value = ExaminationRepository.getAll()
    }
}