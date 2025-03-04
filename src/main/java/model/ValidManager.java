package model;

import util.HolidayManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ValidManager {

    private static final ValidManager instance = new ValidManager();
    private final Set<Integer> validDates;

    private ValidManager() {
        validDates = makeValidDates();
    }

    public static ValidManager getInstance() {
        return instance;
    }

    private static Set<Integer> makeValidDates() {
        final Set<Integer> allDaysOfMonth = IntStream.rangeClosed(1, 31)
                .boxed()
                .collect(Collectors.toSet());
        allDaysOfMonth.removeAll(getWeekendDays());
        allDaysOfMonth.removeAll(HolidayManager.getHolidays());
        return allDaysOfMonth;
    }

    public static Set<Integer> getWeekendDays() {
        final Set<Integer> weekendDays = new HashSet<>();
        final int firstSaturday = findFirstDay(DayOfWeek.SATURDAY);
        final int firstSunday = findFirstDay(DayOfWeek.SUNDAY);

        final Set<Integer> saturdays = findDaysOfWeekBy(firstSaturday);
        final Set<Integer> sundays = findDaysOfWeekBy(firstSunday);

        weekendDays.addAll(saturdays);
        weekendDays.addAll(sundays);
        return weekendDays;
    }

    private static int findFirstDay(final DayOfWeek dayOfWeek) {
        for (int dayOfMonth = 1; dayOfMonth <= 7; dayOfMonth++) {
            final LocalDate localDate = LocalDate.of(2024, 12, dayOfMonth);
            if (localDate.getDayOfWeek().equals(dayOfWeek)) {
                return dayOfMonth;
            }
        }
        return -1;
    }

    private static Set<Integer> findDaysOfWeekBy(final int firstDay) {
        final Set<Integer> days = new HashSet<>();
        int day = firstDay;

        while (day <= 31) {
            days.add(day);
            day += 7;
        }
        return days;
    }

    public Set<Integer> getDatesFromTo(final int fromDayOfMonth, final int toDayOfMonth) {
        final List<Integer> list = getValidDates().stream().toList();
        final int fromIndex = list.indexOf(fromDayOfMonth);
        final int toIndex = list.indexOf(toDayOfMonth);
        final List<Integer> dates = list.subList(fromIndex, toIndex + 1);
        return Set.copyOf(dates);
    }

    public Set<Integer> getDatesTo(final int toDayOfMonth) {
        final List<Integer> list = getValidDates().stream().toList();
        final int toIndex = list.indexOf(toDayOfMonth);
        final List<Integer> dates = list.subList(0, toIndex + 1);
        return Set.copyOf(dates);
    }

    public int getLastByDayOfMonth(final int dayOfMonth) {
        final List<Integer> list = getValidDates().stream().toList();
        return findLastlyIdxThan(list, dayOfMonth);
    }

    public Set<Integer> getValidDates() {
        return validDates;
    }

    private int findLastlyIdxThan(final List<Integer> list, final int target) {
        int left = 0;
        int right = list.size() - 1;

        int idx = findIdx(list, target, left, right);

        if (idx >= 0 && list.get(idx) == target) {
            idx = Math.max(idx - 1, 0);
        }

        return list.get(idx);
    }

    private static int findIdx(final List<Integer> list, final int target, int left, int right) {
        while (left <= right) {
            final int mid = (left + right) / 2;
            if (target > list.get(mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
}