package attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceTime;
import attendance.domain.CrewAttendanceInformation;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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

        attendance1 = new Attendance("체체", new AttendanceTime(localDate1, hour, minute, false));
        attendance2 = new Attendance("체체", new AttendanceTime(localDate2, hour, minute, false));
    }

    @DisplayName("출결 기록을 추가한다.")
    @Test
    void 출결_기록을_추가한다() {

        // given
        Attendance attendance = new Attendance("피글렛", new AttendanceTime(localDate1, hour, minute, false));
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());

        // when
        attendanceRepository.add(attendance);

        // then
        assertThat(attendanceRepository.findAllAttendanceByName("피글렛").size()).isEqualTo(1);
    }

    @DisplayName("해당 날짜에 출결 기록이 있는 크루를 추가할 시 예외가 발생한다.")
    @Test
    void 해당_날짜에_출결_기록이_있는_크루를_추가할_시_예외가_발생한다() {

        // given
        Attendance attendance = new Attendance("체체", new AttendanceTime(localDate1, hour, minute, false));
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
        Attendance attendance = new Attendance("체체", new AttendanceTime(localDate1, hour, minute, false));
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
        Attendance attendance1 = new Attendance("체체", new AttendanceTime(localDate1, hour, minute, false));
        Attendance attendance2 = new Attendance("체체", new AttendanceTime(localDate2, hour, minute, false));

        // when
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>(
                List.of(attendance1, attendance2)));

        // then
        assertThat(attendanceRepository.findAllAttendanceByName("체체").size()).isEqualTo(2);
    }

    @DisplayName("해당 닉네임을 가진 크루의 출석 상태를 가져온다.")
    @ParameterizedTest
    @MethodSource("name")
    void 해당_닉네임을_가진_크루의_출석_상태를_가져온다(List<Attendance> attendances, CrewAttendanceInformation expectedResult) {

        // given
        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);

        // when
        CrewAttendanceInformation result = attendanceRepository.getAcademicStatusByName("체체");

        // then
        assertAll(() -> {
            assertEquals(result.attend(), expectedResult.attend());
            assertEquals(result.late(), expectedResult.late());
            assertEquals(result.absent(), expectedResult.absent());
            assertEquals(result.academicStatus(), expectedResult.academicStatus());
        });
    }

    private static Stream<Arguments> name() {
        String crewName = "체체";
        return Stream.of(
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 20)),
                        new CrewAttendanceInformation(crewName, 0, 0, 1, "X")
                ),
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 10),
                                makeAbsentAttendance(crewName, 2025, 2, 11)),
                        new CrewAttendanceInformation(crewName, 0, 0, 2, "경고")
                ),
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 10),
                                makeAbsentAttendance(crewName, 2025, 2, 11),
                                makeAbsentAttendance(crewName, 2025, 2, 12)),
                        new CrewAttendanceInformation(crewName, 0, 0, 3, "면담")
                ),
                Arguments.of(
                        List.of(makeAbsentAttendance(crewName, 2025, 2, 10),
                                makeAbsentAttendance(crewName, 2025, 2, 11),
                                makeAbsentAttendance(crewName, 2025, 2, 12),
                                makeAbsentAttendance(crewName, 2025, 2, 13),
                                makeAbsentAttendance(crewName, 2025, 2, 14),
                                makeAbsentAttendance(crewName, 2025, 2, 17)),
                        new CrewAttendanceInformation(crewName, 0, 0, 6, "제적")
                )
        );
    }

    private static Attendance makeAbsentAttendance(String name, int year, int month, int day) {
        return new Attendance(name, new AttendanceTime(LocalDate.of(year, month, day), "18", "00", true));
    }

    @DisplayName("입력 받은 크루에 대해 이번 달 출석 기록이 없는 날짜는 결석으로 처리한다")
    @Test
    void 입력_받은_크루에_대해_이번_달_출석_기록이_없는_날짜는_결석으로_처리한다() {

        // given
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());

        // when
        attendanceRepository.initAbsent(Set.of("피글렛", "체체"));

        // then
        assertAll(() ->
                {
                    assertEquals(countWeekDays(), attendanceRepository.findAllAttendanceByName("피글렛").size());
                    assertEquals(countWeekDays(), attendanceRepository.findAllAttendanceByName("체체").size());
                }
        );
    }

    private int countWeekDays() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDateTime.now().getDayOfMonth();
        return (int) IntStream.range(1, currentDay)
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> !isWeekend(date))
                .count();
    }

    private boolean isWeekend(final LocalDate date) {

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    @DisplayName("제적 위험자 리스트를 가져온다.")
    @ParameterizedTest
    @CsvSource(value = {
            "체체,제적", "체글렛,면담", "피글렛,경고"
    })
    void 제적_위험자_리스트를_가져온다(String name, String academicStatus) {

        // given
        AttendanceBook attendanceBook = new AttendanceBook(Set.of("체체", "체글렛", "피글렛", "피글체"));
        List<Attendance> attendances = makeAttendance();
        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);

        // when
        List<CrewAttendanceInformation> crewAttendanceInformations = attendanceRepository.getCrewAtRiskOfExpulsion(
                attendanceBook.getNames(), academicStatus);

        // then
        assertAll(() -> {
            assertEquals(crewAttendanceInformations.size(), 1);
            assertEquals(crewAttendanceInformations.getFirst().crewName(), name);
        });
    }

    private static List<Attendance> makeAttendance() {

        List<Attendance> attendances = new ArrayList<>(List.of(makeAbsentAttendance("체체", 2025, 2, 10),
                makeAbsentAttendance("체체", 2025, 2, 11),
                makeAbsentAttendance("체체", 2025, 2, 12),
                makeAbsentAttendance("체체", 2025, 2, 13),
                makeAbsentAttendance("체체", 2025, 2, 14),
                makeAbsentAttendance("체체", 2025, 2, 17)));

        attendances.addAll(List.of(makeAbsentAttendance("피글렛", 2025, 2, 10),
                makeAbsentAttendance("피글렛", 2025, 2, 11)));

        attendances.addAll(List.of(makeAbsentAttendance("체글렛", 2025, 2, 10),
                makeAbsentAttendance("체글렛", 2025, 2, 11),
                makeAbsentAttendance("체글렛", 2025, 2, 12)));

        attendances.add(makeAbsentAttendance("피글체", 2025, 2, 10));

        return attendances;
    }
}