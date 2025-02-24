package attendance.repository;

import static attendance.AttendanceFixture.makeAbsentAttendance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import attendance.domain.Attendance;
import attendance.domain.Time;
import attendance.dto.CrewNameAndAcademicStatusDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceRepositoryTest {

    LocalDate localDate1;
    LocalDate localDate2;
    String hour;
    String minute;
    Attendance attendance1;
    Attendance attendance2;

    @BeforeEach
    void setUp() {
        localDate1 = LocalDate.of(2025, 2, 17);
        hour = "10";
        minute = "00";
        localDate2 = LocalDate.of(2025, 2, 18);

        attendance1 = new Attendance("체체", new Time(localDate1, hour, minute, false));
        attendance2 = new Attendance("체체", new Time(localDate2, hour, minute, false));


    }

    @DisplayName("출결 기록을 추가한다.")
    @Test
    void 출결_기록을_추가한다() {

        // given
        Attendance attendance = new Attendance("피글렛", new Time(localDate1, hour, minute, false));
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());

        // when
        attendanceRepository.add(attendance);

        // then
        assertThat(attendanceRepository.findAllAttendanceByName("피글렛", 2).size()).isEqualTo(1);
    }

    @DisplayName("해당 날짜에 출결 기록이 있는 크루를 추가할 시 예외가 발생한다.")
    @Test
    void 해당_날짜에_출결_기록이_있는_크루를_추가할_시_예외가_발생한다() {

        // given
        Attendance attendance = new Attendance("체체", new Time(localDate1, hour, minute, false));
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());
        attendanceRepository.add(attendance);

        // when & then
        assertThatThrownBy(() -> attendanceRepository.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 오늘은 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
    }

    @DisplayName("입력 받은 날짜의 크루 출석 기록을 가져온다.")
    @Test
    void 입력_받은_날짜의_크루_출석_기록을_가져온다() {

        // given
        Attendance attendance = new Attendance("체체", new Time(localDate1, hour, minute, false));
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());
        attendanceRepository.add(attendance);

        //when
        Attendance resultAttendance = attendanceRepository.findAttendanceByNameAndLocalDate("체체", localDate1.getYear(),
                localDate1.getMonthValue(), 17);

        //then
        assertThat(attendance).isEqualTo(resultAttendance);
    }

    @DisplayName("해당 닉네임을 가진 크루의 그 달 출석 기록을 가져온다.")
    @Test
    void 해당_닉네임을_가진_크루의_그_달_출석_기록을_가져온다() {

        // given
        int today = LocalDate.now().getDayOfMonth();
        Attendance attendance1 = new Attendance("체체", new Time(localDate1, hour, minute, false));
        Attendance attendance2 = new Attendance("체체", new Time(localDate2, hour, minute, false));

        // when
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>(
                List.of(attendance1, attendance2)));
        // then
        assertThat(attendanceRepository.findAllAttendanceByName("체체", 2).size()).isEqualTo(2);
    }

    @DisplayName("해당 닉네임을 가진 크루의 출석 상태를 가져온다.")
    @ParameterizedTest
    @MethodSource("makeAttendance")
    void 해당_닉네임을_가진_크루의_출석_상태를_가져온다(List<Attendance> attendances, CrewNameAndAcademicStatusDTO expectedResult) {

        // given
        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);

        // when
        CrewNameAndAcademicStatusDTO result = attendanceRepository.getAcademicStatusByName("체체", 2);

        // then
        assertAll(() -> {
            assertEquals(result.attend(), expectedResult.attend());
            assertEquals(result.late(), expectedResult.late());
            assertEquals(result.absent(), expectedResult.absent());
            assertEquals(result.academicStatus(), expectedResult.academicStatus());
        });
    }

    public static Stream<Arguments> makeAttendance() {
        String crewName = "체체";
        return Stream.of(
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 20)),
                        new CrewNameAndAcademicStatusDTO(crewName, 0, 0, 1, "없음")
                ),
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 10),
                                makeAbsentAttendance(crewName, 2025, 2, 11)),
                        new CrewNameAndAcademicStatusDTO(crewName, 0, 0, 2, "경고")
                ),
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 10),
                                makeAbsentAttendance(crewName, 2025, 2, 11),
                                makeAbsentAttendance(crewName, 2025, 2, 12)),
                        new CrewNameAndAcademicStatusDTO(crewName, 0, 0, 3, "면담")
                ),
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 10),
                                makeAbsentAttendance(crewName, 2025, 2, 11),
                                makeAbsentAttendance(crewName, 2025, 2, 12),
                                makeAbsentAttendance(crewName, 2025, 2, 13),
                                makeAbsentAttendance(crewName, 2025, 2, 14),
                                makeAbsentAttendance(crewName, 2025, 2, 17)),
                        new CrewNameAndAcademicStatusDTO(crewName, 0, 0, 6, "제적")
                )
        );
    }
}
