package com.ay.flats;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class UkLocaleDateFormatter {

    private static final Map<String, Integer> months = new HashMap<>(12);

    static {
        months.put(getUkMonth(Month.JANUARY), 1);
        months.put(getUkMonth(Month.FEBRUARY), 2);
        months.put(getUkMonth(Month.MARCH), 3);
        months.put(getUkMonth(Month.APRIL), 4);
        months.put(getUkMonth(Month.MAY), 5);
        months.put(getUkMonth(Month.JUNE), 6);
        months.put(getUkMonth(Month.JULY), 7);
        months.put(getUkMonth(Month.AUGUST), 8);
        months.put(getUkMonth(Month.SEPTEMBER), 9);
        months.put(getUkMonth(Month.OCTOBER), 10);
        months.put(getUkMonth(Month.NOVEMBER), 11);
        months.put(getUkMonth(Month.DECEMBER), 12);
    }

    private final String source;

    UkLocaleDateFormatter(final String source) {
        this.source = source;
    }

    /**
     * Format string to <code>LocalDateTime</code> instance
     * Possible inputs: '17 бер.', '5 січ.', 'Вчора 09:54', 'Сьогодні 20:09'
     *
     * @return <code>LocalDateTime</code> instance
     */
    LocalDateTime format() {
        if (source.length() == 14) { //Сьогодні xx:xx
            return fromDay(LocalDate.now());
        } else if (source.length() == 11) { //Вчора xx:xx
            return fromDay(LocalDate.now().minusDays(1));
        }
        return fromDayAndMonth();
    }

    private LocalDateTime fromDay(final LocalDate date) {
        Integer[] hoursAndMinutes = Arrays.stream(source
                .split(":"))
                .map(str -> str.replaceAll("\\D", ""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return LocalDateTime.of(date, LocalTime.of(hoursAndMinutes[0], hoursAndMinutes[1], 0));
    }

    private LocalDateTime fromDayAndMonth() {
        String[] dayAndMonth = source.split(" ");

        int year = Year.now().getValue();
        int month = months.get(dayAndMonth[1]);
        int day = Integer.parseInt(dayAndMonth[0]);

        return LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.MIDNIGHT);
    }

    private static String getUkMonth(final Month month) {
        return month.getDisplayName(TextStyle.SHORT, new Locale("uk"));
    }
}
