package attendance.view.ouput;

import attendance.controller.dto.AttendancePenaltyCrewsDto;
import attendance.controller.dto.AttendanceRecordsDto;
import attendance.controller.dto.AttendanceRecordsDto.AttendanceRecordDto;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import java.util.List;

public class OutputView {

    public void printMessage(final String message) {
        System.out.println(message);
    }

    public void printAttendanceDateTime(
        final AttendanceDateTime attendanceDateTime,
        final AttendanceStatus attendanceStatus
    ) {
        final AttendanceDate attendanceDate = attendanceDateTime.getAttendanceDate();
        final AttendanceTime attendanceTime = attendanceDateTime.getAttendanceTime();

        System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n",
            attendanceDate.getMonth(),
            attendanceDate.getDay(),
            attendanceDate.getAttendanceDayOfWeekDayOfWeek()
                .getTitle(),
            convertTime(attendanceTime.getHour()
                .orElse(null)),
            convertTime(attendanceTime.getMinute()
                .orElse(null)),
            attendanceStatus.getTitle());
    }

    public void printModifyAttendanceDateTime(
        final AttendanceDate attendanceDate,
        final AttendanceTime originalAttendanceTime,
        final AttendanceStatus originalAttendanceStatus,
        final AttendanceTime modifiedAttendanceTime,
        final AttendanceStatus modifiedAttendanceStatus
    ) {
        System.out.printf("%s월 %s일 %s요일 %s:%s (%s) -> %s:%s (%s) 수정 완료!\n",
            attendanceDate.getMonth(),
            attendanceDate.getDay(),
            attendanceDate.getAttendanceDayOfWeekDayOfWeek()
                .getTitle(),
            convertTime(originalAttendanceTime.getHour()
                .orElse(null)),
            convertTime(originalAttendanceTime.getMinute()
                .orElse(null)), originalAttendanceStatus.getTitle(),
            convertTime(modifiedAttendanceTime.getHour()
                .orElse(null)),
            convertTime(modifiedAttendanceTime.getMinute()
                .orElse(null)), modifiedAttendanceStatus.getTitle());
    }

    public void printAttendanceRecord(final AttendanceRecordsDto attendanceRecordsDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n",
            attendanceRecordsDto.crew()
                .getNickname());
        printAttendanceRecordDtos(attendanceRecordsDto.attendanceRecords());
        printAttendanceStatus(
            attendanceRecordsDto.attendanceCount(),
            attendanceRecordsDto.lateCount(),
            attendanceRecordsDto.absenceCount());
        printAttendancePenalty(attendanceRecordsDto.attendancePenalty());
    }

    private void printAttendanceRecordDtos(final List<AttendanceRecordDto> attendanceRecordDtos) {
        attendanceRecordDtos.forEach(attendanceRecordDto -> {
            final AttendanceDateTime attendanceDateTime = attendanceRecordDto.attendanceDateTime();
            final AttendanceStatus attendanceStatus = attendanceRecordDto.attendanceStatus();

            final AttendanceDate attendanceDate = attendanceDateTime.getAttendanceDate();
            final AttendanceTime attendanceTime = attendanceDateTime.getAttendanceTime();

            System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n",
                attendanceDate.getMonth(),
                attendanceDate.getDay(),
                attendanceDate.getAttendanceDayOfWeekDayOfWeek()
                    .getTitle(),
                convertTime(attendanceTime.getHour()
                    .orElse(null)),
                convertTime(attendanceTime.getMinute()
                    .orElse(null)),
                attendanceStatus.getTitle());
        });
    }

    private String convertTime(final Integer time) {
        if (time == null) {
            return "--";
        }

        final String rawTime = String.valueOf(time);
        if (rawTime.length() < 2) {
            return "0" + rawTime;
        }

        return rawTime;
    }

    private void printAttendanceStatus(
        final int attendanceCount,
        final int lateCount,
        final int absenceCount
    ) {
        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n", absenceCount);
    }

    private void printAttendancePenalty(final AttendancePenalty attendancePenalty) {
        if (!attendancePenalty.isNoPenalty()) {
            System.out.printf("%s 대상자입니다.\n", attendancePenalty.getTitle());
        }
    }

    public void printAttendancePenaltyCrews(final AttendancePenaltyCrewsDto attendancePenaltyCrewsDto) {
        System.out.println("제적 위험자 조회 결과");
        attendancePenaltyCrewsDto.attendancePenaltyCrewDtos()
            .forEach(attendancePenaltyCrewDto -> {
                System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    attendancePenaltyCrewDto.crew()
                        .getNickname(),
                    attendancePenaltyCrewDto.absenceCount(),
                    attendancePenaltyCrewDto.lateCount(),
                    attendancePenaltyCrewDto.attendancePenalty()
                        .getTitle());
            });
    }
}
