package view;

import domain.*;
import domain.constant.StandardDate;
import util.Converter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public void printOptionMessage(LocalDate today) {
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(today.getDayOfWeek());
        System.out.printf("""
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceInformation(AttendanceDto attendanceDto) {
        LocalDate todayDate = StandardDate.TODAY.getDate();
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(todayDate.getDayOfWeek());
        String attendanceTime = Converter.covertLocalTimeToString(attendanceDto.getAttendanceTime());
        String attendanceStatusName = getAttendanceStatusName(attendanceDto);
        System.out.printf("%d월 %02d일 %s %s (%s)\n", todayDate.getMonth().getValue(), todayDate.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
    }

    public void printUpdatedAttendanceHistory(AttendanceDto originalAttendanceDto, AttendanceDto editedAttendanceDto) {
        LocalDate date = editedAttendanceDto.getDate();
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());

        String originalAttendanceTime = Converter.covertLocalTimeToString(originalAttendanceDto.getAttendanceTime());
        String editedAttendanceTime = Converter.covertLocalTimeToString(editedAttendanceDto.getAttendanceTime());
        String originAttendanceStatusName = getAttendanceStatusName(originalAttendanceDto);
        String editedAttendanceStatusName = getAttendanceStatusName(editedAttendanceDto);

        System.out.printf("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName,
                originalAttendanceTime, originAttendanceStatusName,
                editedAttendanceTime, editedAttendanceStatusName);
    }

    public void printCrewAttendanceHistoryMessage(String nickname) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", nickname);
    }

    public void printAttendanceHistoryWithCrew(Crew crew) {

        printTotalAttendanceHistory(crew);
        System.out.println();
        printCountWithAttendanceStatus(crew.calculateAttendanceCount(), crew.calculateLateCount(), crew.calculateAbsentCount());

        if (crew.getPenaltyStatus() != PenaltyStatus.NONE) {
            System.out.println(crew.getPenaltyStatus().getName() + " 대상자입니다.\n");
        }
    }

    private void printCountWithAttendanceStatus(int attendanceCount, int lateCount, int absentCount) {
        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n\n", absentCount);
    }

    private void printTotalAttendanceHistory(Crew crew) {
        for (Attendance attendance : crew.getAttendances()) {
            printAttendanceHistory(attendance);
        }
    }

    private void printAttendanceHistory(Attendance attendance) {
        LocalDate date = attendance.getDate();
        String attendanceTime = "--:--";
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());
        String attendanceStatusName = getAttendanceStatusName(Converter.convertAttendanceToDto(attendance));

        if (attendance.getAttendanceTime() != null) {
            attendanceTime = Converter.covertLocalTimeToString(attendance.getAttendanceTime());
        }

        System.out.printf("%d월 %02d일 %s %s (%s)\n", date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
    }

    private String getAttendanceStatusName(AttendanceDto attendanceDto) {
        if (attendanceDto.getAbsent()) {
            return "결석";
        }

        if (attendanceDto.getLate()) {
            return "지각";
        }

        return "출석";
    }

    public void printPenaltyCrews(List<CrewDto> penaltyCrewDtos) {
        System.out.println("제적 위험자 조회 결과");

        sortCrewDtos(penaltyCrewDtos);
        for (CrewDto dto : penaltyCrewDtos) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", dto.getNickName(), dto.getAbsentCount(), dto.getLateCount(), dto.getPenaltyStatus().getName());
        }
        System.out.println();

    }

    private void sortCrewDtos(List<CrewDto> crewDtos) {
        crewDtos.sort(
                Comparator.comparing((CrewDto dto) -> dto.getPenaltyStatus().getThreshold(), Comparator.reverseOrder())
                        .thenComparing(dto -> dto.getLateCount() + dto.getAbsentCount(), Comparator.reverseOrder())
                        .thenComparing(CrewDto::getNickName));
    }
}
