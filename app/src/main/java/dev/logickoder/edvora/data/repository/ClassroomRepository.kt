package dev.logickoder.edvora.data.repository

import dev.logickoder.edvora.data.Classroom

/**
 *
 */
object ClassroomRepository : DataRepository<Classroom>() {
    private val classrooms = buildList {
        add(Classroom("Data Structures & Algorithms", "DSA"))
        add(Classroom("Object Oriented Programming in Java", "OOJ"))
        add(Classroom("Kotlin in Action", "KIA"))
        add(Classroom("Groovy in Action", "GIA"))
    }.toMutableList()

    override fun getAll(): List<Classroom> = classrooms

    override fun add(vararg t: Classroom) {
        classrooms += t
    }
}