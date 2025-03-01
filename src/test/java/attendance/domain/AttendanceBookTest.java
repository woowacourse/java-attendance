package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dto.AcademicStatusResultDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    private Set<String> names;
    private String name;
    private Attendances attendances;
    private AttendanceBook attendanceBook;
    private Map<LocalDate, Attendance> monthlyAttendances;

    @BeforeEach
    void setUp() {
        names = new HashSet<>();
        name = "체체";
        names.add(name);

        Attendance attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 21, 10, 6)));
        Attendance attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 25, 10, 6)));
        Attendance attendance3 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 26, 10, 6)));
        Attendance attendance4 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 6)));
        Attendance attendance5 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 28, 10, 6)));
        Attendance attendance6 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 17, 10, 31)));
        Attendance attendance7 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 18, 10, 31)));
        Attendance attendance8 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 19, 10, 31)));
        attendances = new Attendances(new HashSet<>());
        attendances.add(attendance1);
        attendances.add(attendance2);
        attendances.add(attendance3);
        attendances.add(attendance4);
        attendances.add(attendance5);
        attendances.add(attendance6);
        attendances.add(attendance7);
        attendances.add(attendance8);
        attendanceBook = new AttendanceBook(names, attendances);
        monthlyAttendances = attendances.getMonthlyAttendanceMap("체체", 2025, 2);
    }

    @DisplayName("입력된 이름이 출석부에 없다면 예외를 발생한다.")
    @Test
    void 입력된_이름이_출석부에_없다면_예외를_발생한다() {

        // given

        // when & then
        assertThatThrownBy(() -> attendanceBook.hasCrew("추추"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석부에 존재하지 않는 닉네임입니다.");
    }

    @DisplayName("입력된 이름이 출석부에 있다면 예외가 발생하지 않는다.")
    @Test
    void 입력된_이름이_출석부에_있다면_예외가_발생하지_않는다() {

        // given

        // when & then
        assertThatCode(() -> {
            attendanceBook.hasCrew("체체");
        }).doesNotThrowAnyException();
    }

    @DisplayName("특정 크루의 특정 출석 상태의 횟수를 구한다.")
    @Test
    void 특정_크루의_특정_출석_상태의_횟수를_구한다() {

        // given

        // when
        long totalCount = attendanceBook.getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.LATE);

        // then
        assertThat(totalCount).isEqualTo(5);
    }

    @DisplayName("특정 크루의 제적 상태를 구한다.")
    @Test
    void 특정_크루의_제적_상태를_구한다() {

        // given

        // when
        AcademicStatus academicStatus = attendanceBook.getAcademicStatusByCalendar(monthlyAttendances);

        // then
        assertThat(academicStatus).isEqualTo(AcademicStatus.EXPELLED);
    }

    @DisplayName("출석을 추가한다.")
    @Test
    void 출석을_추가한다() {

        // given
        Attendance attendance = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 3, 10, 0)));

        // when
        attendanceBook.addAttendance(attendance);
        Map<LocalDate, Attendance> resultAttendances = attendanceBook.findAttendancesByCrewNameAndYearAndMonth("체체",
                2025, 2);

        // then
        assertThat(resultAttendances.size()).isEqualTo(20);
    }

    @DisplayName("크루 이름, 년,월,일로 출석 기록 하나를 가져온다.")
    @Test
    void 크루_이름_년월일로_출석_기록_하나를_가져온다() {

        // given
        attendances.add(new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 5, 10, 0))));

        // when
        Attendance attendance = attendanceBook.findAttendanceByCrewNameAndLocalDate("체체", LocalDate.of(2025, 2, 5));

        // then
        assertThat(attendanceBook.findAttendanceByCrewNameAndLocalDate("체체", LocalDate.of(2025, 2, 5))
                .checkStatus()).isEqualTo(AttendanceStatus.ATTEND);
    }

    @DisplayName("크루 이름, 년,월로 해당 월의 출석 기록들을 가져온다.")
    @Test
    void 크루_이름_년월로_해당_월의_출석_기록들을_가져온다() {

        // given

        // when
        Map<LocalDate, Attendance> resultAttendances = attendanceBook.findAttendancesByCrewNameAndYearAndMonth("체체",
                2025, 2);

        // then
        assertThat(resultAttendances.size()).isEqualTo(20);
    }

    @DisplayName("제적 위험자를 가져온다.")
    @Test
    void 제적_위험자를_가져온다() {

        // given

        // when
        List<AcademicStatusResultDTO> academicStatusResultDTOS = attendanceBook.getExpulsionCrews(
                AcademicStatus.EXPELLED, LocalDate.of(2025, 2, 27));

        // then
        assertThat(academicStatusResultDTOS.size()).isEqualTo(1);
    }
}
