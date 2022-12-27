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
 * @file JBasicInterpreterState.java
 * @brief Stores the state of the interpreter.
 * @details Used to store variables that are declared in the script and subroutines.
 */

package org.jbasic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @brief Stores the state of the interpreter.
 * @details Used to store variables that are declared in the script and subroutines.
 */
public class JBasicInterpreterState {

    /// The hashtable, that stores all the variables with the name of the variable as the key of the entry
    private Map<String, JBasicValue> memory = new HashMap<>();

    /// Stores the defined functions of the executed script
    private final Map<String, JBasicSubroutine> subroutines = new HashMap<>();

    /**
     * Assigns another value to a specific variable in memory
     *
     * @param name  The name of the variable that is changed
     * @param value The new value of the variable
     */
    public void assignToVariable(String name, JBasicValue value) {
        memory.put(name, value);
    }

    /**
     * @param subroutineName
     * @param arguments
     * @param visitor
     */
    public void callSubroutine(String subroutineName, List<JBasicValue> arguments, JBasicVisitor visitor) {
        if (!subroutines.containsKey(subroutineName)) {
            throw new SubroutineNotDefinedException("A subroutine with the name" + subroutineName + " is not defined in the script");
        }
        JBasicSubroutine subroutine = subroutines.get(subroutineName);
        if (subroutine.getArity() != arguments.size()) {
            throw new SubroutineArityException("Subroutine expects " + subroutine.getArguments().length +
                    "arguments but was called with" + arguments.size());
        }
        final Map<String, JBasicValue> oldMemoryState = this.memory;
        // Prepare subroutine memory
        this.memory = new HashMap<>();
        for (int i = 0; i < subroutine.getArity(); i++) {
            this.assignToVariable(subroutine.getArguments()[i], arguments.get(i));
        }
        visitor.visit(subroutine.getSubroutineBody());
        // Reset memory to the old state
        this.memory = oldMemoryState;
    }

    /**
     * @param functionName
     * @param function
     */
    public void defineSubroutine(String functionName, JBasicSubroutine function) {
        if (subroutines.containsKey(functionName)) {
            throw new SubroutineRedefinitionException("A subroutine with the name" + functionName + " is already defined in the script");
        }
        subroutines.put(functionName, function);
    }

    /**
     * @brief Free's the memory
     * @details Deallocates all the memory used by a memory object instance
     */
    public void freeMemory() {
        memory.clear();
    }

    /**
     * Gets a specific variable from memory
     *
     * @param name The name of the variable that is obtained
     */
    public JBasicValue getVariable(String name) {
        return memory.get(name);
    }

}
