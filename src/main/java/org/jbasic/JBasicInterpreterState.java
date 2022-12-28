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

import basic.JBasicParser;

import java.util.Arrays;
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
        this.memory.put(name, value);
    }

    /**
     * Defines a new subroutine
     *
     * @param subroutineName The name of the subroutine
     * @param subroutine     The subroutine that is defined
     */
    public void defineSubroutine(String subroutineName, JBasicSubroutine subroutine, JBasicParser.SubroutineDefinitionStatementContext context) {
        if (this.subroutines.containsKey(subroutineName)) {
            throw new SubroutineRedefinitionException("A subroutine with the name" + subroutineName + " is already defined in the script", context);
        }
        this.subroutines.put(subroutineName, subroutine);
    }

    /**
     * @brief Free's the memory
     * @details Deallocates all the memory used by a memory object instance
     */
    public void freeMemory() {
        this.memory.clear();
    }

    /**
     * Gets a specific variable from memory
     *
     * @param name The name of the variable that is obtained
     */
    public JBasicValue getVariable(String name) {
        return this.memory.get(name);
    }

    /**
     * Invokes a subroutine
     *
     * @param subroutineName The name of the subroutine
     * @param arguments      The arguments of the subroutine call
     * @param visitor        The visitor of the subroutine call. Used to visit the subroutine body
     */
    public void invokeSubroutine(String subroutineName, List<JBasicValue> arguments, JBasicVisitor visitor,
                                 JBasicParser.SubroutineInvocationStatementContext context) {
        if (!this.subroutines.containsKey(subroutineName)) {
            throw new SubroutineNotDefinedException("A subroutine with the name" + subroutineName + " is not defined in the script", context);
        }
        JBasicSubroutine subroutine = this.subroutines.get(subroutineName);
        if (subroutine.getArity() != arguments.size()) {
            throw new SubroutineArityException("Subroutine expects " + subroutine.getArguments().length +
                    "arguments but was called with" + arguments.size(), context);
        }
        final Map<String, JBasicValue> oldMemoryState = this.memory;
        // Prepare subroutine memory
        this.memory = new HashMap<>();
        for (int i = 0; i < subroutine.getArity(); i++) {
            this.assignToVariable(subroutine.getArguments()[i], arguments.get(i));
        }
        Arrays.stream(subroutine.getSubroutineBody()).forEach(visitor::visit);
        // Reset memory to the old state
        this.memory = oldMemoryState;
    }

}
