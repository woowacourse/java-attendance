package attendance.model;

import attendance.util.DateUtils;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonthlyAttendance {

    private final Month month;
    private final Crew crew;
    private final List<Attendance> attendances;

    public MonthlyAttendance(Month month, Crew crew, List<Attendance> attendances) {
        this.month = month;
        this.crew = crew;
        this.attendances = attendances;
    }

    public AttendanceResult calculateAttendanceResultUntilDate(LocalDate endDate) {
        validateMonth(endDate);
        List<Attendance> result = new ArrayList<>();
        for (int date = 1; date <= endDate.getDayOfMonth(); date++) {
            LocalDate currentDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), date);
            if (isCloseDay(currentDate)) {
                continue;
            }
            Attendance addAttendance = attendances.stream()
                    .filter(attendance -> attendance.isAlreadyAttendance(crew, currentDate))
                    .findFirst().orElse(Attendance.absent(crew, currentDate));
            result.add(addAttendance);
        }
        return AttendanceResult.create(crew, result);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        MonthlyAttendance that = (MonthlyAttendance) object;
        return month == that.month && Objects.equals(crew, that.crew) && Objects.equals(attendances,
                that.attendances);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(month);
        result = 31 * result + Objects.hashCode(crew);
        result = 31 * result + Objects.hashCode(attendances);
        return result;
    }

    private void validateMonth(LocalDate endDate) {
        if (endDate.getMonth() != month) {
            throw new IllegalArgumentException("해당하는 달의 출석이 아닙니다.");
        }
    }

    private static boolean isCloseDay(LocalDate date) {
        return DateUtils.isWeekend(date.getDayOfWeek()) ||
                Holiday.isHoliday(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()));
    }
}
