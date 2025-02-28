package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        Map<String, Attendances> initValue = new HashMap<>();
        initValue.put("test1", new Attendances());
        initValue.put("test2", new Attendances());
        Attendances attendances = new Attendances();
        attendances.addAttendance(LocalDateTime.of(2024, 12, 3, 13, 30));
        attendances.addAttendance(LocalDateTime.of(2024, 12, 2, 14, 0));
        attendances.addAttendance(LocalDateTime.of(2024, 12, 4, 12, 0));
        initValue.put("test3", attendances);
        attendanceBook = new AttendanceBook(initValue);
    }

    @DisplayName("특정 크루의 출석을 저장한다")
    @Test
    void saveCrewAttendanceTest() {
        String nickname = "test1";
        LocalDateTime testTime = LocalDateTime.of(2024, 12, 3, 10, 5);

        attendanceBook.addAttendanceForCrew(nickname, testTime);

        Attendances attendances = attendanceBook.getCrewsRecords().get(nickname);
        Assertions.assertEquals(1, attendances.getRecords().size());
    }

    @DisplayName("크루가 존재하지 않으면 예외를 발생시킨다")
    @Test
    void saveCrewAttendanceExceptionTest() {
        String nickname = "notFound";
        LocalDateTime testTime = LocalDateTime.of(2024, 12, 3, 10, 5);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> attendanceBook.addAttendanceForCrew(nickname, testTime));
    }

    @DisplayName("특정 크루의 출석을 수정한다")
    @Test
    void updateAttendanceTest() {
        String nickname = "test3";
        LocalDate today = LocalDate.of(2024, 12, 5);
        LocalDateTime changeTestTime = LocalDateTime.of(2024, 12, 3, 10, 0);

        Attendance originalAttendance = attendanceBook.getAttendanceByNicknameAndDate(nickname, today.getDayOfMonth());
        Attendance updatedAttendance = attendanceBook.updateAttendanceForCrew(nickname, changeTestTime, today);

        org.assertj.core.api.Assertions.assertThat(attendanceBook.getAttendanceByNickname(nickname).getRecords())
                .contains(updatedAttendance)
                .doesNotContain(originalAttendance);
    }

    @DisplayName("닉네임과 날짜로 특정 크루의 출석을 가져온다")
    @Test
    void getAttendanceByNicknameAndDateTest() {
        String nickname = "test3";
        int day = 3;
        LocalDateTime expectedDateTime = LocalDateTime.of(2024, 12, 3, 13, 30);
        Attendance attendance = attendanceBook.getAttendanceByNicknameAndDate(nickname, day);

        Assertions.assertEquals(expectedDateTime, attendance.getDateTime());
    }

    @DisplayName("특정 크루의 전날까지 출석 기록을 불러온다")
    @Test
    void getCrewAttendanceTest() {
        String nickname = "test3";
        List<Attendance> crewRecords = attendanceBook.getAttendanceByNickname(nickname).getRecords();
        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(crewRecords).hasSize(3)
        );
    }

    @DisplayName("크루들 중 제적 위험자들을 불러온다")
    @Test
    void getRiskOfExpelledCrewsTest() {
        LocalDate today = LocalDate.of(2024, 12, 5);
        Map<String, Attendances> riskOfExpelledCrews = attendanceBook.getRiskOfExpelledCrews(today);
        org.assertj.core.api.Assertions.assertThat(riskOfExpelledCrews)
                .hasSize(1)
                .containsKeys("test3");
    }
}
