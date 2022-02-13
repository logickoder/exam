package dev.logickoder.edvora.data.repository

import dev.logickoder.edvora.data.User

/**
 *
 */
object UserRepository : Repository {
    private val user = User("logickoder")

    val currentUser: User get() = user
}