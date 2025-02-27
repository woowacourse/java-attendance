package attendance.domain;

import attendance.domain.constant.Weekday;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceCheckerTest {

    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:31,결석",
            "2025,2,20,10:05,출석",
            "2025,2,21,10:06,지각"})
    void 결석_출석_지각_확인(int year, int month, int day, String timeNumber, String expectedStatus) {
        //given
        List<String> timeNumbers = List.of(timeNumber.split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, Integer.parseInt(timeNumbers.get(0)),
                Integer.parseInt(timeNumbers.get(1)));
        Weekday weekday = Weekday.from(localDateTime.getDayOfWeek());
        AttendanceChecker attendanceChecker = new AttendanceChecker(localDateTime);

        //when
        String attendanceStatus = attendanceChecker.getAttendanceStatus();

        //then
        Assertions.assertThat(attendanceStatus).isEqualTo(expectedStatus);
    }


    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:31,09:59,출석",
            "2025,2,19,10:31,10:11,지각",
            "2025,2,19,10:11,10:31,결석",
            "2025,2,19,10:11,09:50,출석",
            "2025,2,19,09:59,10:11,지각",
            "2025,2,19,09:59,10:31,결석"})
    void 결석_출석_지각으로_수정_확인(int year, int month, int day, String beforeTime, String afterTime, String expectedStatus) {
        //given
        List<String> beforeTimeNumbers = List.of(beforeTime.split(":"));
        List<String> afterTimeNumbers = List.of(afterTime.split(":"));
        LocalDateTime beforeLocalDateTime = LocalDateTime.of(year, month, day,
                Integer.parseInt(beforeTimeNumbers.get(0)), Integer.parseInt(beforeTimeNumbers.get(1)));
        LocalDateTime affterLocalDateTime = LocalDateTime.of(year, month, day,
                Integer.parseInt(afterTimeNumbers.get(0)), Integer.parseInt(afterTimeNumbers.get(1)));
        AttendanceChecker attendanceChecker = new AttendanceChecker(beforeLocalDateTime);
        //when
        attendanceChecker.modifyAttendanceTime(affterLocalDateTime);

        //then
        Assertions.assertThat(attendanceChecker.getAttendanceStatus()).isEqualTo(expectedStatus);
    }


    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:31,true", "2025,2,19,09:59,false", "2025,2,19,10:11,false"})
    void 결석_개수_반환(int year, int month, int day, String timeNumber, boolean expectedStatus) {
        //given
        List<String> timeNumbers = List.of(timeNumber.split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, Integer.parseInt(timeNumbers.get(0)),
                Integer.parseInt(timeNumbers.get(1)));

        AttendanceChecker attendanceChecker = new AttendanceChecker(localDateTime);

        //when & then
        Assertions.assertThat(attendanceChecker.isAbsence()).isEqualTo(expectedStatus);
    }


    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:11,true", "2025,2,19,09:59,false", "2025,2,19,10:31,false"})
    void 지각_개수_반환(int year, int month, int day, String timeNumber, boolean expectedStatus) {
        //given
        List<String> timeNumbers = List.of(timeNumber.split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, Integer.parseInt(timeNumbers.get(0)),
                Integer.parseInt(timeNumbers.get(1)));

        AttendanceChecker attendanceChecker = new AttendanceChecker(localDateTime);

        //when & then
        Assertions.assertThat(attendanceChecker.isLate()).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:11,false", "2025,2,19,09:59,true", "2025,2,19,10:31,false"})
    void 출석_개수_반환(int year, int month, int day, String timeNumber, boolean expectedStatus) {
        //given
        List<String> timeNumbers = List.of(timeNumber.split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, Integer.parseInt(timeNumbers.get(0)),
                Integer.parseInt(timeNumbers.get(1)));

        AttendanceChecker attendanceChecker = new AttendanceChecker(localDateTime);
        //when & then
        Assertions.assertThat(attendanceChecker.isAttendance()).isEqualTo(expectedStatus);
    }
}