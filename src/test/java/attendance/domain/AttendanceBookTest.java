package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    private List<String> names;
    private String name;
    private Attendances attendances;
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        names = new ArrayList<>();
        name = "체체";
        names.add(name);

        Attendance attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 24, 10, 6)));
        Attendance attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 25, 10, 6)));
        Attendance attendance3 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 26, 10, 6)));
        Attendance attendance4 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 6)));
        Attendance attendance5 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 28, 10, 6)));
        Attendance attendance6 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 17, 10, 31)));
        Attendance attendance7 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 18, 10, 31)));
        Attendance attendance8 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 19, 10, 31)));

        attendances = new Attendances(
                Set.of(attendance1, attendance2, attendance3, attendance4, attendance5, attendance6, attendance7,
                        attendance8));
        attendanceBook = new AttendanceBook(names, attendances);
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
        long totalCount = attendanceBook.getCountAcademicStatus(AttendanceStatus.LATE, "체체", 2025, 2, 10, 0);

        // then
        assertThat(totalCount).isEqualTo(5);
    }

    @DisplayName("특정 크루의 제적 상태를 구한다.")
    @Test
    void 특정_크루의_제적_상태를_구한다() {

        // given

        // when
        AcademicStatus academicStatus = attendanceBook.getAcademicStatusByCrewName("체체", 2025, 2, 10, 0);

        // then
        assertThat(academicStatus).isEqualTo(AcademicStatus.INTERVIEW);
    }
}
