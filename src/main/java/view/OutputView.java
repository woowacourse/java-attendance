package view;

import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.AttendanceTimesComparator;
import domain.CrewAttendance;
import domain.CrewAttendanceComparator;
import domain.DisciplinaryStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class OutputView {
    public void attendPage(AttendanceTime attendanceTime) {
        System.out.println(buildSingleLog(attendanceTime));
    }

    public void modifyPage(Optional<AttendanceTime> previous, AttendanceTime modified) {
        LocalDate date = modified.toLocalDate();
        LocalTime modifiedTime = modified.toLocalTime();
        AttendanceStatus previousStatus = null;

        String formattedDate = CustomDateTimeFormatter.dateToString(date);
        String formattedDayOfWeek = CustomDateTimeFormatter.dateToDayOfWeek(date);
        String formattedModifiedTime = CustomDateTimeFormatter.timeToString(modifiedTime);
        String formattedPrevious = "--:--";
        if (previous.isPresent()) {
            formattedPrevious = CustomDateTimeFormatter.timeToString(previous.get().toLocalTime());
            previousStatus = previous.get().toAttendanceStatus();
        }
        String previousStatusName = getAttendanceStatusName(previousStatus);
        String modifiedStatusName = getAttendanceStatusName(modified.toAttendanceStatus());

        System.out.printf("%s %s %s (%s) -> %s (%s) 수정 완료!\n",
                formattedDate,
                formattedDayOfWeek,
                formattedPrevious,
                previousStatusName,
                formattedModifiedTime,
                modifiedStatusName
        );
    }

    public void attendanceLogPage(List<AttendanceTime> logs) {
        List<AttendanceTime> sortedLogs = logs.stream().sorted().toList();

        for (AttendanceTime attendanceTime : sortedLogs) {
            System.out.println(buildSingleLog(attendanceTime));
        }
    }

    public void disciplinaryCrewsPage(List<CrewAttendance> crews, LocalDate today) {
        List<CrewAttendance> sortedCrews = crews.stream()
                .sorted(
                        new CrewAttendanceComparator(new AttendanceTimesComparator(today)).reversed()
                ).toList();
        for (CrewAttendance crewAttendance : sortedCrews) {
            String nickname = crewAttendance.getCrewNickname();
            int absenceCount = crewAttendance.getAbsenceBeforeDate(today);
            int lateCount = crewAttendance.getLateBeforeDate(today);
            String statusName = getDisciplinaryStatusName(crewAttendance.getDisciplinaryStatus(today));
            System.out.printf("- %s: 결석 %s회, 지각 %s회 (%s)\n",
                    nickname,
                    absenceCount,
                    lateCount,
                    statusName);
        }
    }

    private String buildSingleLog(AttendanceTime attendanceTime) {
        LocalDate date = attendanceTime.toLocalDate();
        LocalTime time = attendanceTime.toLocalTime();
        AttendanceStatus attendanceStatus = attendanceTime.toAttendanceStatus();

        String formattedDate = CustomDateTimeFormatter.dateToString(date);
        String formattedDayOfWeek = CustomDateTimeFormatter.dateToDayOfWeek(date);
        String formattedTime = CustomDateTimeFormatter.timeToString(time);
        String formattedStatus = getAttendanceStatusName(attendanceStatus);

        return String.format("%s %s %s (%ss)",
                formattedDate,
                formattedDayOfWeek,
                formattedTime,
                formattedStatus);
    }

    private String getAttendanceStatusName(AttendanceStatus status) {
        if (status == null || status == AttendanceStatus.ABSENCE) {
            return "결석";
        }
        if (status == AttendanceStatus.LATE) {
            return "지각";
        }
        return "출석";
    }

    private String getDisciplinaryStatusName(DisciplinaryStatus status) {
        if (status == DisciplinaryStatus.DISMISSED) {
            return "제적";
        }
        if (status == DisciplinaryStatus.COUNSELING) {
            return "면담";
        }
        if (status == DisciplinaryStatus.WARNED) {
            return "경고";
        }
        return "정상";
    }
}
