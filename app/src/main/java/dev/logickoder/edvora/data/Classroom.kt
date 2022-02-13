package dev.logickoder.edvora.data

/**
 *
 */
class Classroom(val name: String, val code: String) {
    override fun equals(other: Any?) = (other as? Classroom)?.code.equals(code, true)
    override fun hashCode() = code.lowercase().hashCode()
}