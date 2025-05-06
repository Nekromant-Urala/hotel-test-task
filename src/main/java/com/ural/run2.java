package com.ural;

import java.util.*;
import java.io.*;

public class run2 {
    // Константы для символов ключей и дверей
    private static final char[] KEYS_CHAR = new char[26];
    private static final char[] DOORS_CHAR = new char[26];

    static {
        for (int i = 0; i < 26; i++) {
            KEYS_CHAR[i] = (char) ('a' + i);
            DOORS_CHAR[i] = (char) ('A' + i);
        }
    }

    // Чтение данных из стандартного ввода
    private static char[][] getInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }

        char[][] maze = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
        }

        return maze;
    }

    public static void main(String[] args) throws IOException {
        char[][] data = getInput();
        int result = solve(data);

        if (result == Integer.MAX_VALUE) {
            System.out.println("No solution found");
        } else {
            System.out.println(result);
        }
    }

    private static int solve(char[][] maze) {
        int keyCount = 0;
        List<int[]> robots = new ArrayList<>();


        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y] == '@') {
                    robots.add(new int[]{x, y});
                } else if (maze[x][y] >= 'a' && maze[x][y] <= 'z') {
                    keyCount++;
                }
            }
        }

        if (robots.size() != 4) {
            return Integer.MAX_VALUE;
        }

        Map<String, Integer> visited = new HashMap<>();
        Queue<State> queue = new LinkedList<>();

        int[] robot1 = robots.get(0);
        int[] robot2 = robots.get(1);
        int[] robot3 = robots.get(2);
        int[] robot4 = robots.get(3);

        State initialState = new State(robot1[0], robot1[1], robot2[0], robot2[1], robot3[0], robot3[1], robot4[0], robot4[1], 0, 0);
        queue.add(initialState);

        String initialKey = "" + robot1[0] + robot1[1] + robot2[0] + robot2[1] + robot3[0] + robot3[1] + robot4[0] + robot4[1] + 0;
        visited.put(initialKey, 0);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (Integer.bitCount(current.keys) == keyCount) {
                return current.steps;
            }

            for (int robot = 0; robot < 4; robot++) {
                for (int[] dir : directions) {
                    int newX = 0, newY = 0;
                    int[] newPositions = current.getPositions();

                    switch (robot) {
                        case 0:
                            newX = current.x1 + dir[0];
                            newY = current.y1 + dir[1];
                            if (isNotValid(maze, newX, newY, current.keys)) continue;
                            newPositions[0] = newX;
                            newPositions[1] = newY;
                            break;
                        case 1:
                            newX = current.x2 + dir[0];
                            newY = current.y2 + dir[1];
                            if (isNotValid(maze, newX, newY, current.keys)) continue;
                            newPositions[2] = newX;
                            newPositions[3] = newY;
                            break;
                        case 2:
                            newX = current.x3 + dir[0];
                            newY = current.y3 + dir[1];
                            if (isNotValid(maze, newX, newY, current.keys)) continue;
                            newPositions[4] = newX;
                            newPositions[5] = newY;
                            break;
                        case 3:
                            newX = current.x4 + dir[0];
                            newY = current.y4 + dir[1];
                            if (isNotValid(maze, newX, newY, current.keys)) continue;
                            newPositions[6] = newX;
                            newPositions[7] = newY;
                            break;
                    }

                    int newKeys = current.keys;
                    char cell = maze[newX][newY];
                    if (cell >= 'a' && cell <= 'z') {
                        newKeys |= (1 << (cell - 'a'));
                    }

                    State newState = new State(newPositions[0], newPositions[1],
                            newPositions[2], newPositions[3],
                            newPositions[4], newPositions[5],
                            newPositions[6], newPositions[7],
                            newKeys, current.steps + 1);

                    String stateKey = "" + newPositions[0] + newPositions[1] + newPositions[2] + newPositions[3] +
                            newPositions[4] + newPositions[5] + newPositions[6] + newPositions[7] + newKeys;

                    if (!visited.containsKey(stateKey) || visited.get(stateKey) > newState.steps) {
                        visited.put(stateKey, newState.steps);
                        queue.add(newState);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    private static boolean isNotValid(char[][] maze, int x, int y, int keys) {
        if (x < 0 || x >= maze.length || y < 0 || y >= maze[0].length) {
            return true;
        }

        char cell = maze[x][y];
        if (cell == '#') {
            return true;
        }

        if (cell >= 'A' && cell <= 'Z') {
            return (keys & (1 << (cell - 'A'))) == 0;
        }

        return false;
    }

    static class State {
        int x1, y1, x2, y2, x3, y3, x4, y4;
        int keys;
        int steps;

        public State(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int keys, int steps) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.x3 = x3;
            this.y3 = y3;
            this.x4 = x4;
            this.y4 = y4;
            this.keys = keys;
            this.steps = steps;
        }

        public int[] getPositions() {
            return new int[]{x1, y1, x2, y2, x3, y3, x4, y4};
        }
    }
}