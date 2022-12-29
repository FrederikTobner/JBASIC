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
 * @file FunctionSafeguard.java
 * @brief Guarding functions for JBASIC functions.
 */

package org.jbasic.core.guard;

import jbasic.JBasicParser;
import org.jbasic.error.functions.FunctionArityException;

import java.util.function.Function;

/**
 * @brief Guarding functions for JBASIC functions.
 */
public class FunctionSafeguard {

    /**
     * Ensures the arity of a function upon invocation is met
     * @param functionName The name of the function were the arity is ensured upon invocation
     * @param context The function call arguments parsing context
     * @param guard The guard function, that is applied to the number of arguments that were used when the function was invoked.
     */
    public static void guaranteeArityIsNotViolated(String functionName,
                                                   JBasicParser.FunctionCallArgsContext context,
                                                   Function<Integer, Boolean> guard) {
        if (!guard.apply(context.expression().size())) {
            throw new FunctionArityException(functionName + " can not be called with" + context.expression().size() + "arguments", context);
        }
    }
}
