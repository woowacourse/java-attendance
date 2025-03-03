package view;

import domain.attendance.AttendanceStatus;
import domain.attendance.AttendanceTime;
import domain.attendance.comparator.AttendanceTimesComparator;
import domain.crew.CrewAttendance;
import domain.crew.DisciplinaryStatus;
import domain.crew.comparator.CrewAttendanceComparator;
import domain.holiday.Holiday;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class OutputView {

    public void attendPage(AttendanceTime attendanceTime) {
        System.out.println(buildSingleLog(attendanceTime));
    }

    public void modifyPage(AttendanceTime previous, AttendanceTime modified) {
        LocalDate date = modified.toLocalDate();
        LocalTime modifiedTime = modified.toLocalTime();
        AttendanceStatus previousStatus = null;

        String formattedDate = CustomDateTimeFormatter.dateToString(date);
        String formattedDayOfWeek = CustomDateTimeFormatter.dateToDayOfWeek(date);
        String formattedModifiedTime = CustomDateTimeFormatter.timeToString(modifiedTime);
        String formattedPrevious = "--:--";
        if (previous != null) {
            formattedPrevious = CustomDateTimeFormatter.timeToString(previous.toLocalTime());
            previousStatus = previous.toAttendanceStatus();
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

    public void attendanceLogPage(CrewAttendance crewAttendance, LocalDate today) {
        for (int i = 1; i <= today.getDayOfMonth(); i++) {
            LocalDate date = today.withDayOfMonth(i);

            if (Holiday.isWeekendOrHoliday(date)) {
                continue;
            }
            Optional<AttendanceTime> optionalLog = crewAttendance.readLog(date);
            AttendanceTime log = AttendanceTime.of(date, null);
            if (optionalLog.isPresent()) {
                log = optionalLog.get();
            }
            System.out.println(buildSingleLog(log));
        }
        DisciplinaryStatus status = crewAttendance.getDisciplinaryStatus(today);
        System.out.printf("\n출석: %d회\n지각:%d회\n결석:%d회\n",
                crewAttendance.getAttendanceBeforeDate(today),
                crewAttendance.getLateBeforeDate(today),
                crewAttendance.getAbsenceBeforeDate(today));
        if (status != DisciplinaryStatus.NORMAL) {
            System.out.printf("\n%s 대상자입니다\n\n", getDisciplinaryStatusName(status));
        }
    }

    private String buildSingleLog(AttendanceTime attendanceTime) {
        LocalDate date = attendanceTime.toLocalDate();
        String formattedDate = CustomDateTimeFormatter.dateToString(date);
        String formattedDayOfWeek = CustomDateTimeFormatter.dateToDayOfWeek(date);

        String formattedTime = "--:--";
        String formattedStatus = "결석";

        if (attendanceTime.toLocalTime() != null) {
            formattedTime = CustomDateTimeFormatter.timeToString(attendanceTime.toLocalTime());
            formattedStatus = getAttendanceStatusName(attendanceTime.toAttendanceStatus());
        }

        return String.format("%s %s %s (%s)", formattedDate, formattedDayOfWeek, formattedTime, formattedStatus);
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
