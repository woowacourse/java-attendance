package attendance.domain.fixture;

import static java.time.DayOfWeek.MONDAY;

import attendance.domain.Attendances;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class AttendancesTestFixture {
    private static final int REGULAR_PRESENT_HOUR = 9;
    private static final int REGULAR_PRESENT_MINUTE = 50;
    private static final int MONDAY_PRESENT_HOUR = 12;
    private static final int MONDAY_PRESENT_MINUTE = 50;
    private static final int REGULAR_LATENESS_HOUR = 10;
    private static final int REGULAR_LATENESS_MINUTE = 6;
    private static final int MONDAY_LATENESS_HOUR = 13;
    private static final int MONDAY_LATENESS_MINUTE = 6;
    private static final int REGULAR_ABSENCE_HOUR = 10;
    private static final int REGULAR_ABSENCE_MINUTE = 50;
    private static final int MONDAY_ABSENCE_HOUR = 13;
    private static final int MONDAY_ABSENCE_MINUTE = 50;


    public static Attendances createAttendances(int latenessCount, int absenceCount, int endDate) {
        validateCount(endDate, latenessCount, absenceCount);
        List<LocalDate> regularDates = LocalDateTestFixture.createRegularDates(endDate);

        Attendances attendances = new Attendances();

        IntStream.range(0, regularDates.size())
                .forEach(index -> {
                    LocalDate date = regularDates.get(index);
                    if (index < latenessCount) {
                        attendances.addAttendance(date, createLatenessTime(isMonday(date)));
                        return;
                    }
                    if (index < latenessCount + absenceCount) {
                        attendances.addAttendance(date, createAbsenceTime(isMonday(date)));
                        return;
                    }
                    attendances.addAttendance(date, createPresentTime(isMonday(date)));
                });

        return attendances;
    }


    private static void validateCount(int endDate, int latenessCount, int absenceCount) {
        if (endDate <= latenessCount + absenceCount) {
            throw new IllegalArgumentException("지각과 결석 횟수가 날짜보다 작아야 합니다.");
        }

    }

    private static LocalTime createPresentTime(boolean isMonday) {
        if (isMonday) {
            return LocalTime.of(MONDAY_PRESENT_HOUR, MONDAY_PRESENT_MINUTE);
        }
        return LocalTime.of(REGULAR_PRESENT_HOUR, REGULAR_PRESENT_MINUTE);
    }

    private static LocalTime createLatenessTime(boolean isMonday) {
        if (isMonday) {
            return LocalTime.of(MONDAY_LATENESS_HOUR, MONDAY_LATENESS_MINUTE);
        }
        return LocalTime.of(REGULAR_LATENESS_HOUR, REGULAR_LATENESS_MINUTE);
    }

    private static LocalTime createAbsenceTime(boolean isMonday) {
        if (isMonday) {
            return LocalTime.of(MONDAY_ABSENCE_HOUR, MONDAY_ABSENCE_MINUTE);
        }
        return LocalTime.of(REGULAR_ABSENCE_HOUR, REGULAR_ABSENCE_MINUTE);
    }

    private static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == MONDAY;
    }

}
