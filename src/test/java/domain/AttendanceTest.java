package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dto.AbsenceHistoryDto;
import dto.AttendanceHistoryDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import util.FileManager;

class AttendanceTest {

    @DisplayName("입력받은 크루가 존재하지 않는 크루라면 예외를 발생한다.")
    @Test
    void nonExistenceCrew() {
        // given
        Attendance attendance = FileManager.readFile();
        String name = "도기";

        // when & then
        assertThatThrownBy(() -> attendance.getCrewByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 크루 입니다.");
    }

    @DisplayName("입력받은 크루가 존재하는 크루를 반환한다.")
    @Test
    void existenceCrew() {
        // given
        Attendance attendance = FileManager.readFile();
        String name = "빙티";

        // when
        Crew actual = attendance.getCrewByName(name);

        // then
        assertThat(actual.getName()).isEqualTo("빙티");

    }

    @DisplayName("특정 크루의 오늘 출석 시간을 저장한다.")
    @Test
    void save() {
        // given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        // when
        attendance.save(crew, "10:20", LocalDate.of(2024, 12, 8));

        // then
        assertThat(attendance.getAttendanceMap().get(crew)).hasSize(5);
    }

    @DisplayName("이미 출석한 크루가 다시 출석하면 예외가 발생한다.")
    @Test
    void duplicateSave() {
        // given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        // when & then
        assertThatThrownBy(() -> attendance.save(crew, "09:55", LocalDate.of(2024, 12, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 크루입니다.");
    }

    @DisplayName("하루에 한 번은 출석할 수 있다.")
    @Test
    void nonDuplicateSave() {
        // given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        // when & then
        assertThatCode(() -> attendance.save(crew, "09:55", LocalDate.of(2024, 12, 8)))
                .doesNotThrowAnyException();
    }

    @DisplayName("특정 크루의 출석 수정 시간을 저장한다.")
    @Test
    void update() {
        // given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        // when
        attendance.update(crew, "10:02", 4);
        List<LocalDateTime> actual = attendances.get(crew);

        // then
        assertThat(actual).containsExactly(
                LocalDateTime.of(2024, 12, 2, 10, 00),
                LocalDateTime.of(2024, 12, 3, 10, 06),
                LocalDateTime.of(2024, 12, 4, 10, 02),
                LocalDateTime.of(2024, 12, 5, 10, 14)
        );
    }

    @DisplayName("특정 크루의 출석부를 조회한다.")
    @Test
    void readRecord() {
        // given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        int todayDay = 10;

        // when
        List<AttendanceHistoryDto> actual = attendance.readRecord(crew);

        // then
        assertThat(actual).hasSize(6);
    }

    @DisplayName("출결 기록을 바탕으로 제적 위험자를 확인한다.")
    @Test
    void getAbsence() {
        // given
        Attendance attendance = FileManager.readFile();

        // when
        Map<Crew, AbsenceHistoryDto> actual = attendance.getAbsenceHistory();

        // then
        assertThat(actual).hasSize(5);
    }

    @DisplayName("출석 수정일이 공휴일일 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 14, 15, 21, 22, 25, 28, 29})
    void updateHoliday(int dayOfMonth) {
        // given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        // when & then
        Assertions.assertThatThrownBy(() -> attendance.update(crew, "09:55", dayOfMonth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석을 할 수 없습니다.");
    }
}
