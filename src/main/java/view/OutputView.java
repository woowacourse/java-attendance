package view;

import domain.Attendance;
import domain.AttendanceDto;
import domain.Crew;
import domain.CrewDto;
import domain.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import util.Converter;

public class OutputView {

    public void printOptionMessage() {
        LocalDate today = LocalDate.now();
        String dayOfWeekName = DayOfWeek.getNameById(today.getDayOfWeek().getValue());
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n" +
                        "1. 출석 확인\n" +
                        "2. 출석 수정\n" +
                        "3. 크루별 출석 기록 확인\n" +
                        "4. 제적 위험자 확인\n" +
                        "Q. 종료\n",
                today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceInformation(AttendanceDto attendanceDto) {
        LocalDate today = LocalDate.now();
        String dayOfWeekName = DayOfWeek.getNameById(today.getDayOfWeek().getValue());
        String attendanceTime = Converter.covertLocalTimeToString(attendanceDto.getAttendanceTime());
        String attendanceStatusName = getAttendanceStatusName(attendanceDto);
        System.out.printf("%d월 %02d일 %s %s (%s)\n", today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName,
                attendanceTime, attendanceStatusName);
    }

    public void printUpdatedAttendanceHistory(AttendanceDto originalAttendanceDto, AttendanceDto editedAttendanceDto) {

        LocalDate date = editedAttendanceDto.getDate();
        String dayOfWeekName = DayOfWeek.getNameById(date.getDayOfWeek().getValue());

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
        CrewDto crewDto = crew.toDto();
        int lateCount = crewDto.getLateCount();
        int absentCount = crewDto.getAbsentCount();

        int attendanceCount = crew.getAttendances().size() - lateCount - absentCount;

        for (Attendance attendance : crew.getAttendances()) {
            AttendanceDto dto = attendance.toDto();
            LocalDate date = dto.getDate();
            String attendanceTime = "--:--";
            String dayOfWeekName = DayOfWeek.getNameById(date.getDayOfWeek().getValue());
            String attendanceStatusName = getAttendanceStatusName(dto);

            if (dto.getAttendanceTime() != null) {
                attendanceTime = Converter.covertLocalTimeToString(dto.getAttendanceTime());
            }

            System.out.printf("%d월 %02d일 %s %s (%s)\n", date.getMonth().getValue(), date.getDayOfMonth(),
                    dayOfWeekName, attendanceTime, attendanceStatusName);
        }

        System.out.println();

        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n\n", absentCount);

        System.out.println(crewDto.getPenaltyStatus().getName() + " 대상자입니다.\n");
    }

    private String getAttendanceStatusName(AttendanceDto attendanceDto) {
        String attendanceStatusName = "출석";
        if (attendanceDto.getAbsent()) {
            attendanceStatusName = "결석";
        }

        if (attendanceDto.getLate()) {
            attendanceStatusName = "지각";
        }
        return attendanceStatusName;
    }

    public void printPenaltyCrews(List<CrewDto> crewDtos) {
        System.out.println("제적 위험자 조회 결과");
        sortCrewDtos(crewDtos);
        for (CrewDto dto : crewDtos) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", dto.getNickName(), dto.getAbsentCount(),
                    dto.getLateCount(),
                    dto.getPenaltyStatus().getName());
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
