package model;

import static constant.ErrorMessage.ALREADY_CHECK_IN;
import static constant.ErrorMessage.CANNOT_CHECK_IN_ON_HOLIDAY;
import static constant.ErrorMessage.NOT_FOUND_CREW;
import static constant.ErrorMessage.OUT_OF_OPERATION_HOURS;
import static constant.PathConstant.ATTENDANCE_FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dto.AttendanceCheckInRequest;
import dto.AttendanceCheckInResponse;
import dto.AttendanceHistoryRequest;
import dto.AttendanceHistoryResponse;
import dto.AttendanceRiskCrewsResponse;
import dto.AttendanceUpdateRequest;
import dto.AttendanceUpdateResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DateTimeGenerator;
import util.FileParser;
import util.FixedDateTimeStrategy;

class AttendancesTest {

    LocalDate fixedDate;
    DateTimeGenerator dateTimeGenerator;
    Attendances attendances;

    @BeforeEach
    void beforeEach() {
        fixedDate = LocalDate.of(2024, 12, 13);
        FixedDateTimeStrategy fixedDateTimeStrategy = new FixedDateTimeStrategy(fixedDate);
        dateTimeGenerator = new DateTimeGenerator(fixedDateTimeStrategy);

        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());
        attendances = Attendances.from(lines, dateTimeGenerator);
    }

    @Test
    @DisplayName("Attendances 초기 설정을 진행한다.")
    void test1() {
        // given
        Crew miso = Crew.of("미소");
        Crew neo = Crew.of("네오");
        Crew pobi = Crew.of("포비");

        // when
//        Attendances attendances = Attendances.from(lines, dateTimeGenerator);

        // then
        int expected = 9;
        assertThat(attendances.getAttendancesByCrew(miso)).hasSize(expected);
        assertThat(attendances.getAttendancesByCrew(neo)).hasSize(expected);
        assertThat(attendances.getAttendancesByCrew(pobi)).hasSize(expected);
    }

    @Test
    @DisplayName("출석을 진행한다. (출석)")
    void test2() {
        // given
        String nickname = "미소";
        String checkInTime = "10:00";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);

        // when
        AttendanceCheckInResponse response = attendances.add(request, dateTimeGenerator);

        // then
        assertThat(response.checkInDate()).isEqualTo(dateTimeGenerator.now());
        assertThat(response.checkInTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(response.attendanceType()).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("출석을 진행한다. (지각)")
    void test3() {
        // given
        String nickname = "미소";
        String checkInTime = "10:06";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);

        // when
        AttendanceCheckInResponse response = attendances.add(request, dateTimeGenerator);

        // then
        assertThat(response.checkInDate()).isEqualTo(dateTimeGenerator.now());
        assertThat(response.checkInTime()).isEqualTo(LocalTime.of(10, 6));
        assertThat(response.attendanceType()).isEqualTo(AttendanceType.BE_LATE);
    }

    @Test
    @DisplayName("출석을 진행한다. (결석)")
    void test4() {
        // given
        String nickname = "미소";
        String checkInTime = "10:31";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);

        // when
        AttendanceCheckInResponse response = attendances.add(request, dateTimeGenerator);

        // then
        assertThat(response.checkInDate()).isEqualTo(dateTimeGenerator.now());
        assertThat(response.checkInTime()).isEqualTo(LocalTime.of(10, 31));
        assertThat(response.attendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }

    @Test
    @DisplayName("없는 크루가 출석을 시도하면 예외가 발생한다.")
    void test5() {
        // given
        String nickname = "헤일러";
        String checkInTime = "10:00";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);

        // when & then
        assertThatThrownBy(() -> attendances.add(request, dateTimeGenerator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_FOUND_CREW.getMessage());
    }

    @Test
    @DisplayName("출석을 수정한다. (지각)")
    void test6() {
        // given
        String nickname = "미소";
        String day = "6";
        String updateTime = "10:00";
        AttendanceUpdateRequest request = new AttendanceUpdateRequest(nickname, day, updateTime);

        // when
        AttendanceUpdateResponse response = attendances.update(request, dateTimeGenerator);

        // then
        assertThat(response.date()).isEqualTo(LocalDate.of(2024, 12, 6));
        assertThat(response.previousTime()).isEqualTo(LocalTime.of(10, 30));
        assertThat(response.previousAttendanceType()).isEqualTo(AttendanceType.BE_LATE);
        assertThat(response.updateTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(response.updateAttendanceType()).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("출석을 수정한다. (결석)")
    void test7() {
        // given
        String nickname = "미소";
        String day = "4";
        String updateTime = "10:00";
        AttendanceUpdateRequest request = new AttendanceUpdateRequest(nickname, day, updateTime);

        // when
        AttendanceUpdateResponse response = attendances.update(request, dateTimeGenerator);

        // then
        assertThat(response.date()).isEqualTo(LocalDate.of(2024, 12, 4));
        assertThat(response.previousTime()).isNull();
        assertThat(response.previousAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
        assertThat(response.updateTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(response.updateAttendanceType()).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("특정 크루의 출석 기록을 가져온다.")
    void test8() {
        // given
        String nickname = "미소";
        AttendanceHistoryRequest request = new AttendanceHistoryRequest(nickname);

        // when
        AttendanceHistoryResponse response = attendances.findHistoryByCrew(request, dateTimeGenerator);

        // then
        assertThat(response.nickname()).isEqualTo(nickname);
        assertThat(response.attendances()).hasSize(9);
        assertThat(response.attendanceTotal().get(AttendanceType.SUCCESS)).isEqualTo(3);
        assertThat(response.attendanceTotal().get(AttendanceType.BE_LATE)).isEqualTo(2);
        assertThat(response.attendanceTotal().get(AttendanceType.ABSENCE)).isEqualTo(4);
        assertThat(response.punishmentType()).isEqualTo(PunishmentType.MEETING);
    }

    @Test
    @DisplayName("제적 위험자 리스트를 가져온다.")
    void test9() {
        // given

        // when
        AttendanceRiskCrewsResponse response = attendances.findRiskCrews();

        // then
        assertThat(response.riskCrewResponses().get(0).crew().getNickname()).isEqualTo("네오");
        assertThat(response.riskCrewResponses().get(1).crew().getNickname()).isEqualTo("미소");
        assertThat(response.riskCrewResponses().get(2).crew().getNickname()).isEqualTo("포비");
        assertThat(response.riskCrewResponses().get(0).attendanceTotal().get(AttendanceType.ABSENCE)).isEqualTo(6);
        assertThat(response.riskCrewResponses().get(0).attendanceTotal().get(AttendanceType.BE_LATE)).isEqualTo(0);
        assertThat(response.riskCrewResponses().get(1).attendanceTotal().get(AttendanceType.ABSENCE)).isEqualTo(4);
        assertThat(response.riskCrewResponses().get(1).attendanceTotal().get(AttendanceType.BE_LATE)).isEqualTo(2);
        assertThat(response.riskCrewResponses().get(2).attendanceTotal().get(AttendanceType.ABSENCE)).isEqualTo(5);
        assertThat(response.riskCrewResponses().get(2).attendanceTotal().get(AttendanceType.BE_LATE)).isEqualTo(2);
        assertThat(response.riskCrewResponses().get(0).punishmentType()).isEqualTo(PunishmentType.EXPULSION);
        assertThat(response.riskCrewResponses().get(1).punishmentType()).isEqualTo(PunishmentType.MEETING);
        assertThat(response.riskCrewResponses().get(2).punishmentType()).isEqualTo(PunishmentType.MEETING);
    }

    @Test
    @DisplayName("공휴일에 출석을 시도하는 경우 예외가 발생한다.")
    void test10() {
        fixedDate = LocalDate.of(2024, 12, 25);
        FixedDateTimeStrategy fixedDateTimeStrategy = new FixedDateTimeStrategy(fixedDate);
        dateTimeGenerator = new DateTimeGenerator(fixedDateTimeStrategy);

        String nickname = "미소";
        String checkInTime = "10:00";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);

        // when & then
        assertThatThrownBy(() -> attendances.add(request, dateTimeGenerator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CANNOT_CHECK_IN_ON_HOLIDAY.getMessage());
    }

    @Test
    @DisplayName("운영 시간이 아닐 때 출석을 시도하는 경우 예외가 발생한다.")
    void test11() {
        String nickname = "미소";
        String checkInTime = "07:00";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);

        // when & then
        assertThatThrownBy(() -> attendances.add(request, dateTimeGenerator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(OUT_OF_OPERATION_HOURS.getMessage());
    }

    @Test
    @DisplayName("이미 체크인 한 경우 다시 체크인하면 예외가 발생한다.")
    void test12() {
        String nickname = "미소";
        String checkInTime = "10:00";
        AttendanceCheckInRequest request = new AttendanceCheckInRequest(nickname, checkInTime);
        attendances.add(request, dateTimeGenerator);

        // when & then
        assertThatThrownBy(() -> attendances.add(request, dateTimeGenerator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ALREADY_CHECK_IN.getMessage());
    }
}
