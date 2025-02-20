package presentation.view;

import domain.attendance.AttendanceState;
import domain.attendance.AttendanceWarning;
import dto.ResponseAttendanceEditStateDto;
import dto.ResponseAttendanceStateCountDto;
import dto.ResponseCrewAttendanceStateDto;
import dto.ResponseCrewStatusDto;
import util.DateTimeUtil;

public class OutputView {
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    //12월 13일 금요일 09:59 (출석)
    public static void printAttendanceState(String attendanceTime, AttendanceState attendanceState) {
        System.out.println(getAttendanceInfo(attendanceTime, attendanceState));
    }

    public static void printEditState(ResponseAttendanceEditStateDto editStateDto) {
        System.out.println(getAttendanceInfo(editStateDto.beforeDateTime(), editStateDto.beforeState()) + " -> "
                + editStateDto.afterDateTime() + getAttendanceStateMessage(editStateDto.afterState()) + " 수정 완료!"
        );
    }

    // 크루 별 출석 기록 확인
    // todo: README에 결석일 때는 시간 상관없이 --:--로 출력한다 (수정, 조회 모두)
                /*
                이번 달 빙티의 출석 기록입니다.

                12월 02일 월요일 13:00 (출석)
                12월 03일 화요일 10:07 (지각)
                12월 04일 수요일 10:02 (출석)
                12월 05일 목요일 10:06 (지각)
                12월 06일 금요일 10:01 (출석)
                12월 09일 월요일 --:-- (결석)
                12월 10일 화요일 10:03 (출석)
                12월 11일 수요일 --:-- (결석)
                12월 12일 목요일 --:-- (결석)
                12월 13일 금요일 10:02 (출석)

                출석: 3회
                지각: 0회
                결석: 3회

                면담 대상자입니다.
                 */
    public static void printAttendanceStatusCrew(ResponseCrewAttendanceStateDto crewAttendanceStateDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewAttendanceStateDto.crewName());
        System.out.println();
        crewAttendanceStateDto.crewAttendanceStateDtos().stream().forEach(
                responseCrewStatusDto -> printAttendanceState(responseCrewStatusDto.crewDateTime(),
                        responseCrewStatusDto.attendanceState()));
        System.out.println();
        printAttendanceStatusCount(crewAttendanceStateDto.attendanceStateCountDto());
        System.out.println();
    }

    // hh:mm (출석)
    private static String getAttendanceInfo(String attendanceTime, AttendanceState attendanceState) {
        return attendanceTime + getAttendanceStateMessage(attendanceState);
    }

    // (출석)
    private static String getAttendanceStateMessage(AttendanceState attendanceState) {
        return "(" + attendanceState.getState() + ")";
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
