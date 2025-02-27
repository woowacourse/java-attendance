package view;

import static domain.AttendanceStatus.ABSENT;

import java.time.LocalDateTime;
import util.DayOfWeekConverter;
import view.dto.AlertCrewDTO;
import view.dto.AlertCrewsDTO;
import view.dto.AttendanceLogDTO;
import view.dto.ChangeAttendanceLogDTO;
import view.dto.CrewAttendancesDTO;

public class OutputView {

    private static final String ABSENT_TIME = "--:--";

    public void printAttendancesLog(CrewAttendancesDTO crewAttendancesDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("이번 달 %s의 출석 기록입니다. \n\n", crewAttendancesDTO.nickName()));

        for (AttendanceLogDTO attendanceLogDTO : crewAttendancesDTO.attendanceLogDTOs()) {
            stringBuilder.append(makeAttendanceLog(attendanceLogDTO));
        }

        stringBuilder.append("\n");
        makeAttendanceStatistics(crewAttendancesDTO, stringBuilder);
        System.out.println(stringBuilder);
    }

    private void makeAttendanceStatistics(CrewAttendancesDTO crewAttendancesDTO, StringBuilder stringBuilder) {
        stringBuilder.append(String.format("출석: %d회 \n", crewAttendancesDTO.present()));
        stringBuilder.append(String.format("지각: %d회 \n", crewAttendancesDTO.late()));
        stringBuilder.append(String.format("결석: %d회 \n\n", crewAttendancesDTO.absent()));
        stringBuilder.append(String.format("%s 대상자입니다.", crewAttendancesDTO.alertLevel()));
    }

    public void printAttendanceLog(AttendanceLogDTO attendanceLogDTO) {
        System.out.println(makeAttendanceLog(attendanceLogDTO));
    }

    private String makeAttendanceLog(AttendanceLogDTO attendanceLogDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime dateTime = attendanceLogDTO.localDateTime();
        String time = String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
        if (attendanceLogDTO.attendanceStatus().equals(ABSENT)) {
            time = ABSENT_TIME;
        }

        String dayOfWeekKorean = DayOfWeekConverter.convertDayOfWeek(dateTime);
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

    public void printChangeLog(ChangeAttendanceLogDTO changeAttendanceLogDTO) {
        LocalDateTime originalTime = changeAttendanceLogDTO.originalTime();
        LocalDateTime changeTime = changeAttendanceLogDTO.changeTime();

        String dayOfWeekKorean = DayOfWeekConverter.convertDayOfWeek(originalTime);
        String oldTime = String.format("%02d:%02d", originalTime.getHour(), originalTime.getMinute());
        String currentTime = String.format("%02d:%02d", changeTime.getHour(), changeTime.getMinute());

        if (changeAttendanceLogDTO.originalStatus().equals(ABSENT)) {
            oldTime = ABSENT_TIME;
        }
        if (changeAttendanceLogDTO.changeStatus().equals(ABSENT)) {
            currentTime = ABSENT_TIME;
        }

        System.out.printf("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!%n",
                originalTime.getMonth().getValue(),
                originalTime.getDayOfMonth(),
                dayOfWeekKorean, oldTime, changeAttendanceLogDTO.originalStatus().getName(), currentTime,
                changeAttendanceLogDTO.changeStatus().getName());

    }

    public void printGuide() {
        System.out.println("이미 출석하셨습니다. 출석 수정을 이용해주세요.");
    }

    public void printError(String message) {
        System.out.println("\n[ERROR] " + message);
    }
}
