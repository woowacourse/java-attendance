package attendance.domain.fixture;

import attendance.domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class AttendanceBookTestFixture {
    private static final int REGULAR_PRESENT_HOUR = 9;
    private static final int REGULAR_PRESENT_MINUTE = 50;
    private static final int REGULAR_LATENESS_HOUR = 10;
    private static final int REGULAR_LATENESS_MINUTE = 6;
    private static final int REGULAR_ABSENCE_HOUR = 10;
    private static final int REGULAR_ABSENCE_MINUTE = 50;


    public static AttendanceBook createAttendanceBook(String name, int latenessCount, int absenceCount, int endDate) {
        validateCount(endDate, latenessCount, absenceCount);
        AttendanceBook attendanceBook = new AttendanceBook(name);
        List<LocalDate> regularDates = LocalDateTestFixture.createRegularDates(endDate);

        IntStream.range(0, regularDates.size())
                .forEach(index -> {
                    LocalDate date = regularDates.get(index);
                    if (index < latenessCount) {
                        attendanceBook.attend(date, createRegularLatenessTime());
                        return;
                    }
                    if (index < latenessCount + absenceCount) {
                        attendanceBook.attend(date, createRegularAbsenceTime());
                        return;
                    }
                    attendanceBook.attend(date, createRegularPresentTime());
                });

        return attendanceBook;
    }

    private static LocalTime createRegularPresentTime() {
        return LocalTime.of(REGULAR_PRESENT_HOUR, REGULAR_PRESENT_MINUTE);
    }

    private static void validateCount(int endDate, int latenessCount, int absenceCount) {
        if (endDate <= latenessCount + absenceCount) {
            throw new IllegalArgumentException("지각과 결석 횟수가 날짜보다 작아야 합니다.");
        }

    }

    private static LocalTime createRegularLatenessTime() {
        return LocalTime.of(REGULAR_LATENESS_HOUR, REGULAR_LATENESS_MINUTE);
    }

    private static LocalTime createRegularAbsenceTime() {
        return LocalTime.of(REGULAR_ABSENCE_HOUR, REGULAR_ABSENCE_MINUTE);
    }

}
