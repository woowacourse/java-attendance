import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Crew;
import domain.ErrorCode;
import domain.PenaltyStatus;
import dto.AttendanceRecordResponse;
import dto.CrewPenaltyResponse;
import dto.ModifyAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Parser.NameParsedData;

class AttendanceBookTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        attendanceBook = new AttendanceBook();
    }

    @Test
    void 출석부에_기존이름이_존재여부_확인() {
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThat(attendanceBook.checkCrewAlreadyExists("쿠키")).isEqualTo(true);
        assertThat(attendanceBook.checkCrewAlreadyExists("없음")).isEqualTo(false);
    }

    @Test
    void 캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.validateIsInOperationHour(LocalTime.of(7, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
    }

    @Test
    void 초기_출석_데이터를_불러온다() {
        attendanceBook.initializeAttendanceBook(List.of(
                new NameParsedData("쿠키", "2024-12-11 10:08"),
                new NameParsedData("쿠키", "2024-12-12 08:08"),
                new NameParsedData("쿠키", "2024-12-13 09:08"),
                new NameParsedData("빙봉", "2024-12-13 10:08")
        ));

        Crew crew1 = attendanceBook.findCrewByName("쿠키");
        Crew crew2 = attendanceBook.findCrewByName("빙봉");

        assertThat(crew1.getAttendanceRecords()).containsAll(
                List.of(new AttendanceRecordResponse(
                                LocalDate.parse("2024-12-11"),
                                LocalTime.parse("10:08"),
                                AttendanceStatus.LATE),
                        new AttendanceRecordResponse(
                                LocalDate.parse("2024-12-12"),
                                LocalTime.parse("08:08"),
                                AttendanceStatus.ATTEND),
                        new AttendanceRecordResponse(
                                LocalDate.parse("2024-12-13"),
                                LocalTime.parse("09:08"),
                                AttendanceStatus.ATTEND)
                )
        );
        assertThat(crew2.getAttendanceRecords()).contains(
                new AttendanceRecordResponse(
                        LocalDate.parse("2024-12-13"),
                        LocalTime.parse("10:08"),
                        AttendanceStatus.LATE)
        );
    }

    @Test
    void 제적_위험자를_파악한다() {
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 10),
                LocalDate.of(2024, 12, 4), LocalTime.of(8, 30),
                LocalDate.of(2024, 12, 5), LocalTime.of(9, 0)
        ));

        attendanceBook.addNewCrew(crew1);

        List<CrewPenaltyResponse> penaltyResponses = attendanceBook.checkPenaltyCrew();

        assertThat(penaltyResponses).anyMatch(response ->
                response.name().equals("쿠키") &&
                        response.absentCount() == 17 &&
                        response.lateCount() == 1 &&
                        response.penaltyStatus() == PenaltyStatus.EXPULSION
        );
    }

    @Test
    void 정상적인_출석_체크가_가능하다() {
        Crew crew = Crew.createByName("쿠키");
        attendanceBook.addNewCrew(crew);

        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime time = LocalTime.of(9, 0);

        AttendanceRecordResponse response = attendanceBook.checkAttendance("쿠키", Map.of(date, time));

        assertThat(response.date()).isEqualTo(date);
        assertThat(response.time()).isEqualTo(time);
        assertThat(response.attendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 존재하지_않는_크루의_출석을_체크하면_예외가_발생한다() {
        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime time = LocalTime.of(9, 0);

        assertThatThrownBy(() -> attendanceBook.checkAttendance("없는크루", Map.of(date, time)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorCode.NICKNAME_NOT_FOUND.getMessage());
    }

    @Test
    void 주말_또는_휴일에_출석을_체크하면_예외가_발생한다() {
        Crew crew = Crew.createByName("쿠키");
        attendanceBook.addNewCrew(crew);

        LocalDate holiday = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(9, 0);

        assertThatThrownBy(() -> attendanceBook.checkAttendance("쿠키", Map.of(holiday, time)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(ErrorCode.HOLIDAY_NOT_WORKING_DAY_FORMAT.getMessage(), 25));
    }

    @Test
    void 운영_시간_외_출석을_체크하면_예외가_발생한다() {
        Crew crew = Crew.createByName("쿠키");
        attendanceBook.addNewCrew(crew);

        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime invalidTime = LocalTime.of(7, 0);

        assertThatThrownBy(() -> attendanceBook.checkAttendance("쿠키", Map.of(date, invalidTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getMessage());
    }

    @Test
    void 정상적으로_출석_시간을_수정한다() {
        Crew crew = Crew.createByName("쿠키");
        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime originalTime = LocalTime.of(10, 20);
        LocalTime modifiedTime = LocalTime.of(8, 30);

        crew.addDailyAttendance(Map.of(date, originalTime));
        attendanceBook.addNewCrew(crew);

        ModifyAttendanceResponse response = attendanceBook.modifyAttendance("쿠키", Map.of(date, modifiedTime));

        assertThat(response.date()).isEqualTo(date);
        assertThat(response.originalTime()).isEqualTo(originalTime);
        assertThat(response.modifiedTime()).isEqualTo(modifiedTime);
        assertThat(response.originalStatus()).isEqualTo(AttendanceStatus.LATE);
        assertThat(response.modifiedStatus()).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 존재하지_않는_크루의_출석을_수정하면_예외가_발생한다() {
        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime modifiedTime = LocalTime.of(9, 0);

        assertThatThrownBy(() -> attendanceBook.modifyAttendance("없는크루", Map.of(date, modifiedTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorCode.NICKNAME_NOT_FOUND.getMessage());
    }

    @Test
    void 출석_기록이_없는_날짜의_출석을_수정하면_예외가_발생한다() {
        Crew crew = Crew.createByName("쿠키");
        attendanceBook.addNewCrew(crew);

        LocalDate date = LocalDate.of(2024, 12, 6);
        LocalTime modifiedTime = LocalTime.of(9, 0);

        assertThatThrownBy(() -> attendanceBook.modifyAttendance("쿠키", Map.of(date, modifiedTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(ErrorCode.ATTENDANCE_RECORD_NOT_EXISTS_FORMAT.getMessage(), 6));
    }

    @Test
    void 운영_시간_외_시간으로_출석을_수정하면_예외가_발생한다() {
        Crew crew = Crew.createByName("쿠키");
        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime originalTime = LocalTime.of(9, 0);
        LocalTime invalidModifiedTime = LocalTime.of(7, 0);

        crew.addDailyAttendance(Map.of(date, originalTime));
        attendanceBook.addNewCrew(crew);

        assertThatThrownBy(() -> attendanceBook.modifyAttendance("쿠키", Map.of(date, invalidModifiedTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getMessage());
    }
}
