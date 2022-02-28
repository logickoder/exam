package dev.logickoder.exams.data.repository

import dev.logickoder.exams.data.User

/**
 *
 */
object UserRepository : Repository {
    private val user = User("logickoder")

    val currentUser: User get() = user
}