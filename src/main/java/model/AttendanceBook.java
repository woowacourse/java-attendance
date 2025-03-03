package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AttendanceBook {

    private final TreeSet<Attendance> attendances;

    public AttendanceBook(final TreeSet<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean isSameByDate(final Attendance attendance) {
        return attendances.stream()
                .anyMatch(o -> o.getAttendanceDateTime().getDateTime().toLocalDate().equals(attendance.getAttendanceDateTime().getDateTime().toLocalDate()));
    }

    public Attendance findByDayOfMonth(final DayOfMonth dayOfMonth) {
        return attendances.stream()
                .filter(o -> o.getAttendanceDateTime().getDateTime().getDayOfMonth() == dayOfMonth.getValue())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("날짜에 대한 출석 기록이 존재하지 않습니다."));
    }

    public void add(final Attendance attendance) {
        attendances.add(attendance);
    }

    public void update(final Attendance oldAttendance, final Attendance newAttendance) {
        attendances.remove(oldAttendance);
        attendances.add(newAttendance);
    }

    public void updateRecordFromTo(final int fromDayOfMonth, final int toDayOfMonth) {
        final Set<Integer> datesFromTo = ValidManager.getInstance().getDatesFromTo(fromDayOfMonth, toDayOfMonth);
        for (final int day : datesFromTo) {
            final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, day, LocalTime.of(0, 0));
            final Attendance attendance = Attendance.of(attendanceDateTime);

            add(attendance);
        }
    }

    public TreeSet<Attendance> getAttendances() {
        return attendances;
    }

    public AttendanceBook getBefore(final AttendanceDateTime todayDateTime) {
        final List<Attendance> attendancesList = new ArrayList<>(attendances);
        final int toIdx = findLargestIdxLessThan(attendancesList, todayDateTime);
        final List<Attendance> attendances = attendancesList.subList(0, toIdx);
        return new AttendanceBook(new TreeSet<>(attendances));
    }

    public int getLastlyAttendance() {
        return attendances.getLast().getAttendanceDateTime().getDate().getDayOfMonth();
    }

    public Set<Integer> getAllDayOfMonth() {
        final List<Integer> allDays = attendances.stream()
                .map(Attendance::getAttendanceDateTime)
                .map(AttendanceDateTime::getDateTime)
                .map(LocalDateTime::getDayOfMonth)
                .toList();
        return Set.copyOf(allDays);
    }

    public List<AttendanceStatus> getStatuses() {
        return attendances.stream()
                .map(Attendance::getAttendanceStatus)
                .toList();
    }

    private static int findLargestIdxLessThan(final List<Attendance> arr, final AttendanceDateTime target) {
        final int left = 0;
        final int right = arr.size() - 1;

        return findIdx(arr, target, left, right);
    }

    private static int findIdx(final List<Attendance> arr, final AttendanceDateTime target, int left, int right) {
        while (left <= right) {
            final int mid = (left + right) / 2;
            final AttendanceDateTime midDateTime = arr.get(mid).getAttendanceDateTime();
            if (target.isAfter(midDateTime)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
}
