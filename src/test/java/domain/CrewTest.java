package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {
    Crew crew;

    @BeforeEach
    void setUp() {
        crew = new Crew("테스트");
    }

    @DisplayName("출석을 추가합니다.")
    @Test
    void addAttendanceTest() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        crew.addAttendance(dateTime);

        List<Attendance> attendances = crew.getAttendances();
        Assertions.assertEquals(1, attendances.size());
    }

    @DisplayName("평일 중 당일 전까지 출석하지 않은 날에 대한 결석을 추가합니다.")
    @Test
    void addAllAbsentTest() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 5, 10, 0);
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        crew.addAttendance(dateTime);

        crew.addAllAbsent(today);

        Assertions.assertEquals(4, crew.getAttendances().size());
    }

    @DisplayName("특정 날짜의 출석을 가져옵니다.")
    @Test
    void getSpecificAttendanceTest() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Attendance attendance = new Attendance(dateTime);
        crew.addAttendance(dateTime);

        Attendance specificAttendance = crew.getSpecificAttendance(2);
        Assertions.assertEquals(attendance, specificAttendance);
    }

    @DisplayName("출석 변경 시 상태 횟수와 출석이 변경됩니다")
    @Test
    void changeAttendanceTest() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        crew.addAttendance(dateTime);

        crew.changeAttendance(2, new Time("13:06"));

        Map<AttendanceStatus, Integer> statuses = crew.getAttendanceCount().getStatuses();
        Assertions.assertEquals(-1, statuses.get(AttendanceStatus.PRESENT));
        Assertions.assertEquals(1, statuses.get(AttendanceStatus.LATE));
    }

    @DisplayName("출석 기록에 대한 상태를 저장합니다.")
    @Test
    void saveAttendanceCountTest() {
        LocalDateTime present = LocalDateTime.of(2024, 12, 2, 13, 0);
        LocalDateTime late = LocalDateTime.of(2024, 12, 3, 10, 6);
        LocalDateTime absent = LocalDateTime.of(2024, 12, 4, 10, 50);
        crew.addAttendance(present);
        crew.addAttendance(late);
        crew.addAttendance(absent);

        crew.saveAttendanceCount();

        Map<AttendanceStatus, Integer> statuses = crew.getAttendanceCount().getStatuses();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, statuses.get(AttendanceStatus.PRESENT)),
                () -> Assertions.assertEquals(1, statuses.get(AttendanceStatus.LATE)),
                () -> Assertions.assertEquals(1, statuses.get(AttendanceStatus.ABSENT))
        );
    }

    @DisplayName("제적 위험자 결과를 계산합니다.")
    @Test
    void calculateAttendanceAlertLevelTest() {
        LocalDateTime absent1 = LocalDateTime.of(2024, 12, 2, 13, 50);
        LocalDateTime absent2 = LocalDateTime.of(2024, 12, 3, 10, 50);
        LocalDateTime absent3 = LocalDateTime.of(2024, 12, 4, 10, 50);
        LocalDateTime absent4 = LocalDateTime.of(2024, 12, 5, 10, 50);
        crew.addAttendance(absent1);
        crew.addAttendance(absent2);
        crew.addAttendance(absent3);
        crew.addAttendance(absent4);
        crew.saveAttendanceCount();

        AttendanceAlertLevel attendanceAlertLevel = crew.calculateAttendanceAlertLevel();

        Assertions.assertEquals(AttendanceAlertLevel.COUNSEL_REQUIRED, attendanceAlertLevel);
    }

    @DisplayName("같은 날짜에 출석이 존재하는지 검사합니다.")
    @Test
    void isAlreadyCheckedTest() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 3);
        Crew crew = new Crew("아마");
        crew.addAttendance(dateTime);
        Assertions.assertTrue(crew.isAlreadyChecked(dateTime));
    }
}