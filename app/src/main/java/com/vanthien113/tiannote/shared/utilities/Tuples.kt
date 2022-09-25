package com.vanthien113.tiannote.shared.utilities

import java.io.Serializable

data class CoupleObject<out A, out B>(val first: A, val second: B) : Serializable {
    override fun toString(): String = "($first, $second)"
}

fun <T> CoupleObject<T, T>.toList(): List<T> = listOf(first, second)


data class TripleObject<out A, out B, out C>(val first: A, val second: B, val third: C) : Serializable {
    override fun toString(): String = "($first, $second, $third)"
}

fun <T> TripleObject<T, T, T>.toList(): List<T> = listOf(first, second, third)


data class QuadObject<out A, out B, out C, out D>(val first: A, val second: B, val third: C, val fourth: D) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

fun <T> QuadObject<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

data class PentadObject<out A, out B, out C, out D, out E>(val first: A, val second: B, val third: C, val fourth: D, val fifth: E) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

fun <T> PentadObject<T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth)