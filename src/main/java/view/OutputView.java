package view;

import domain.*;
import domain.constant.StandardDate;
import util.Converter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class OutputView {
    private static final String APPLICATION_START_MESSAGE = """
            오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;
    private static final String UPDATE_COMPLETE_MESSAGE = "%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n";
    private static final String CREW_ATTENDANCE_HISTORY_MESSAGE = "이번 달 %s의 출석 기록입니다.\n\n";
    private static final String ATTENDANCE_HISTORY_WITH_DATE = "%d월 %02d일 %s %s (%s)\n";
    private static final String ATTENDANCE_COUNT = "출석: %d회\n";
    private static final String LATE_COUNT = "지각: %d회\n";
    private static final String ABSENT_COUNT = "결석: %d회\n\n";
    private static final String PENALTY_CREW_READ_RESULT_PREFIX = "제적 위험자 조회 결과";
    private static final String PENALTY_CREW_READ_RESULT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    private static final String ATTENDANCE_STATUS_NAME = "출석";
    private static final String LATE_STATUS_NAME = "지각";
    private static final String ABSENT_STATUS_NAME = "결석";

    public void printOptionMessage(LocalDate today) {
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(today.getDayOfWeek());
        System.out.printf(APPLICATION_START_MESSAGE, today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceInformation(AttendanceDto attendanceDto) {
        LocalDate today = StandardDate.DATE;
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(today.getDayOfWeek());
        String attendanceTime = Converter.covertLocalTimeToString(attendanceDto.getAttendanceTime());
        String attendanceStatusName = getAttendanceStatusName(attendanceDto);
        System.out.printf("%d월 %02d일 %s %s (%s)\n", today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
    }

    public void printUpdatedAttendanceHistory(AttendanceDto originalAttendanceDto, AttendanceDto editedAttendanceDto) {
        LocalDate date = editedAttendanceDto.getDate();
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());

        String originalAttendanceTime = Converter.covertLocalTimeToString(originalAttendanceDto.getAttendanceTime());
        String editedAttendanceTime = Converter.covertLocalTimeToString(editedAttendanceDto.getAttendanceTime());
        String originAttendanceStatusName = getAttendanceStatusName(originalAttendanceDto);
        String editedAttendanceStatusName = getAttendanceStatusName(editedAttendanceDto);

        System.out.printf(UPDATE_COMPLETE_MESSAGE,
                date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName,
                originalAttendanceTime, originAttendanceStatusName,
                editedAttendanceTime, editedAttendanceStatusName);
    }

    public void printCrewAttendanceHistoryMessage(String nickname) {
        System.out.printf(CREW_ATTENDANCE_HISTORY_MESSAGE, nickname);
    }

    public void printAttendanceHistoryWithCrew(Crew crew) {
        CrewDto crewDto = crew.toDto();
        int attendanceCount = crewDto.getAttendanceCount();
        int lateCount = crewDto.getLateCount();
        int absentCount = crewDto.getAbsentCount();

        printTotalAttendanceHistory(crew);
        System.out.println();
        printCountWithAttendanceStatus(attendanceCount, lateCount, absentCount);
        System.out.println(crewDto.getPenaltyStatus().getName() + " 대상자입니다.\n");
    }

    private void printTotalAttendanceHistory(Crew crew) {
        for (Attendance attendance : crew.getAttendances()) {
            AttendanceDto dto = attendance.toDto();
            LocalDate date = dto.getDate();
            String attendanceTime = "--:--";
            String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());
            String attendanceStatusName = getAttendanceStatusName(dto);

            if (dto.getAttendanceTime() != null) {
                attendanceTime = Converter.covertLocalTimeToString(dto.getAttendanceTime());
            }
            System.out.printf(ATTENDANCE_HISTORY_WITH_DATE, date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
        }
    }

    private void printCountWithAttendanceStatus(int attendanceCount, int lateCount, int absentCount) {
        System.out.printf(ATTENDANCE_COUNT, attendanceCount);
        System.out.printf(LATE_COUNT, lateCount);
        System.out.printf(ABSENT_COUNT, absentCount);
    }

    private String getAttendanceStatusName(AttendanceDto attendanceDto) {
        if (attendanceDto.getAbsent()) {
            return ABSENT_STATUS_NAME;
        }

        if (attendanceDto.getLate()) {
            return LATE_STATUS_NAME;
        }

        return ATTENDANCE_STATUS_NAME;
    }

    public void printPenaltyCrews(List<CrewDto> penaltyCrewDtos) {
        System.out.println(PENALTY_CREW_READ_RESULT_PREFIX);

        sortCrewDtos(penaltyCrewDtos);
        for (CrewDto dto : penaltyCrewDtos) {
            System.out.printf(PENALTY_CREW_READ_RESULT, dto.getNickName(), dto.getAbsentCount(), dto.getLateCount(), dto.getPenaltyStatus().getName());
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
