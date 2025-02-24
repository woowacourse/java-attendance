package view;

import domain.AttendanceStatus;
import java.time.LocalDateTime;
import util.DayOfWeekConverter;
import view.dto.AlertCrewDto;
import view.dto.AlertCrewsDto;
import view.dto.AttendanceLogDto;
import view.dto.ChangeAttendanceLogDto;
import view.dto.CrewAttendancesDto;

public class OutputView {
    public void printAttendancesLog(CrewAttendancesDto crewAttendancesDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("이번 달 %s의 출석 기록입니다. \n\n", crewAttendancesDto.nickName()));

        for (AttendanceLogDto attendanceLogDTO : crewAttendancesDto.attendanceLogDtos()) {
            stringBuilder.append(makeAttendanceLog(attendanceLogDTO));
        }
        stringBuilder.append("\n");
        makeAttendanceStatistics(crewAttendancesDto, stringBuilder);
        System.out.println(stringBuilder);
    }

    private void makeAttendanceStatistics(CrewAttendancesDto crewAttendancesDto, StringBuilder stringBuilder) {
        stringBuilder.append(String.format("출석: %d회 \n", crewAttendancesDto.present()));
        stringBuilder.append(String.format("지각: %d회 \n", crewAttendancesDto.late()));
        stringBuilder.append(String.format("결석: %d회 \n\n", crewAttendancesDto.absent()));
        stringBuilder.append(String.format("%s 대상자입니다.", crewAttendancesDto.alertLevel()));
    }

    public void printAttendanceLog(AttendanceLogDto attendanceLogDto) {
        System.out.println(makeAttendanceLog(attendanceLogDto));
    }

    private String makeAttendanceLog(AttendanceLogDto attendanceLogDto) {
        StringBuilder stringBuilder = new StringBuilder();

        LocalDateTime dateTime = attendanceLogDto.localDateTime();
        String dayOfWeekKorean = DayOfWeekConverter.convertDayOfWeek(dateTime);
        String timeFormat = makeTimeFormat(attendanceLogDto.attendanceStatus(), dateTime);

        stringBuilder.append(
                String.format(("%d월 %02d일 %s %s (%s)"), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(),
                        dayOfWeekKorean, timeFormat, attendanceLogDto.attendanceStatus().getName())
        );

        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void printAlertCrews(AlertCrewsDto alertCrewsDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("제적 위험자 조회 결과\n");
        for (AlertCrewDto alertCrewDTO : alertCrewsDto.alertCrews()) {
            stringBuilder.append(makeAlertCrew(alertCrewDTO));
        }

        System.out.println(stringBuilder);
    }

    private String makeAlertCrew(AlertCrewDto alertCrewDto) {
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)\n", alertCrewDto.nickName(), alertCrewDto.absent(),
                alertCrewDto.late(), alertCrewDto.AlertLevel());
    }

    public void printChangeLog(ChangeAttendanceLogDto changeAttendanceLogDto) {
        LocalDateTime originalTime = changeAttendanceLogDto.originalTime();
        LocalDateTime changeTime = changeAttendanceLogDto.changeTime();

        String originalTimeFormat = makeTimeFormat(changeAttendanceLogDto.originalStatus(), originalTime);
        String changeTimeFormat = makeTimeFormat(changeAttendanceLogDto.changeStatus(), changeTime);

        System.out.printf("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!%n",
                originalTime.getMonthValue(),
                originalTime.getDayOfMonth(),
                DayOfWeekConverter.convertDayOfWeek(originalTime), originalTimeFormat,
                changeAttendanceLogDto.originalStatus().getName(),
                changeTimeFormat,
                changeAttendanceLogDto.changeStatus().getName());
    }

    private String makeTimeFormat(AttendanceStatus status, LocalDateTime time) {
        if (status.equals(AttendanceStatus.ABSENT)) {
            return "--:--";
        }
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    public void printAlreadyCheckedGuide() {
        System.out.println("이미 출석하셨습니다. 출석 수정을 이용해주세요.");
    }

    public void printError(String message) {
        System.out.println("\n[ERROR] " + message);
    }
}
