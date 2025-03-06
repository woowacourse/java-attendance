import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.AttendanceRecords;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordsTest {

    @DisplayName("출석을 추가하였을 때 이미 출석한 기록이 있다면 예외를 발생시킨다")
    @Test
    void validateExistByDate() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        AttendanceRecords attendanceRecords = AttendanceRecords.create(new ArrayList<>(List.of(attendance)));

        // when && then
        assertThatThrownBy(
                () -> attendanceRecords.add(Attendance.create(localDate, null, null))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 날짜에 출석 기록이 이미 존재합니다");
    }

    @DisplayName("출석을 추가할 수 있다")
    @Test
    void add() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        AttendanceRecords attendanceRecords = AttendanceRecords.create(new ArrayList<>(List.of()));

        // when
        attendanceRecords.add(attendance);

        // then
        assertThat(attendanceRecords).extracting("attendances").asInstanceOf(InstanceOfAssertFactories.LIST)
                .contains(attendance);
    }

    @DisplayName("원하는 날짜의 출석을 조회할 수 있다")
    @Test
    void retrieveByDate() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        AttendanceRecords attendanceRecords = AttendanceRecords.create(new ArrayList<>(List.of(attendance)));

        //when
        Attendance foundAttendance = attendanceRecords.retrieveAttendanceByDate(localDate);

        //then
        assertThat(foundAttendance).isSameAs(attendance);
    }

    @DisplayName("입력 날짜의 전날까지 출석 기록 조회한다")
    @Test
    void retrieveAllAttendanceUntilDate() {
        //given
        LocalDate today = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(today, 10, 0);

        LocalDate oldest = LocalDate.of(2024, 12, 12);
        Attendance attendance2 = AttendanceFixture.createAttendance(oldest, 10, 0);

        AttendanceRecords attendanceRecords = AttendanceRecords.create(
                new ArrayList<>(List.of(attendance, attendance2)));

        // when
        List<Attendance> result = attendanceRecords.retrieveAllFilledNonExistingDay(oldest, today);

        // then
        assertThat(result).contains(attendance2);
    }

    @DisplayName("가장 오래된 날짜부터 오늘 날짜까지 출석 기록이 없다면, 결석 기록으로 채워준다")
    @Test
    void retrieveAllFilledNonExistingDay() {
        //given
        LocalDate today = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(today, 10, 0);

        LocalDate oldest = LocalDate.of(2024, 12, 9);
        Attendance attendance2 = AttendanceFixture.createAttendance(oldest, 10, 0);

        AttendanceRecords attendanceRecords = AttendanceRecords.create(
                new ArrayList<>(List.of(attendance, attendance2)));

        // when
        List<Attendance> result = attendanceRecords.retrieveAllFilledNonExistingDay(oldest, today);

        // then
        assertThat(result.size()).isEqualTo(4);
    }

    @DisplayName("출석 중 가장 오래된 날짜를 조회한다")
    @Test
    void retrieveOldestDate() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(today, 10, 0);

        LocalDate date = LocalDate.of(2024, 12, 12);
        Attendance attendance2 = AttendanceFixture.createAttendance(date, 10, 0);

        LocalDate oldest = LocalDate.of(2024, 12, 11);
        Attendance attendance3 = AttendanceFixture.createAttendance(oldest, 10, 0);

        AttendanceRecords attendanceRecords = AttendanceRecords.create(new ArrayList<>(
                List.of(attendance, attendance2, attendance3)
        ));

        // when
        LocalDate oldestDate = attendanceRecords.retrieveOldestDate();

        // then
        assertThat(oldestDate).isEqualTo(oldest);
    }

    @DisplayName("오래된 날짜 조회 중 출석 기록이 없다면 예외를 발생시킨다")
    @Test
    void retrieveOldestDateNotExist() {
        // given
        AttendanceRecords attendanceRecords = AttendanceRecords.create(new ArrayList<>(List.of()));

        // when & then
        assertThatThrownBy(
                attendanceRecords::retrieveOldestDate
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 없어 가장 오래된 날짜를 찾을 수 없습니다");
    }


}
