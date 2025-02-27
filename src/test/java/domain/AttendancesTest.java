package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @DisplayName("한 크루의 출석 기록 목록 저장 테스트")
    @Test
    void generateAttendancesTest() {
        List<Attendance> attendances = List.of(
                new Attendance(
                        new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 0))
                ),
                new Attendance(
                        new AttendanceDate(LocalDate.of(2024, 12, 4)),
                        new AttendanceTime(LocalTime.of(10, 6))
                )
        );

        assertDoesNotThrow(() -> new Attendances(attendances));
    }

    @DisplayName("특정 날짜의 출석 기록 찾기 테스트")
    @Test
    void findAttendanceOfDateTest() {
        Attendance firstAttendance = new Attendance(
                new AttendanceDate(LocalDate.of(2024, 12, 2)),
                new AttendanceTime(LocalTime.of(10, 0))
        );
        Attendance secondAttendance = new Attendance(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(10, 0))
        );
        Attendances attendances = new Attendances(List.of(firstAttendance, secondAttendance));

        assertThat(attendances.findAttendanceByDate(new AttendanceDate(LocalDate.of(2024, 12, 3))))
                .isEqualTo(secondAttendance);
    }

    @DisplayName("경고 상태 테스트")
    @Test
    void checkWarningTest() {
        Attendances normalAttendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 2)), new AttendanceTime(LocalTime.of(13, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31)))
        ));

        Assertions.assertThat(normalAttendances.getCrewStatue(LocalDate.of(2024, 12, 4)))
                .isEqualTo(CrewStatus.WARNING);
    }

    @DisplayName("면담 상태 테스트")
    @Test
    void checkInterviewStatus() {
        Attendances normalAttendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 4)), new AttendanceTime(LocalTime.of(10, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 5)), new AttendanceTime(LocalTime.of(10, 31)))
        ));

        Assertions.assertThat(normalAttendances.getCrewStatue(LocalDate.of(2024, 12, 6)))
                .isEqualTo(CrewStatus.INTERVIEW);
    }

    @DisplayName("제적 상태 테스트")
    @Test
    void countUnattendedTest() {
        Attendances noAttendances = new Attendances(List.of());

        Assertions.assertThat(noAttendances.getCrewStatue(LocalDate.of(2024, 12, 10)))
                .isEqualTo(CrewStatus.EXPELLED);
    }

    @DisplayName("출석 기록 추가 테스트")
    @Test
    void addAttendanceTest() {
        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 4)), new AttendanceTime(LocalTime.of(10, 31)))
        ));

        attendances.addNewAttendance(
                new AttendanceDate(LocalDate.of(2024, 12, 24)),
                new AttendanceTime(LocalTime.of(10, 0)));

        Assertions.assertThat(attendances.checkAlreadyAttend(new AttendanceDate(LocalDate.of(2024, 12, 24))))
                .isEqualTo(true);
    }

    @DisplayName("출석 기록 수정 테스트")
    @Test
    void editAttendanceTest() {
        LocalDate editDate = LocalDate.of(2024, 12, 3);
        LocalTime oldTime = LocalTime.of(10, 31);
        LocalTime editTime = LocalTime.of(9, 55);

        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(editDate), new AttendanceTime(oldTime))
        ));

        attendances.editAttendance(
                new AttendanceDate(editDate),
                new AttendanceTime(editTime));

        Assertions.assertThat(attendances.findAttendanceByDate(new AttendanceDate(editDate)).isLate()).isEqualTo(false);

    }

    @DisplayName("결석 수 카운팅 테스트")
    @Test
    void countAbsentTest() {
        Attendances normalAttendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 2)), new AttendanceTime(LocalTime.of(13, 1))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 1)))
        ));

        Assertions.assertThat(normalAttendances.countUnattended(LocalDate.of(2024, 12, 6)))
                .isEqualTo(2);
    }

    @DisplayName("출석 수 카운팅 테스트")
    @Test
    void countAttendTest() {
        Attendances normalAttendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 2)), new AttendanceTime(LocalTime.of(13, 1))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 1)))
        ));

        Assertions.assertThat(normalAttendances.countAttendance())
                .isEqualTo(2);
    }
}
