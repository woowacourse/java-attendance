package domain.crew;

import domain.DisciplinaryStatus;
import domain.dateTime.AttendanceDate;
import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import domain.record.AttendanceRecords;
import domain.record.AttendanceStatusCounts;

public class Crew implements Comparable<Crew> {

    private final Nickname nickname;
    private final AttendanceRecords attendanceRecords;
    private DisciplinaryStatus disciplinaryStatus;

    public Crew(final Nickname nickname,
                final AttendanceRecords attendanceRecords,
                final DisciplinaryStatus disciplinaryStatus) {
        this.nickname = nickname;
        this.attendanceRecords = attendanceRecords;
        this.disciplinaryStatus = disciplinaryStatus;
    }

    public void attend(final AttendanceDateTime attendanceDateTime) {
        if (attendanceRecords.hasAttendanceDateTime(attendanceDateTime)) {
            throw new IllegalArgumentException("이미 출석을 했습니다. 다시 출석을 할 수 없으며 수정은 원할 시 수정기능을 사용해주세요.");
        }
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceRecords.updateAttendanceRecord(attendanceRecord);
        updateDisciplinaryStatus();
    }

    public void updateDisciplinaryStatus() {
        this.disciplinaryStatus = attendanceRecords.findDisciplinaryStatus();
    }

    public boolean isSameAs(final Nickname nickname) {
        return this.nickname.equals(nickname);
    }

    public DisciplinaryStatus getDisciplinaryStatus() {
        return disciplinaryStatus;
    }

    public AttendanceRecords getAttendanceRecords() {
        return attendanceRecords;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public AttendanceRecord findAttendanceRecordByDate(AttendanceDate attendanceDate) {
        return attendanceRecords.findAttendanceRecord(attendanceDate);
    }

    public AttendanceRecord editAttendanceDateTime(final AttendanceDateTime wantedAttendanceDateTime) {
        final AttendanceRecord beforeRecord = attendanceRecords.findMatchingAttendanceDate(wantedAttendanceDateTime);
        final AttendanceRecord afterRecord = new AttendanceRecord(wantedAttendanceDateTime);

        attendanceRecords.editAttendanceDateTime(beforeRecord, afterRecord);
        attendanceRecords.updateCountAttendanceStatus();
        return afterRecord;
    }

    public int getAbsenceCountInDisciplinaryStatus() {
        return disciplinaryStatus.getAbsenceCount();
    }

    @Override
    public int compareTo(final Crew other) {
        int comparisonResult = compareDisciplinaryStatus(other);
        if (comparisonResult != 0) {
            return comparisonResult;
        }
        comparisonResult = compareAdjustedAbsence(other);
        if (comparisonResult != 0) {
            return comparisonResult;
        }
        return compareNickname(other);
    }

    private int compareDisciplinaryStatus(final Crew other) {
        return Integer.compare(other.getAbsenceCountInDisciplinaryStatus(), this.getAbsenceCountInDisciplinaryStatus());
    }

    private int compareAdjustedAbsence(final Crew other) {
        final int thisAdjusted = getAdjustedAbsence(this.attendanceRecords);
        final int otherAdjusted = getAdjustedAbsence(other.getAttendanceRecords());
        return Integer.compare(otherAdjusted, thisAdjusted);
    }

    private int getAdjustedAbsence(final AttendanceRecords attendanceRecords) {
        final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();
        return attendanceStatusCounts.getAdjustedAbsence();
    }

    private int compareNickname(final Crew other) {
        return this.nickname.compareTo(other.nickname);
    }
}
