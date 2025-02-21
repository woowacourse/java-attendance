package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.util.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    private Attendances attendances;

    @BeforeEach
    void setUp() {
        attendances = new Attendances();
        Crews crews = new Crews();

        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource("attendances.csv");

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    @DisplayName("Crew와 LocalDate 객체로 원하는 Attendance를 찾아야한다.")
    @Test
    void findAttendanceByCrewAndDate() {
        Attendance findAttendance = attendances.findMatchCrewDate(
                new Crew("빙티"),
                LocalDate.of(2025, 2, 13)
        );

        // 빙티,2025-02-13 10:07 (csv 파일 내용 중)
        assertAll(
                () -> assertThat(findAttendance.getType()).isEqualTo(AttendanceType.LATE),
                () -> assertThat(findAttendance.getDate()).isEqualTo(LocalDate.of(2025, 2, 13))
        );
    }

    @DisplayName("크루와 날짜로 출석 기록을 찾아서 원하는 시간으로 기록을 수정한다.")
    @Test
    void modifyAttendanceWithNewTime() {
        // 원본 : 쿠키,2025-02-14 13:03
        // 수정시도 : 쿠키, 2025-2-14 10:00
        attendances.modifyAttendances(
                new Crew("쿠키"),
                LocalDateTime.of(2025, 2, 14, 10, 0, 0)
        );

        Attendance findAttendance = attendances.findMatchCrewDate(
                new Crew("쿠키"),
                LocalDate.of(2025, 2, 14)
        );

        assertAll(
                () -> assertThat(findAttendance.getType()).isEqualTo(AttendanceType.SAFE),
                () -> assertThat(findAttendance.getTimeValue()).isEqualTo("10:00")
        );
    }

    @DisplayName("크루의 모든 출석 기록을 조회해서 각 기록의 출석자가 해당 크루인지 확인한다.")
    @Test
    void findCrewAllAttendances() {
        List<Attendance> bingbongRecords = attendances.findCrewAttendances(new Crew("빙봉"));

//        bingBongRecords.size()는 날짜가 지날수록 늘어남, 테스트하기 안좋음, 21일 기준으로 size는 14개가 맞음
//        assertThat(bingBongRecords.size()).isEqualTo(14);

//        대신 모든 출석기록의 이름이 빙봉인지 확인하기
        bingbongRecords.forEach(record -> assertThat(record)
                        .extracting("crew")
                        .extracting("crewName")
                        .isEqualTo("빙봉")
        );


    }
}