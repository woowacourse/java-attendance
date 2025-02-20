package domain;

import static org.assertj.core.api.Assertions.assertThat;

import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileManager;

class AttendanceTest {

    @DisplayName("특정 크루의 오늘 출석 시간을 저장한다.")
    @Test
    void save() {
        //given
        Crew crew = new Crew("도기");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.of(2024, 12, 2, 10, 00));
        localDateTimes.add(LocalDateTime.of(2024, 12, 3, 10, 06));
        localDateTimes.add(LocalDateTime.of(2024, 12, 4, 10, 11));
        localDateTimes.add(LocalDateTime.of(2024, 12, 5, 10, 14));

        Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();
        attendances.put(crew, localDateTimes);

        Attendance attendance = new Attendance(attendances);

        //when
        attendance.save(crew, "10:20", 6);

        //then
        assertThat(attendance.getAttendanceMap().get(crew)).hasSize(5);
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

        //when
        attendance.update(crew, "10:02", 4);
        List<LocalDateTime> actual = attendances.get(crew);

        //then
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
        //given
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

        //when
        List<AttendanceResultDto> actual = attendance.readRecord(crew, todayDay);

        //then
        assertThat(actual).hasSize(6);
    }

    @DisplayName("출결 기록을 바탕으로 제적 위험자를 확인한다.")
    @Test
    void getAbsence() {
        //given
        Attendance attendance = FileManager.readFile();

        //when
        Map<Crew, AbsenceResultDto> actual = attendance.getAbsence(14);

        //then
        assertThat(actual).hasSize(5);
    }
}
