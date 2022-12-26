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

package org.jbasic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class JBasicBaseTest {

    protected static class Result {

        protected JBasicInterpreter interpreter;
        public String output;
        protected String error;

        public Result(JBasicInterpreter interpreter, String output, String error) {
            this.interpreter = interpreter;
            this.output = output;
            this.error = error;
        }
    }

    protected void test(String resource, Consumer<Result> assertions) {
        test(resource, "", assertions);
    }

    protected void test(String resource, String input, Consumer<Result> assertions) {
        try {
            ByteArrayOutputStream stdout = new ByteArrayOutputStream();
            ByteArrayOutputStream stderr = new ByteArrayOutputStream();
            ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
            JBasicInterpreter interpreter = new JBasicInterpreter(stdin, stdout, stderr);
            interpreter.run(resource(resource));
            String output = stdout.toString();
            String error = stderr.toString();
            assertions.accept(new Result(interpreter, output, error));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream resource(String filename) {
        return getClass().getResourceAsStream("/" + filename);
    }

}