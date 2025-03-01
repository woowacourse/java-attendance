package attendance.view;

import attendance.dto.CrewAttendanceDTO;
import attendance.model.AcademicStatus;
import attendance.model.AttendanceStatus;
import attendance.model.AttendanceTime;
import attendance.model.ExpulsionCandidate;
import java.time.format.TextStyle;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printErrorMessage(final String message) {

        System.out.println(message);
    }

    public void printAttendance(final AttendanceTime attendanceTime) {

        final int month = attendanceTime.getDate().getMonthValue();
        final int date = attendanceTime.getDate().getDayOfMonth();
        final String day = attendanceTime.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        final int hour = attendanceTime.getHour();
        final int minute = attendanceTime.getMinute();

        String time = String.format("%02d:%02d", hour, minute);
        if (hour == -1 && minute == -1) {
            time = "--:--";
        }
        final String status = AttendanceStatus.getAttendanceStatus(attendanceTime).getValue();

        System.out.printf("%02d월 %02d일 %s %s (%s)", month, date, day, time, status);
    }

    public void printAfterAttendance(final AttendanceTime attendanceTime) {

        final int hour = attendanceTime.getHour();
        final int minute = attendanceTime.getMinute();
        final String status = AttendanceStatus.getAttendanceStatus(attendanceTime).getValue();
        System.out.printf(" -> %02d:%02d (%s) 수정 완료!\n", hour, minute, status);
    }

    public void printCrewAttendances(final CrewAttendanceDTO crewAttendanceDTO) {

        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", crewAttendanceDTO.name());

        printAttendances(crewAttendanceDTO);
        printLine();

        printCrewAttendanceStatus(crewAttendanceDTO);
        printLine();

        if (crewAttendanceDTO.academicStatus() == AcademicStatus.NOT) {
            return;
        }
        System.out.printf("%s 대상자입니다.\n", crewAttendanceDTO.academicStatus().getValue());
        printLine();
    }

    public void printExpulsionCandidates(List<ExpulsionCandidate> expulsionCandidates) {

        System.out.println("제적 위험자 조회 결과");
        for (ExpulsionCandidate candidate : expulsionCandidates) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    candidate.name(), candidate.absent(), candidate.late(), candidate.status().getValue());
        }
        printLine();
    }

    private void printAttendances(CrewAttendanceDTO crewAttendanceDTO) {

        for (AttendanceTime attendanceTime : crewAttendanceDTO.attendances()) {
            printAttendance(attendanceTime);
            printLine();
        }
    }

    private void printCrewAttendanceStatus(CrewAttendanceDTO crewAttendanceDTO) {

        EnumMap<AttendanceStatus, Integer> statusCounts = crewAttendanceDTO.statusCount();
        for (AttendanceStatus attendanceStatus : statusCounts.keySet()) {
            printAttendanceStatus(attendanceStatus, statusCounts.get(attendanceStatus));
        }
    }

    private void printAttendanceStatus(final AttendanceStatus attendanceStatus, final int count) {

        System.out.printf("%s: %d회", attendanceStatus.getValue(), count);
        printLine();
    }

    public void printLine() {

        System.out.println();
    }
}
