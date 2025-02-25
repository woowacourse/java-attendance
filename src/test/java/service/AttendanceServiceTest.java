package service;

import controller.dto.SaveAttendanceRequest;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRecordRepository;
import repository.CrewRepository;

class AttendanceServiceTest {
    private final AttendanceService attendanceService = new AttendanceService();

    @AfterEach
    void clearRepository() {
        AttendanceRecordRepository.clear();
        CrewRepository.clear();
    }

    @Test
    @DisplayName("닉네임과 등교시간을 받아 출석 기록을 저장한다")
    void saveAttendanceRecordTest() {
        // given
        String nickname = "name";
        LocalDate date = LocalDate.of(2025, 2, 4);
        LocalTime time = LocalTime.of(10, 30);
        CrewRepository.addCrew(new Crew(nickname));
        SaveAttendanceRequest request = new SaveAttendanceRequest(nickname, date, time);

        // when
        attendanceService.saveAttendanceRecord(request);

        // then
        Assertions.assertThat(AttendanceRecordRepository.exists(nickname, date)).isTrue();
    }
}