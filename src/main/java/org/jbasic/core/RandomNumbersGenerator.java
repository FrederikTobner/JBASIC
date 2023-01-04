/****************************************************************************
 * Copyright (C) 2022 by Frederik Tobner                                    *
 *                                                                          *
 * This file is part of JBASIC.                                             *
 *                                                                          *
 * Permission to use, copy, modify, and distribute this software and its    *
 * documentation under the terms of the GNU General Public License is       *
 * hereby granted.                                                          *
 * No representations are made about the suitability of this software for   *
 * any purpose.                                                             *
 * It is provided "as is" without express or implied warranty.              *
 * See the <"https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public  *
 * License for more details.                                                *
 ****************************************************************************/

/**
 * @file RandomNumbersGenerator.java
 * @brief Contains utility methods to create random numbers.
 */

package org.jbasic.core;

import java.util.Random;

/**
 * @brief Contains utility methods to create random numbers.
 */
public class RandomNumbersGenerator {

    /// The underlying random number generator from java.util.Random
    private static final Random randomNumberGenerator = new Random();

    /**
     * Generates a random double precision floating point number
     *
     * @param minimalValue The minimal value
     * @param maximumValue The maximum value
     * @return The generated random number
     */
    public static double doubleRandomWithinRange(double minimalValue, double maximumValue) {
        return minimalValue + (maximumValue - minimalValue) * randomNumberGenerator.nextDouble();
    }
}
