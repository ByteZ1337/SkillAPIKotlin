package com.sucy.skill.util.math

import kotlin.math.PI

/**
 * Assumes [this] is a time measured in seconds and converts [this]
 * to ticks (1/20 of a second)
 */
fun Double.toTicks(): Long {
    return (this * 20).toLong()
}

/**
 * Assumes [this] is a time measured in seconds and converts [this]
 * to milliseconds (1/1000 of a second)
 */
fun Double.toMillis(): Long {
    return (this * 1000).toLong()
}

/**
 * Converts a coordinate into a chunk coordinate
 */
fun Double.toChunk(): Int {
    return this.toInt() shr 4
}

fun Double.toRadians(): Double {
    return this * PI / 180
}

fun Double.toDegrees(): Double {
    return this * 180 / PI
}
