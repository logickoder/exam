package dev.logickoder.edvora.data.repository

/**
 *
 */
abstract class DataRepository<T> : Repository {
    abstract fun getAll(): List<T>
    abstract fun add(vararg t: T)
}