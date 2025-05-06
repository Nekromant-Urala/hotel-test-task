package com.ural;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Run2Test {

    char[][] getMaze(String input) {
        String[] lines = input.split("\n");

        char[][] maze = new char[lines.length][];
        for (int i = 0; i < lines.length; ++i) {
            maze[i] = lines[i].toCharArray();
        }
        return maze;
    }

    Object getResultSolve(String input) throws Exception {
        Class<?> run2 = Class.forName("com.ural.run2");

        Method solve = run2.getDeclaredMethod("solve", char[][].class);
        solve.setAccessible(true);
        return solve.invoke(null, (Object) getMaze(input));
    }

    @Test
    void example1() throws Exception {
        String input = """
                #######
                #a.#Cd#
                ##@#@##
                #######
                ##@#@##
                #cB#Ab#
                #######""";


        Object result = getResultSolve(input);
        Assertions.assertEquals(8, result);
    }

    @Test
    void example2() throws Exception {
        String input = """
                ###############
                #d.ABC.#.....a#
                ######@#@######
                ###############
                ######@#@######
                #b.....#.....c#
                ###############""";

        Object result = getResultSolve(input);
        Assertions.assertEquals(24, result);
    }

    @Test
    void example3() throws Exception {
        String input = """
                #############
                #DcBa.#.GhKl#
                #.###@#@#I###
                #e#d#####j#k#
                ###C#@#@###J#
                #fEbA.#.FgHi#
                #############""";

        Object result = getResultSolve(input);
        Assertions.assertEquals(32, result);
    }

    @Test
    void wrongNumberOfRobots() throws Exception {
        String input = """
                #############
                #DcBa.#.GhKl#
                #.###@#@#I###
                #e#d#####j#k#
                ###C###@###J#
                #fEbA.#.FgHi#
                #############""";

        Object result = getResultSolve(input);
        Assertions.assertEquals(Integer.MAX_VALUE, result);
    }
}
