package presentation.view;

import domain.attendance.AttendanceState;
import domain.attendance.AttendanceWarning;
import dto.ResponseAttendanceEditStateDto;
import dto.ResponseAttendanceStateCountDto;
import dto.ResponseCrewAttendanceStateDto;
import dto.ResponseWarningCrewDto;
import java.util.List;

public class OutputView {
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printAttendanceState(String attendanceDate,
                                            String attendanceTime,
                                            AttendanceState attendanceState) {
        System.out.println(attendanceDate + " " + getAttendanceInfo(attendanceTime, attendanceState));
    }

    public static void printEditState(ResponseAttendanceEditStateDto editStateDto) {
        System.out.println(getAttendanceInfo(editStateDto.beforeDateTime(), editStateDto.beforeState()) + " -> "
                + editStateDto.afterDateTime() + getAttendanceStateMessage(editStateDto.afterState()) + " 수정 완료!"
        );
    }

    public static void printAttendanceStatusCrew(ResponseCrewAttendanceStateDto crewAttendanceStateDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewAttendanceStateDto.crewName());
        System.out.println();
        crewAttendanceStateDto.crewAttendanceStateDtos().forEach(
                responseCrewStatusDto -> System.out.println(getAttendanceDateTimeState(
                        responseCrewStatusDto.crewDate(),
                        responseCrewStatusDto.crewTime(),
                        responseCrewStatusDto.attendanceState())));
        System.out.println();
        printAttendanceStatusCount(crewAttendanceStateDto.attendanceStateCountDto());
        System.out.println();
    }

    public static void printAttendanceWarningCrews(List<ResponseWarningCrewDto> warningCrewDtos) {
        System.out.println("제적 위험자 조회 결과");
        warningCrewDtos.forEach(OutputView::printAttendanceWarningCrew);
        System.out.println();
    }

    private static void printAttendanceWarningCrew(ResponseWarningCrewDto warningCrewDto) {
        System.out.println("- "+ warningCrewDto.crewName() + ": 결석 " + warningCrewDto.absenceCount() + "회, 지각 "
                + warningCrewDto.tardyCount() + "회"
                + " (" + (warningCrewDto.attendanceWarning().getStatus()) + ")");
    }

    private static String getAttendanceDateTimeState(String date, String time, AttendanceState state) {
        if (state == AttendanceState.ABSENCE) {
            return date + " " + "--:--" + getAttendanceStateMessage(state);
        }
        return date + " " + time + getAttendanceStateMessage(state);
    }

    private static String getAttendanceInfo(String attendanceTime, AttendanceState attendanceState) {
        return attendanceTime + getAttendanceStateMessage(attendanceState);
    }

    private static String getAttendanceStateMessage(AttendanceState attendanceState) {
        return " (" + attendanceState.getState() + ")";
    }

    private static void printAttendanceStatusCount(ResponseAttendanceStateCountDto responseAttendanceStateCountDto) {
        System.out.printf("출석 : %d회\n", responseAttendanceStateCountDto.attendanceCount());
        System.out.printf("지각 : %d회\n", responseAttendanceStateCountDto.tardyCount());
        System.out.printf("결석 : %d회\n", responseAttendanceStateCountDto.absenceCount());

        if (responseAttendanceStateCountDto.attendanceWarning() != AttendanceWarning.NONE) {
            System.out.printf("\n%s 대상자 입니다.\n", responseAttendanceStateCountDto.attendanceWarning().getStatus());
        }
    }
}
