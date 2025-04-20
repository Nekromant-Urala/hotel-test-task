package com.ural;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunTest {

    @Test
    void testCapacityNotExceeded() {
        int capacity = 2;
        List<Map<String, String>> guests = List.of(
                Map.of("name", "1", "check-in", "2021-01-10", "check-out", "2021-01-15"),
                Map.of("name", "2", "check-in", "2021-01-12", "check-out", "2021-01-20"),
                Map.of("name", "3", "check-in", "2021-01-15", "check-out", "2021-01-21")
        );
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertTrue(check);
    }
    @Test
    void testCapacityExceeded() {
        int capacity = 2;
        List<Map<String, String>> guests = List.of(
                Map.of("name", "1", "check-in", "2021-01-10", "check-out", "2021-01-16"),
                Map.of("name", "2", "check-in", "2021-01-12", "check-out", "2021-01-20"),
                Map.of("name", "3", "check-in", "2021-01-15", "check-out", "2021-01-21")
        );
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertFalse(check);
    }

    @Test
    void testCheckInAndCheckOutParallel() {
        int capacity = 2;
        List<Map<String, String>> guests = List.of(
                Map.of("name", "1", "check-in", "2024-01-10", "check-out", "2024-01-15"),
                Map.of("name", "2", "check-in", "2024-01-15", "check-out", "2024-01-20")
        );
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertTrue(check);
    }

    @Test
    void testDifferentYears() {
        int capacity = 2;
        List<Map<String, String>> guests = List.of(
                Map.of("name", "1", "check-in", "2024-12-31", "check-out", "2025-01-01"),
                Map.of("name", "2", "check-in", "2025-01-01", "check-out", "2025-01-02")
        );
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertTrue(check);
    }

    @Test
    void testEmptyListGuest() {
        int capacity = 10;
        List<Map<String, String >> guests = new ArrayList<>();
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertTrue(check);
    }

    @Test
    void testLongStayTime() {
        int capacity = 2;
        List<Map<String, String>> guests = List.of(
                Map.of("name", "1", "check-in", "2024-03-01", "check-out", "2024-03-31"),
                Map.of("name", "2", "check-in", "2024-03-15", "check-out", "2024-04-15"),
                Map.of("name", "3", "check-in", "2024-03-20", "check-out", "2024-04-20")
        );
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertFalse(check);
    }

    @Test
    void testEmptyCapacity() {
        int capacity = 0;
        List<Map<String, String>> guests = List.of(
                Map.of("name", "1", "check-in", "2024-03-01", "check-out", "2024-03-31"),
                Map.of("name", "2", "check-in", "2024-03-15", "check-out", "2024-04-15"),
                Map.of("name", "3", "check-in", "2024-03-20", "check-out", "2024-04-20")
        );
        boolean check = run.checkCapacity(capacity, guests);
        Assertions.assertFalse(check);
    }
}
