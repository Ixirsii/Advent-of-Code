/*
 * Copyright (c) Ryan Porterfield 2023.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of Advent-of-Code nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package tech.ixirsii.year2023.day02

import tech.ixirsii.logging.Logging
import tech.ixirsii.logging.LoggingImpl

class CubeConundrum : Logging by LoggingImpl<CubeConundrum>() {
    fun partOne(): Int? {
        val possible: Int? = this.javaClass.getResourceAsStream("/2023/02/input.txt")
            ?.bufferedReader()
            ?.useLines { lines ->
                lines.map {
                    val id: Int = getID(it)
                    val handfuls: List<String> = handfuls(it)
                    val possible: Boolean = handfuls.all { handful -> isHandfulPossible(handful) }

                    if (possible) id else 0
                }.sum()
            }

        log.info("Part one result is {}", possible)

        return possible
    }

    fun partTwo(): Long? {
        val power: Long? = this.javaClass.getResourceAsStream("/2023/02/input.txt")
            ?.bufferedReader()
            ?.useLines { lines ->
                lines.map { handfulPower(handfuls(it)) }.sum()
            }

        log.info("Part two result is {}", power)

        return power
    }

    /* *************************************** Private utility functions **************************************** */

    private fun getHandful(input: String): Handful {
        val red: String = "(\\d+) red".toRegex().find(input)?.groupValues?.get(1) ?: "0"
        val redCount: Int = red.toInt()
        val green: String = "(\\d+) green".toRegex().find(input)?.groupValues?.get(1) ?: "0"
        val greenCount: Int = green.toInt()
        val blue: String = "(\\d+) blue".toRegex().find(input)?.groupValues?.get(1) ?: "0"
        val blueCount: Int = blue.toInt()

        return Handful(red = redCount, green = greenCount, blue = blueCount)
    }

    private fun getID(line: String): Int {
        val (idMatch: String) = "Game (\\d+):".toRegex().find(line)!!.destructured
        val id: Int = idMatch.toInt()

        log.debug("ID for line {} is {}", line, id)

        return id
    }

    private fun handfuls(input: String): List<String> {
        val result: List<String> = input.substringAfter(':').trim().split(';')

        log.debug("Handfuls for line {} are {}", input, result)

        return result
    }

    private fun handfulPower(input: List<String>): Long {
        val handfuls: List<Handful> = input.map { getHandful(it) }
        val red = handfuls.fold(0L) { acc: Long, handful: Handful ->
            if (handful.red > acc) handful.red.toLong() else acc
        }
        val green = handfuls.fold(0L) { acc: Long, handful: Handful ->
            if (handful.green > acc) handful.green.toLong() else acc
        }
        val blue = handfuls.fold(0L) { acc: Long, handful: Handful ->
            if (handful.blue > acc) handful.blue.toLong() else acc
        }
        val power: Long = red * green * blue

        log.debug("Power for handfuls {} is {}", input, power)

        return power
    }

    private fun isHandfulPossible(input: String): Boolean {
        val handful: Handful = getHandful(input)
        val result: Boolean = (handful.red < 13) && (handful.green < 14) && (handful.blue < 15)

        log.debug("Handful {} is possible: {}", handful, result)

        return result
    }
}
