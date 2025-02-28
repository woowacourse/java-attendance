package domain.testdata;

import domain.AttendanceTime;
import domain.AttendanceTimes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceTestData {

    public static class AttendanceTimesData {
        // 결석 1회, 지각 2회
        public static AttendanceTimes createNormalAttendanceTimes() {
            AttendanceTime attendanceTime1 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 2),
                    LocalTime.of(11, 6)
            );
            AttendanceTime attendanceTime2 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 3),
                    LocalTime.of(11, 5)
            );
            AttendanceTime attendanceTime3 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 4),
                    LocalTime.of(9, 6)
            );
            AttendanceTime attendanceTime4 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 5),
                    LocalTime.of(10, 6)
            );
            AttendanceTime attendanceTime5 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 6),
                    LocalTime.of(10, 6)
            );
            AttendanceTime attendanceTime6 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 9),
                    LocalTime.of(13, 1)
            );
            return AttendanceTimes.of(
                    List.of(attendanceTime1,
                            attendanceTime2,
                            attendanceTime3,
                            attendanceTime4,
                            attendanceTime5,
                            attendanceTime6)
            );
        }

        // 결석 1회, 지각 3회, 경고 대상자
        public static AttendanceTimes createWarnedAttendanceTimes() {
            AttendanceTime attendanceTime1 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 2),
                    LocalTime.of(13, 6)
            );
            AttendanceTime attendanceTime2 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 3),
                    LocalTime.of(10, 6)
            );
            AttendanceTime attendanceTime3 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 4),
                    LocalTime.of(10, 6)
            );
            AttendanceTime attendanceTime4 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 5),
                    LocalTime.of(11, 6)
            );
            AttendanceTime attendanceTime5 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 6),
                    LocalTime.of(9, 6)
            );
            AttendanceTime attendanceTime6 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 9),
                    LocalTime.of(11, 6)
            );
            return AttendanceTimes.of(
                    List.of(attendanceTime1,
                            attendanceTime2,
                            attendanceTime3,
                            attendanceTime4,
                            attendanceTime5,
                            attendanceTime6)
            );
        }

        // 결석 2회, 지각 3회, 면담 대상자
        public static AttendanceTimes createCounselingAttendanceTimes() {
            AttendanceTime attendanceTime1 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 2),
                    LocalTime.of(15, 6)
            );
            AttendanceTime attendanceTime2 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 3),
                    LocalTime.of(11, 6)
            );
            AttendanceTime attendanceTime3 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 4),
                    LocalTime.of(11, 6)
            );
            AttendanceTime attendanceTime4 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 5),
                    LocalTime.of(10, 6)
            );
            AttendanceTime attendanceTime5 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 6),
                    LocalTime.of(10, 6)
            );
            AttendanceTime attendanceTime6 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 9),
                    LocalTime.of(11, 6)
            );
            return AttendanceTimes.of(
                    List.of(attendanceTime1,
                            attendanceTime2,
                            attendanceTime3,
                            attendanceTime4,
                            attendanceTime5,
                            attendanceTime6)
            );
        }

        // 결석 6회, 제적 대상자
        public static AttendanceTimes createDismissAttendanceTimes() {
            AttendanceTime attendanceTime1 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 2),
                    LocalTime.of(16, 6)
            );
            AttendanceTime attendanceTime2 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 3),
                    LocalTime.of(16, 6)
            );
            AttendanceTime attendanceTime3 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 4),
                    LocalTime.of(16, 6)
            );
            AttendanceTime attendanceTime4 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 5),
                    LocalTime.of(16, 6)
            );
            AttendanceTime attendanceTime5 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 6),
                    LocalTime.of(16, 6)
            );
            AttendanceTime attendanceTime6 = AttendanceTime.of(
                    LocalDate.of(2024, 12, 9),
                    LocalTime.of(16, 6)
            );
            return AttendanceTimes.of(
                    List.of(attendanceTime1,
                            attendanceTime2,
                            attendanceTime3,
                            attendanceTime4,
                            attendanceTime5,
                            attendanceTime6)
            );
        }
    }
}
