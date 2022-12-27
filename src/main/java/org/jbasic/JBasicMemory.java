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
 * @file Memory.java
 * @brief A very simple memory model implemented with a hashmap.
 */

package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @brief A very simple memory model implemented with a hashmap.
 */
public class JBasicMemory {

    /// The hashtable, that stores all the variables with the name of the variable as the key of the entry
    private final Map<String, JBasicValue> memory = new HashMap<>();

    private final Map<String, ParserRuleContext> labels = new HashMap<>();

    /**
     * Assigns another value to a specific variable in memory
     *
     * @param name  The name of the variable that is changed
     * @param value The new value of the variable
     */
    public void assign(String name, JBasicValue value) {
        memory.put(name, value);
    }

    /**
     * @brief Free's the memory
     * @details Deallocates all the memory used by a memory object instance
     */
    public void free() {
        memory.clear();
    }

    /**
     * Gets a specific variable from memory
     *
     * @param name The name of the variable that is obtained
     */
    public JBasicValue get(String name) {
        return memory.get(name);
    }

}
