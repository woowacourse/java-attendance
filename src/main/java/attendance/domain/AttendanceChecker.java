package attendance.domain;

public interface AttendanceChecker {
    void checkCampusHour(int hour, int minute);

    void validateCampusDay(int day);

    boolean isCampusDay(int day);

}
