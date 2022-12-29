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
 * @file Trigonometry.java
 * @brief Trigonometric functions.
 */

package org.jbasic.core;

/**
 * @brief Trigonometric functions.
 */
public class Trigonometry {
    /**
     * Area tangent hyperbolicus, the inverse functions of tangent hyperbolicus
     *
     * @param value The value applied to the function
     */
    public static double areaTangentHyperbolicus(double value) {
        final double multiplicand;
        if (Double.doubleToRawLongBits(value) < 0) {
            value = Math.abs(value);
            multiplicand = -0.5d;
        }
        else {
            multiplicand = 0.5d;
        }
        return multiplicand * Math.log((1.0d + value) / (1.0d - value));
    }
}
