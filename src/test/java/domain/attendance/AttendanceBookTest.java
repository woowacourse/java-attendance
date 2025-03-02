package domain.attendance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.crew.Crew;
import domain.crew.CrewStatus;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceBookTest {

    private AttendanceBook attendanceBook;
    private List<Crew> crews;
    private List<AttendanceLogs> crewAttendanceLogs;

    @BeforeEach
    void setUp() {
        List<String> crewNames = Arrays.asList("크루1", "크루2", "크루3", "크루4");
        List<List<LocalDateTime>> logDates = getLogDates();
        Map<Crew, AttendanceLogs> crewAttendances = createCrewAttendances(crewNames, logDates);
        crews = crewAttendances.keySet().stream().toList();
        crewAttendanceLogs = crewAttendances.values().stream().toList();
        attendanceBook = new AttendanceBook(crewAttendances);
    }

    private List<List<LocalDateTime>> getLogDates() {
        List<List<Integer>> crewDays = Arrays.asList(
                List.of(2), List.of(2, 3), List.of(2, 3, 4, 5, 6), List.of(2, 3, 4, 5, 6, 9)
        );

        return crewDays.stream()
                .map(days -> days.stream()
                        .map(day -> LocalDateTime.of(2024, 12, day, 10, 0))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private Map<Crew, AttendanceLogs> createCrewAttendances(List<String> crewNames,
                                                            List<List<LocalDateTime>> logDates) {
        Map<Crew, AttendanceLogs> crewAttendances = new LinkedHashMap<>();
        for (int i = 0; i < crewNames.size(); i++) {
            Crew crew = new Crew(crewNames.get(i));
            AttendanceLogs attendanceLogs = createAttendanceLogs(logDates.get(i));
            crewAttendances.put(crew, attendanceLogs);
        }
        return crewAttendances;
    }

    private AttendanceLogs createAttendanceLogs(List<LocalDateTime> dateTimes) {
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        for (LocalDateTime dateTime : dateTimes) {
            attendanceLogs.registerLog(dateTime);
        }
        return attendanceLogs;
    }

    @Test
    @DisplayName("크루의 출결 기록 검색 기능 테스트")
    void 크루의_출결_기록_검색_기능_테스트() {
        // when
        String crewName = crews.getFirst().getName();
        // then & given
        assertEquals(attendanceBook.findCrewAttendanceLogs(crewName), crewAttendanceLogs.getFirst());
    }

    @Test
    @DisplayName("크루의 출결 기록 검색 예외 테스트")
    void 크루의_출결_기록_검색_예외_테스트() {
        // when
        String crewName = "토성";
        // then & given
        assertThatThrownBy(() -> attendanceBook.findCrewAttendanceLogs(crewName))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 확인 기능 테스트")
    void 출석_확인_기능_테스트() {
        // given
        String crewName = crews.getFirst().getName();
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 3, 11, 0);
        // when & then
        assertTrue(attendanceBook.registerCrewAttendanceLog(crewName, attendDateTime)
                .isAttendDate(attendDateTime.toLocalDate()));
    }

    @Test
    @DisplayName("출석 확인 예외 테스트")
    void 출석_확인_예외_테스트() {
        // given
        String crewName = crews.getFirst().getName();
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        // when & then
        assertThatThrownBy(() -> attendanceBook.registerCrewAttendanceLog(crewName, attendDateTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 수정 기능 테스트")
    void 출석_수정_기능_테스트() {
        // given
        String crewName = crews.getFirst().getName();
        LocalDate editDate = LocalDate.of(2024, 12, 2);
        LocalTime editTime = LocalTime.of(10, 0);
        // when & then
        assertEquals(attendanceBook.editCrewAttendanceLog(crewName, editDate, editTime).getAttendanceTime(), editTime);
    }

    @Test
    @DisplayName("출석 수정 예외 테스트")
    void 출석_수정_예외_테스트() {
        // given
        String crewName = crews.getFirst().getName();
        LocalDate editDate = LocalDate.of(2024, 12, 3);
        LocalTime editTime = LocalTime.of(10, 0);
        // when & then
        assertThatThrownBy(() -> attendanceBook.editCrewAttendanceLog(crewName, editDate, editTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("크루별 출석 기록 확인 기능 테스트")
    void 크루별_출석_기록_확인_기능_테스트() {
        // given
        String crewName = crews.getFirst().getName();
        // when
        AttendanceLogs attendanceLogs = attendanceBook.findCrewAttendanceLogs(crewName);
        // then
        assertEquals(crewAttendanceLogs.getFirst(), attendanceLogs);
    }

    @Test
    @DisplayName("제적 위험자 확인 기능 테스트")
    void 제적_위험자_확인_기능_테스트() {
        // given
        LocalDate todayDate = LocalDate.of(2024, 12, 11);
        List<String> crewNames = List.of("크루1", "크루2", "크루3");
        // when
        List<Map.Entry<Crew, AttendanceResult>> expulsionRiskCrews = attendanceBook.findExpulsionRiskCrews(todayDate);
        List<String> expulsionRiskCrewNames = expulsionRiskCrews.stream()
                .map(entry -> entry.getKey().getName())
                .toList();
        // then
        assertEquals(new HashSet<>(crewNames), new HashSet<>(expulsionRiskCrewNames));
    }

    @ParameterizedTest
    @CsvSource({"크루1,EXPEL", "크루2,CONSULT", "크루3,WARNING"})
    @DisplayName("제적 위험자 상태 확인 기능 테스트")
    void 제적_위험자_상태_확인_기능_테스트(String crewName, String crewStatus) {
        // given
        LocalDate todayDate = LocalDate.of(2024, 12, 11);
        // when
        List<Map.Entry<Crew, AttendanceResult>> expulsionRiskCrews = attendanceBook.findExpulsionRiskCrews(todayDate);
        AttendanceResult attendanceResult = expulsionRiskCrews.stream()
                .filter(entry -> entry.getKey().isCrew(crewName))
                .map(Map.Entry::getValue)  // Extract the AttendanceResult from the Map.Entry
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("크루 " + crewName + "을 찾을 수 없습니다."));
        // then
        assertEquals(CrewStatus.valueOf(crewStatus), attendanceResult.getCrewStatus());
    }

    @Test
    @DisplayName("크루의 해당 날짜 출석 로그 검색 기능 테스트")
    void 크루의_해당_날짜_출석_로그_검색_기능_테스트() {
        // when
        String crewName = crews.getFirst().getName();
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceLog attendanceLog = new AttendanceLog(attendDateTime);
        // then
        AttendanceLog crewAttendanceLog = attendanceBook.findCrewAttendanceLog(crewName, attendDateTime.toLocalDate());
        // given
        assertAll(
                () -> assertEquals(attendanceLog.getAttendanceDate(), crewAttendanceLog.getAttendanceDate()),
                () -> assertEquals(attendanceLog.getAttendanceTime(), crewAttendanceLog.getAttendanceTime()),
                () -> assertEquals(attendanceLog.getAttendanceStatus(), crewAttendanceLog.getAttendanceStatus())
        );
    }

    @Test
    @DisplayName("크루의 해당 날짜까지 출석 로그 기록 검색 기능 테스트")
    void 크루의_해당_날짜까지_출석_로그_기록_검색_기능_테스트() {
        // when
        String crewName = crews.getLast().getName();
        LocalDate todayDate = LocalDate.of(2024, 12, 5);
        // then
        List<AttendanceLog> attendanceLogHistory = attendanceBook.findCrewAttendanceLogHistory(crewName, todayDate);
        // given
        assertAll(
                () -> assertEquals(3, attendanceLogHistory.size()),
                () -> assertTrue(attendanceLogHistory.get(0).getAttendanceDate().equals(LocalDate.of(2024, 12, 2))),
                () -> assertTrue(attendanceLogHistory.get(1).getAttendanceDate().equals(LocalDate.of(2024, 12, 3))),
                () -> assertTrue(attendanceLogHistory.get(2).getAttendanceDate().equals(LocalDate.of(2024, 12, 4)))
        );
    }
}
