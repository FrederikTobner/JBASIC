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
 * @details Used to store variables that are declared in the script, labeled blocks and subroutines.
 */

package org.jbasic.interpreter;

import jbasic.JBasicParser.BlockContext;
import jbasic.JBasicParser.SubroutineDefinitionStatementContext;
import jbasic.JBasicParser.SubroutineInvocationStatementContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.error.variable.UndefinedVariableException;
import org.jbasic.visitor.JBasicVisitor;
import org.jbasic.languageModels.JBasicSubroutine;
import org.jbasic.error.labels.UndefinedLabelException;
import org.jbasic.error.subroutine.SubroutineArityException;
import org.jbasic.error.subroutine.SubroutineNotDefinedException;
import org.jbasic.error.subroutine.SubroutineRedefinitionException;
import org.jbasic.languageModels.JBasicValue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * @brief Stores the state of the interpreter.
 * @details Used to store variables that are declared in the script and subroutines.
 */
public class JBasicInterpreterState {

    /// The hashtable, that stores all the variables with the name of the variable as the key of the entry
    private Map<String, JBasicValue> memory = new HashMap<>();

    /// Stores the defined functions of the executed script
    private final Map<String, JBasicSubroutine> subroutines = new HashMap<>();

    private final Map<String, BlockContext> labeledBlocks = new HashMap<>();

    private String currentScript;

    private final ArrayDeque<JBasicValue> dataSegment = new ArrayDeque<>();

    private final Stack<JBasicValue> poppedDataList = new Stack<>();

    public ArrayDeque<JBasicValue> getDataSegment() {
        return this.dataSegment;
    }

    public void addLabel(String labelName, BlockContext blockContext) {
        this.labeledBlocks.put(labelName, blockContext);
    }

    public JBasicValue gotoLabel(String labelName, JBasicVisitor visitor, ParserRuleContext context) throws UndefinedLabelException {
        BlockContext blockContext = this.labeledBlocks.get(labelName);
        if (blockContext == null) {
            throw new UndefinedLabelException("A label called " + labelName + " is not defined", context);
        }
        return visitor.visit(blockContext);
    }

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
    public void defineSubroutine(String subroutineName, JBasicSubroutine subroutine, SubroutineDefinitionStatementContext context)
            throws SubroutineRedefinitionException {
        if (this.subroutines.containsKey(subroutineName)) {
            throw new SubroutineRedefinitionException("A subroutine with the name " + subroutineName + " is already defined in the script", context);
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
     * @brief Free's the memory
     * @details Deallocates all the memory used by a memory object instance
     */
    public void eraseLabels() {
        this.labeledBlocks.clear();
    }

    /**
     * @brief Free's the memory
     * @details Deallocates all the memory used by a memory object instance
     */
    public void eraseSubroutines() {
        this.subroutines.clear();
    }

    /**
     * Gets a specific variable from memory
     *
     * @param name The name of the variable that is obtained
     * @note Throws an UndefinedVariableException if the variable is not defined
     */
    public JBasicValue getVariableValue(String name, ParserRuleContext context) throws UndefinedVariableException {
        JBasicValue value = this.memory.get(name);
        if(value == null)
            throw new UndefinedVariableException(name + " is not defined", context);
        return value;
    }

    /**
     * Invokes a subroutine
     *
     * @param subroutineName The name of the subroutine
     * @param arguments      The arguments of the subroutine call
     * @param visitor        The visitor of the subroutine call. Used to visit the subroutine body
     */
    public void invokeSubroutine(String subroutineName, List<JBasicValue> arguments, JBasicVisitor visitor,
                                 SubroutineInvocationStatementContext context)
                                 throws SubroutineNotDefinedException, SubroutineArityException  {
        if (!this.subroutines.containsKey(subroutineName)) {
            throw new SubroutineNotDefinedException("A subroutine with the name" + subroutineName + " is not defined in the script", context);
        }
        JBasicSubroutine subroutine = this.subroutines.get(subroutineName);
        if (subroutine.getArity() != arguments.size()) {
            throw new SubroutineArityException("Subroutine expects " + subroutine.getArguments().length +
                    " arguments but was called with " + arguments.size(), context);
        }
        final Map<String, JBasicValue> oldMemoryState = this.memory;
        // Prepare subroutine memory
        this.memory = new HashMap<>();
        IntStream.range(0, subroutine.getArity())
                .forEach(i -> this.assignToVariable(subroutine.getArguments()[i], arguments.get(i)));
        Arrays.stream(subroutine.getSubroutineBody()).forEach(visitor::visit);
        // Reset memory to the old state
        this.memory = oldMemoryState;
    }

    public String getCurrentScript() {
        return this.currentScript;
    }

    public void setCurrentScript(String currentScript) {
        this.currentScript = currentScript;
    }

    public Stack<JBasicValue> getPoppedDataStack() {
        return this.poppedDataList;
    }
}
