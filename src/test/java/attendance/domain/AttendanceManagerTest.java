package attendance.domain;

import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.PenaltyCrewDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceManagerTest {

    @Test
    void 출석_데이터를_읽어온다() {
        // given
        Attendances expectedAttendances = new Attendances();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);
        expectedAttendances.addAttendance("쿠키", new Attendance(attendanceDate, LocalTime.of(10, 8)));
        expectedAttendances.addAttendance("빙봉", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendances.addAttendance("빙티", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendances.addAttendance("이든", new Attendance(attendanceDate, LocalTime.of(10, 7)));

        // when
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );

        // then
        assertThat(service).extracting("attendances").isEqualTo(expectedAttendances);
    }

    @Test
    void 출석_성공시_dto객체를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024, 12, 16);
        LocalTime attendanceTime = LocalTime.of(12, 50);

        // when
        AttendanceInfoDto dto = service.remarkAttendance("빙봉", attendanceDate, attendanceTime);

        // then
        assertThat(dto).isEqualTo(AttendanceInfoDto.of(attendanceDate, attendanceTime, AttendanceStatus.PRESENCE));
    }

    @Test
    void 출석_날짜가_캠퍼스_운영일이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024, 12, 14);
        LocalTime attendanceTime = LocalTime.of(12, 50);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", attendanceDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 출석_시간이_캠퍼스_운영시간이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024, 12, 16);
        LocalTime attendanceTime = LocalTime.of(23, 59);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", attendanceDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다.");
    }

    @Test
    void 출석_수정이_성공하면_dto를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate editDate = LocalDate.of(2024, 12, 13);
        LocalTime editTime = LocalTime.of(12, 50);

        // when
        AttendanceEditDto dto = service.editAttendance("빙봉", editDate, editTime);

        // then
        LocalTime beforeEditTime = LocalTime.of(10, 7);
        assertThat(dto).isEqualTo(
            AttendanceEditDto.of(editDate, beforeEditTime, AttendanceStatus.LATE, editTime, AttendanceStatus.ABSENCE));
    }

    @Test
    void 출석_수정_날짜가_캠퍼스_운영일이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate editDate = LocalDate.of(2024, 12, 14);
        LocalTime editTime = LocalTime.of(12, 50);

        // when & then
        assertThatThrownBy(() -> service.editAttendance("빙봉", editDate, editTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 출석_수정_시간이_캠퍼스_운영시간이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate editDate = LocalDate.of(2024, 12, 16);
        LocalTime editTime = LocalTime.of(23, 59);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", editDate, editTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다.");
    }

    @Test
    void 출석_조회시_dto를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate second = LocalDate.of(2024, 12, 2);
        LocalDate today = LocalDate.of(2024, 12, 3);

        // when
        AttendanceCheckDto dto = service.checkAttendance("빙티", today);

        // then
        AttendanceCheckDto expectedDto = AttendanceCheckDto.of("빙티",
            List.of(
                AttendanceInfoDto.of(second, null, AttendanceStatus.ABSENCE)
            ),
            Map.of(
                AttendanceStatus.LATE, 0,
                AttendanceStatus.PRESENCE, 0,
                AttendanceStatus.ABSENCE, 1
            ),
            AttendancePenalty.NONE
        );

        assertThat(dto).isEqualTo(expectedDto);
    }

    @Test
    void 제적_위험_크루_조회시_dto를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate today = LocalDate.of(2024, 12, 5);

        // when
        List<PenaltyCrewDto> dto = service.findPenaltyCrews(today);

        // then
        List<PenaltyCrewDto> expectedDto = List.of(
            PenaltyCrewDto.of(
                new PenaltyCrew("빙봉", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0))),
            PenaltyCrewDto.of(
                new PenaltyCrew("빙티", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0))),
            PenaltyCrewDto.of(
                new PenaltyCrew("이든", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0))),
            PenaltyCrewDto.of(
                new PenaltyCrew("쿠키", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0)))
        );

        assertThat(dto).isEqualTo(expectedDto);
    }
}
