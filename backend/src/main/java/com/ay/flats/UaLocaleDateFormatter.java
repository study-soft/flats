package com.ay.flats;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class UaLocaleDateFormatter {

    private static final Map<String, Integer> months = new HashMap<>(12);

    private static final String TODAY = new String("Сьогодні".getBytes(), StandardCharsets.UTF_8);
    private static final String YESTERDAY = new String("Вчора".getBytes(), StandardCharsets.UTF_8);

    static {
        months.put(new String("січ.".getBytes(), StandardCharsets.UTF_8), 1);
        months.put(new String("лют.".getBytes(), StandardCharsets.UTF_8), 2);
        months.put(new String("бер.".getBytes(), StandardCharsets.UTF_8), 3);
        months.put(new String("кві.".getBytes(), StandardCharsets.UTF_8), 4);
        months.put(new String("тра.".getBytes(), StandardCharsets.UTF_8), 5);
        months.put(new String("чер.".getBytes(), StandardCharsets.UTF_8), 6);
        months.put(new String("лип.".getBytes(), StandardCharsets.UTF_8), 7);
        months.put(new String("сер.".getBytes(), StandardCharsets.UTF_8), 8);
        months.put(new String("вер.".getBytes(), StandardCharsets.UTF_8), 9);
        months.put(new String("жов.".getBytes(), StandardCharsets.UTF_8), 10);
        months.put(new String("лис.".getBytes(), StandardCharsets.UTF_8), 11);
        months.put(new String("гру.".getBytes(), StandardCharsets.UTF_8), 12);
    }

    private final String source;

    UaLocaleDateFormatter(final String source) {
        this.source = source;
    }

    /**
     * Format string to <code>LocalDateTime</code> instance
     * Possible inputs: '17 бер.', '5 січ.', 'Вчора 17:54', 'Сьогодні 20:09'
     *
     * @return <code>LocalDateTime</code> instance
     */
    LocalDateTime format() {
        if (source.contains(TODAY)) {
            return fromDay(TODAY);
        } else if (source.contains(YESTERDAY)) {
            return fromDay(YESTERDAY);
        }
        return fromDayAndMonth();
    }

    private LocalDateTime fromDay(final String day) {
        Integer[] hoursAndMinutes = Arrays.stream(source
                .split(":"))
                .map(str -> str.replaceAll("\\D", ""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hoursAndMinutes[0], hoursAndMinutes[1], 0));
    }

    private LocalDateTime fromDayAndMonth() {
        String[] dayAndMonth = source.split(" ");

        int year = Year.now().getValue();
        int month = months.get(dayAndMonth[1]);
        int day = Integer.parseInt(dayAndMonth[0]);

        return LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.MIDNIGHT);
    }
}
