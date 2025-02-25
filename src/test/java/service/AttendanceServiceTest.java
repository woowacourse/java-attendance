package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

    @Test
    @DisplayName("닉네임, 수정하려는 날짜, 시간을 받아 출석 기록을 수정한다")
    void modifyAttendanceRecordTest() {
        // given
        String nickname = "name";
        LocalDate date = LocalDate.of(2025, 2, 4);
        int day = 4;
        LocalTime before = LocalTime.of(10, 31);
        LocalTime after = LocalTime.of(10, 5);
        CrewRepository.addCrew(new Crew(nickname));
        AttendanceRecordRepository.add(
                new AttendanceRecord(nickname, date, before, AttendanceStatus.of(date, before)));
        ModifyAttendanceRequest request = ModifyAttendanceRequest.of(nickname, date, day, after);

        // when
        attendanceService.modifyAttendanceRecord(request);

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(AttendanceRecordRepository.exists(nickname, date)).isTrue();
            AttendanceRecord record = AttendanceRecordRepository.find(nickname, date);
            softAssertions.assertThat(record.time()).isEqualTo(after);
        });
    }

    @Test
    @DisplayName("수정하려는 출석 기록과 동일한 출석 기록이 존재하면 예외가 발생한다")
    void modifyAttendanceRecordTest_SameAttendanceRecordExistsException() {
        // given
        String nickname = "name";
        LocalDate date = LocalDate.of(2025, 2, 4);
        int day = 4;
        LocalTime before = LocalTime.of(10, 31);
        LocalTime after = before;
        CrewRepository.addCrew(new Crew(nickname));
        AttendanceRecordRepository.add(
                new AttendanceRecord(nickname, date, before, AttendanceStatus.of(date, before)));
        ModifyAttendanceRequest request = ModifyAttendanceRequest.of(nickname, date, day, after);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            attendanceService.modifyAttendanceRecord(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}