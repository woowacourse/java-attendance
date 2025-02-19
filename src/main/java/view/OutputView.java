package view;

import domain.AttendanceStatus;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import view.dto.AlertCrewDTO;
import view.dto.AlertCrewsDTO;
import view.dto.AttendanceLogDTO;
import view.dto.CrewAttendancesDTO;

public class OutputView {
    public void printAttendancesLog(CrewAttendancesDTO crewAttendancesDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("이번 달 %s의 출석 기록입니다. \n", crewAttendancesDTO.nickName()));
        stringBuilder.append("\n");

        for (AttendanceLogDTO attendanceLogDTO : crewAttendancesDTO.attendanceLogDTOs()) {
            stringBuilder.append(makeAttendanceLog(attendanceLogDTO));
        }

        stringBuilder.append("\n");

        stringBuilder.append(String.format("출석: %d회", crewAttendancesDTO.present()));
        stringBuilder.append("\n");
        stringBuilder.append(String.format("지각: %d회", crewAttendancesDTO.late()));
        stringBuilder.append("\n");
        stringBuilder.append(String.format("결석: %d회", crewAttendancesDTO.absent()));

        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%s 대상자입니다.", crewAttendancesDTO.alertLevel()));

        System.out.println(stringBuilder);
    }

    public void printAttendanceLog(AttendanceLogDTO attendanceLogDTO) {
        System.out.println(makeAttendanceLog(attendanceLogDTO));
    }

    private String makeAttendanceLog(AttendanceLogDTO attendanceLogDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime dateTime = attendanceLogDTO.localDateTime();
        String time = String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
        if (attendanceLogDTO.attendanceStatus().equals(AttendanceStatus.ABSENT)) {
            time = "--:--";
        }

        String dayOfWeekKorean = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        stringBuilder.append(
                String.format(("%d월 %02d일 %s %s (%s)"), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(),
                        dayOfWeekKorean, time, attendanceLogDTO.attendanceStatus().getName())
        );
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void printAlertCrews(AlertCrewsDTO alertCrewsDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("제적 위험자 조회 결과\n");
        for (AlertCrewDTO alertCrewDTO : alertCrewsDTO.alertCrews()) {
            stringBuilder.append(makeAlertCrew(alertCrewDTO));
        }

        System.out.println(stringBuilder);
    }

    private String makeAlertCrew(AlertCrewDTO alertCrewDTO) {
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)\n", alertCrewDTO.nickName(), alertCrewDTO.absent(),
                alertCrewDTO.late(), alertCrewDTO.AlertLevel());
    }
}
