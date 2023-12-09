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

package tech.ixirsii.year2023.day01

import arrow.core.fold
import tech.ixirsii.logging.Logging
import tech.ixirsii.logging.LoggingImpl

class Trebuchet : Logging by LoggingImpl<Trebuchet>() {
    fun partOne(): Long? {
        val result: Long? = this.javaClass.getResourceAsStream("/2023/01/input.txt")
            ?.bufferedReader()
            ?.useLines { lines ->
                lines.map { calibrate(it) }.sum()
            }

        log.info("Part One: {}", result)

        return result
    }

    fun partTwo(): Long? {
        val result: Long? = this.javaClass.getResourceAsStream("/2023/01/input.txt")
            ?.bufferedReader()
            ?.useLines { lines ->
                lines.map { calibrate2(it) }.sum()
            }

        log.info("Part Two: {}", result)

        return result
    }

    /* *************************************** Private utility functions **************************************** */

    private fun calibrate(input: String): Long {
        log.debug("Calibrating input: {}", input)

        var first: Long? = null
        var last: Long? = null

        for (c: Char in input) {
            if (c.isDigit()) {
                if (first == null) {
                    first = c.toString().toLong()
                }

                last = c.toString().toLong()
            }
        }

        val calibrated: Long = (first ?: 0) * 10 + (last ?: 0)

        log.debug("Calibrated input: {}", calibrated)

        return calibrated
    }

    private fun calibrate2(input: String): Long {
        log.debug("Calibrating input: {}", input)

        var first: Long? = null
        var last: Long? = null

        for (i: Int in input.indices) {
            if (input.elementAt(i).isDigit()) {
                val digit: Long = input.elementAt(i).toString().toLong()

                if (first == null) {
                    first = digit
                }

                last = digit
            } else {
                for (j: Int in (i + 2) .. input.length) {
                    if (NUMBERS.containsKey(input.substring(i, j))) {
                        val digit: Long = NUMBERS[input.substring(i, j)]!!

                        if (first == null) {
                            first = digit
                        }

                        last = digit
                    }
                }
            }
        }

        val calibrated: Long = (first ?: 0) * 10 + (last ?: 0)

        log.debug("Calibrated input: {}", calibrated)

        return calibrated
    }

    companion object {
        private val NUMBERS: Map<String, Long> = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )
    }
}
