/**
 * Copyright (C) 2022 by Frederik Tobner                                    *
 * *
 * This file is part of JBASIC.                                             *
 * *
 * Permission to use, copy, modify, and distribute this software and its    *
 * documentation under the terms of the GNU General Public License is       *
 * hereby granted.                                                          *
 * No representations are made about the suitability of this software for   *
 * any purpose.                                                             *
 * It is provided "as is" without express or implied warranty.              *
 * See the <"https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public  *
 * License for more details.                                                *
 *
 * @file JBasicSubroutine.java
 * @brief Stores the metadata associated with a subroutine, and it's block parsing context so the subroutine can be invoked later.
 */

/**
 * @file JBasicSubroutine.java
 * @brief Stores the metadata associated with a subroutine, and it's block parsing context so the subroutine can be invoked later.
 */

package org.jbasic;

import basic.JBasicParser;

/**
 * @brief Stores the metadata associated with a subroutine, and it's block parsing context so the subroutine can be invoked later.
 */
public class JBasicSubroutine {

    /// Arguments of the subroutine
    private final String[] arguments;

    /// Stores the parsing context of the functionBody, so we can execute it later
    private final JBasicParser.BlockContext subroutineBody;

    /**
     * Constructor of the JBASICSubroutine class
     * @param arguments The names of the arguments of the subroutine
     * @param subroutineBody The parsing context of the body of the subroutine
     */
    public JBasicSubroutine(String[] arguments, JBasicParser.BlockContext subroutineBody) {
        this.arguments = arguments;
        this.subroutineBody = subroutineBody;
    }

    /**
     * Gets the name of the arguments of the subroutine
     * @return The names of the arguments
     */
    public String[] getArguments() {
        return this.arguments;
    }

    /**
     * Gets the body of the subroutine
     *
     * @return The body of the subroutine
     */
    public JBasicParser.BlockContext getSubroutineBody() {
        return this.subroutineBody;
    }

    /**
     * Gets the arity, meaning the amount arguments the subroutine expects
     * @return The arity of the subroutine
     */
    public int getArity() {
        return this.arguments.length;
    }
}