package com.ural;

import java.time.LocalDate;
import java.util.*;


public class run {
    public static boolean checkCapacity(int maxCapacity, List<Map<String, String>> guests) {
        if (maxCapacity <= 0) {
            return false;
        }

        HashMap<LocalDate, Integer> eventHistory = new HashMap<>(guests.size());
        final String keyCheckIn = "check-in";
        final String keyCheckOut = "check-out";

        // Разделяем события на "въезд" и "выезд" посетителей
        for (Map<String, String> guest : guests) {
            LocalDate checkInDate = LocalDate.parse(guest.get(keyCheckIn));
            LocalDate checkOutDate = LocalDate.parse(guest.get(keyCheckOut));

            Integer count = eventHistory.get(checkInDate);
            eventHistory.put(checkInDate, count == null ? 1 : count + 1);

            count = eventHistory.get(checkOutDate);
            eventHistory.put(checkOutDate, count == null ? -1 : count - 1);
        }

        // сортируем порядок событий
        TreeMap<LocalDate, Integer> sortedEventHistory = new TreeMap<>(eventHistory);

        // Проверяем выходит ли количество посетителей за допустимую границу
        int capacity = 0;
        for (int event : sortedEventHistory.values()) {
            capacity += event;
            if (capacity > maxCapacity) {
                return false;
            }
        }
        return true;
    }

    // Вспомогательный метод для парсинга JSON строки в Map
    private static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();
        // Удаляем фигурные скобки
        json = json.substring(1, json.length() - 1);

        // Разбиваем на пары ключ-значение
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");
            map.put(key, value);
        }
        return map;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Первая строка - вместимость гостиницы
        int maxCapacity = Integer.parseInt(scanner.nextLine());
        // Вторая строка - количество записей о гостях
        int n = Integer.parseInt(scanner.nextLine());

        List<Map<String, String>> guests = new ArrayList<>();

        // Читаем n строк, json-данные о посещении
        for (int i = 0; i < n; i++) {
            String jsonGuest = scanner.nextLine();
            // Простой парсер JSON строки в Map
            Map<String, String> guest = parseJsonToMap(jsonGuest);
            guests.add(guest);
        }

        // Вызов функции
        boolean result = checkCapacity(maxCapacity, guests);

        // Вывод результата
        System.out.println(result ? "True" : "False");

        scanner.close();
    }
}