package view;

import domain.AttendanceStatus;
import domain.DisciplinaryStatus;
import domain.crew.Crew;
import domain.crew.Nickname;
import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import domain.record.AttendanceRecords;
import domain.record.AttendanceStatusCounts;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import util.DateUtil;

public final class OutputView {

    private static final ResourceBundle RESOURCE_STATUS = ResourceBundle.getBundle("attendanceStatus");
    private static final ResourceBundle RESOURCE_DISCIPLINARY_STATUS = ResourceBundle.getBundle("disciplinaryStatus");

    private OutputView() {
    }

    public static void printAttendanceCheck(final LocalDateTime time, final String attendanceStatus) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm", Locale.KOREAN);

        print("%n %s (%s)%n", time.format(formatter), RESOURCE_STATUS.getString(attendanceStatus));
    }

    public static void printEditAttendanceDateTime(final AttendanceRecord beforeRecord,
                                                   final AttendanceRecord afterRecord) {
        final AttendanceDateTime beforeAttendanceDateTime = beforeRecord.getAttendanceDateTime();
        final LocalDateTime beforeDateTime = beforeAttendanceDateTime.getDateTime();
        final AttendanceDateTime afterAttendanceDateTime = afterRecord.getAttendanceDateTime();
        final LocalDateTime afterDateTime = afterAttendanceDateTime.getDateTime();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm", Locale.KOREAN);

        print("%n %s (%s) -> %s (%s) 수정 완료!%n",
                beforeDateTime.format(formatter),
                RESOURCE_STATUS.getString(beforeRecord.getAttendanceStatus().name()),
                afterDateTime.toLocalTime(),
                RESOURCE_STATUS.getString(afterRecord.getAttendanceStatus().name())
        );
    }

    public static void printValidAttendances(final LocalDateTime localDateTime,
                                             final AttendanceRecords attendanceRecords,
                                             final DisciplinaryStatus disciplinaryStatus) {
        final int endOfDay = localDateTime.getDayOfMonth() - 1;
        final int year = localDateTime.getYear();
        final int month = localDateTime.getMonthValue();
        final List<Integer> validDays = DateUtil.calculateValidDays(year, month, endOfDay);

        validDays.forEach(day -> {
            final AttendanceRecord attendanceRecord = attendanceRecords.findByDate(LocalDate.of(year, month, day));

            printAttendanceInRecord(attendanceRecord);
        });
        printAttendanceCounts(attendanceRecords);

        if (disciplinaryStatus != DisciplinaryStatus.NONE) {
            print("%n%s 대상자입니다.%n", RESOURCE_DISCIPLINARY_STATUS.getString(disciplinaryStatus.name()));
        }
    }

    private static void printAttendanceInRecord(final AttendanceRecord attendanceRecord) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm", Locale.KOREAN);
        final DateTimeFormatter absenceFormat = DateTimeFormatter.ofPattern("M월 dd일 EEEE", Locale.KOREAN);
        final AttendanceDateTime attendanceDateTime = attendanceRecord.getAttendanceDateTime();
        final LocalDateTime dateTime = attendanceDateTime.getDateTime();
        final AttendanceStatus attendanceStatus = attendanceRecord.getAttendanceStatus();

        if (dateTime.toLocalTime().equals(LocalTime.of(0, 0))) {
            print("%s --:-- (%s)%n", dateTime.format(absenceFormat),
                    RESOURCE_STATUS.getString(attendanceStatus.name()));
            return;
        }

        print("%s (%s)%n", dateTime.format(formatter), RESOURCE_STATUS.getString(attendanceStatus.name()));
    }

    private static void printAttendanceCounts(final AttendanceRecords attendanceRecords) {
        final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();
        final int present = attendanceStatusCounts.getAttendance();
        final int late = attendanceStatusCounts.getLate();
        final int absent = attendanceStatusCounts.getAbsence();
        print("%n%s: %d회%n", RESOURCE_STATUS.getString(AttendanceStatus.PRESENT.name()), present);
        print("%s: %d회%n", RESOURCE_STATUS.getString(AttendanceStatus.LATE.name()), late);
        print("%s: %d회%n", RESOURCE_STATUS.getString(AttendanceStatus.ABSENT.name()), absent);
    }

    public static void printRiskMembers(final List<Crew> disciplinaryCrews) {
        print("%n제적 위험자 조회 결과%n");
        disciplinaryCrews.forEach(crew -> {
            final DisciplinaryStatus disciplinaryStatus = crew.getDisciplinaryStatus();
            final String status = RESOURCE_DISCIPLINARY_STATUS.getString(disciplinaryStatus.name());
            final Nickname nickname = crew.getNickname();
            final AttendanceRecords attendanceRecords = crew.getAttendanceRecords();
            final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();
            final int absence = attendanceStatusCounts.getAbsence();
            final int late = attendanceStatusCounts.getLate();

            print("- %s: 결석 %d회, 지각 %d회 (%s)%n", nickname.getValue(), absence, late, status);
        });
    }

    private static void print(final String message, final Object... args) {
        System.out.printf(message, args);
    }
}
