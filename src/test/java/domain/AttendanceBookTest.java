package domain;

import static org.assertj.core.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.InitialInformation;
import dto.ModifyingResult;
import dto.PenaltyCrewsInformation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    AttendanceBook attendanceBook;
    AttendanceRecord attendanceRecord;

    CrewName mimi = new CrewName("미미");
    CrewName malone = new CrewName("말론");
    CrewName norang = new CrewName("노랑");
    CrewName pree = new CrewName("프리");
    CrewName river = new CrewName("리버");

    LocalDateTime attendanceDayOf2 = LocalDateTime.of(2024, 12, 2, 13, 0);
    LocalDateTime attendanceDayOf3 = LocalDateTime.of(2024, 12, 3, 10, 0);
    LocalDateTime attendanceDayOf4 = LocalDateTime.of(2024, 12, 4, 10, 0);
    LocalDateTime attendanceDayOf5 = LocalDateTime.of(2024, 12, 5, 10, 0);
    LocalDateTime attendanceDayOf6 = LocalDateTime.of(2024, 12, 6, 10, 0);
    LocalDateTime attendanceDayOf9 = LocalDateTime.of(2024, 12, 9, 13, 0);
    LocalDateTime attendanceDayOf10 = LocalDateTime.of(2024, 12, 10, 10, 0);

    LocalDateTime lateDayOf2 = LocalDateTime.of(2024, 12, 2, 13, 15);
    LocalDateTime lateDayOf3 = LocalDateTime.of(2024, 12, 3, 10, 15);
    LocalDateTime lateDayOf4 = LocalDateTime.of(2024, 12, 4, 10, 15);
    LocalDateTime lateDayOf5 = LocalDateTime.of(2024, 12, 5, 10, 15);
    LocalDateTime lateDayOf6 = LocalDateTime.of(2024, 12, 5, 10, 15);
    LocalDateTime lateDayOf9 = LocalDateTime.of(2024, 12, 5, 13, 15);

    @BeforeEach
    void setUp() {
        attendanceRecord = new AttendanceRecord();

        Map<CrewName, AttendanceRecord> testData = new HashMap<>();
        testData.put(mimi, attendanceRecord);

        InitialInformation initialInformation = new InitialInformation(testData);
        attendanceBook = new AttendanceBook(initialInformation);
    }

    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    @Test
    void test1() {
        LocalDateTime todayAttendanceDateTime = LocalDateTime.of(2024, 12, 13, 9, 59);
        Attendance todayAttendance = new Attendance(todayAttendanceDateTime);

        Attendance savedAttendance = attendanceBook.addAttendance(mimi, todayAttendance);

        assertThat(todayAttendance).isEqualTo(savedAttendance);
    }

    @DisplayName("출석 저장 시, 이미 해당 날짜에 출석 기록이 있는 경우 예외가 발생한다.")
    @Test
    void test4() {
        CrewName crewName = new CrewName("미미");
        attendanceRecord.add(new Attendance(attendanceDayOf10));
        LocalDateTime sameDateDifferentTime = LocalDateTime.of(2024, 12, 10, 10, 31);
        Attendance attendance = new Attendance(sameDateDifferentTime);

        assertThatThrownBy(() -> attendanceBook.addAttendance(crewName, attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("닉네임, 수정하려는 날짜, 등교 시간을 입력하여 출석 기록을 수정할 수 있다.")
    @Test
    void test5() {
        attendanceRecord.add(new Attendance(attendanceDayOf10));
        LocalDateTime sameDateDifferentTime = LocalDateTime.of(2024, 12, 10, 10, 31);
        Attendance newAttendance = new Attendance(sameDateDifferentTime);

        attendanceBook.modify(mimi, newAttendance);

        assertThat(attendanceBook.findAttendanceRecordBy(mimi)
                .contains(newAttendance))
                .isTrue();
    }

    @DisplayName("출석 수정 시, 기존 출석 기록과 업데이트된 출석 기록을 모두 확인할 수 있다.")
    @Test
    void test7() {
        Attendance originalAttendance = new Attendance(attendanceDayOf10);
        attendanceRecord.add(originalAttendance);
        LocalDateTime sameDateDifferentTime = LocalDateTime.of(2024, 12, 10, 10, 31);
        Attendance newAttendance = new Attendance(sameDateDifferentTime);

        ModifyingResult modifyingResult = attendanceBook.modify(mimi, newAttendance);

        assertThat(modifyingResult.originalAttendance()).isEqualTo(originalAttendance);
        assertThat(modifyingResult.modifiedAttendance()).isEqualTo(newAttendance);
    }

    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    @Test
    void test8() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                lateDayOf4,
                lateDayOf5,
                attendanceDayOf6,
                attendanceDayOf9,
                attendanceDayOf10
                // 11, 12 => 결석
                // 결석 2, 지각 3, 출석 4
        );
        Attendance firstAttendance = new Attendance(attendanceDayOf2);
        Attendance expectedLastAttendance = new Attendance(LocalDateTime.of(2024, 12, 12, 15, 0));

        AttendanceLog attendanceLog = attendanceBook.findAttendanceLogUntilYesterday(mimi);
        List<Attendance> sortedAttendance = attendanceLog.sortedAttendanceLog();

        assertThat(sortedAttendance.getFirst()).isEqualTo(firstAttendance);
        assertThat(sortedAttendance.getLast()).isEqualTo(expectedLastAttendance);
    }

    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 상태 횟수를 확인할 수 있다.")
    @Test
    void test9() {
        addAttendanceToBook(mimi,
                attendanceDayOf2,
                lateDayOf3,
                lateDayOf4,
                lateDayOf5,
                attendanceDayOf6,
                attendanceDayOf9,
                attendanceDayOf10
                // 11, 12 => 결석
                // 결석 2, 지각 3, 출석 4
        );
        AttendanceCount attendanceCount = attendanceBook.findCountUntilYesterday(mimi);

        assertThat(attendanceCount.absentCount()).isEqualTo(2);
        assertThat(attendanceCount.lateCount()).isEqualTo(3);
        assertThat(attendanceCount.attendCount()).isEqualTo(4);
    }

    @DisplayName("전날까지의 크루 출석 기록을 바탕으로 제적 대상자, 면담 대상자, 경고 대상자순으로 출력한다.")
    @Test
    void test10() {
        setAttendanceBook();

        PenaltyCrewsInformation penaltyCrewsInformation = attendanceBook.findPenaltyCrewsSortedUntilYesterday();
        List<CrewName> crewNames = penaltyCrewsInformation.sortedPenaltyCrewsInformation().stream()
                .map(AttendanceCount::crewName)
                .toList();

        assertThat(crewNames).isEqualTo(List.of(malone, norang, pree, river));
    }

    @DisplayName("제적 위험자는 지각을 결석으로 간주하여 내림차순한다.")
    @Test
    void test11() {
        setAttendanceBook();
        updateConsideredAbsent();

        PenaltyCrewsInformation penaltyCrewsInformation = attendanceBook.findPenaltyCrewsSortedUntilYesterday();
        List<CrewName> crewNames = penaltyCrewsInformation.sortedPenaltyCrewsInformation().stream()
                .map(AttendanceCount::crewName)
                .toList();

        assertThat(crewNames).isEqualTo(List.of(malone, norang, river, pree));
    }

    @DisplayName("제적 위험자는 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.")
    @Test
    void test12() {
        setAttendanceBook();
        updateEqualAbsent();

        PenaltyCrewsInformation penaltyCrewsInformation = attendanceBook.findPenaltyCrewsSortedUntilYesterday();
        List<CrewName> crewNames = penaltyCrewsInformation.sortedPenaltyCrewsInformation().stream()
                .map(AttendanceCount::crewName)
                .toList();

        assertThat(crewNames).isEqualTo(List.of(malone, norang, river, pree));
    }

    void setAttendanceBook() {
        Map<CrewName, AttendanceRecord> testData = new HashMap<>();
        testData.put(malone, new AttendanceRecord());
        testData.put(norang, new AttendanceRecord());
        testData.put(pree, new AttendanceRecord());
        testData.put(river, new AttendanceRecord());

        InitialInformation initialInformation = new InitialInformation(testData);
        attendanceBook = new AttendanceBook(initialInformation);

        makeFirstWarning();
        makeFirstCounseling();
        makeFirstExpulsion();
        makeSecondExpulsion();
    }

    void updateConsideredAbsent() { // 출석1 + 지각6(결석2 간주) + 결석2 => 면담
        updateAttendance(river,
                lateDayOf2,
                lateDayOf3,
                lateDayOf4,
                lateDayOf5,
                lateDayOf6,
                lateDayOf9,
                attendanceDayOf10
        );
    }

    void updateEqualAbsent() { // 지각3(결석1 간주)
        updateAttendance(river,
                lateDayOf5,
                lateDayOf6,
                lateDayOf9);
    }

    void makeFirstExpulsion() { // 출석3 + 결석6 => 제적
        addAttendanceToBook(malone,
                attendanceDayOf2,
                attendanceDayOf3,
                attendanceDayOf10
        );
    }

    void makeSecondExpulsion() { // 출석4 + 결석5 => 제적
        addAttendanceToBook(norang,
                attendanceDayOf2,
                attendanceDayOf3,
                attendanceDayOf4,
                attendanceDayOf10
        );
    }

    void makeFirstCounseling() { // 출석6 + 결석3 => 면담
        addAttendanceToBook(pree,
                attendanceDayOf2,
                attendanceDayOf3,
                attendanceDayOf4,
                attendanceDayOf5,
                attendanceDayOf6,
                attendanceDayOf10
        );
    }

    void makeFirstWarning() { // 출석7 + 결석2 => 경고
        addAttendanceToBook(river,
                attendanceDayOf2,
                attendanceDayOf3,
                attendanceDayOf4,
                attendanceDayOf5,
                attendanceDayOf6,
                attendanceDayOf9,
                attendanceDayOf10
        );
    }

    void addAttendanceToBook(CrewName crewName, LocalDateTime... localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            attendanceBook.addAttendance(crewName, new Attendance(localDateTime));
        }
    }

    void updateAttendance(CrewName crewName, LocalDateTime... localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            attendanceBook.modify(crewName, new Attendance(localDateTime));
        }
    }
}
