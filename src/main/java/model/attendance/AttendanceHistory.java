package model.attendance;

import common.Campus;
import model.date.December;
import model.exception.DuplicatedAttendanceRegistrationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import model.exception.HolidayAttendanceException;
import model.exception.SystemException;

public class AttendanceHistory {
    private final List<Attendance> attendances;

    public AttendanceHistory() {
        List<Attendance> defaultAttendances = new ArrayList<>();
        for (int date = December.START_DATE; date <= December.END_DATE; date++) {
            try {
                defaultAttendances.add(new Attendance(December.createDecemberDateWith(date)));
            } catch (HolidayAttendanceException ignored) {
            }
        }
        this.attendances = defaultAttendances;
    }

    public Attendance register(LocalDate date, LocalTime time) {
        Attendance oldAttendance = findByDate(date);
        validateFirstRegistration(oldAttendance);
        Attendance newAttendance = new Attendance(date, time);
        this.attendances.remove(oldAttendance);
        this.attendances.add(newAttendance);
        return newAttendance;
    }

    public Attendance modifyFrom(Attendance oldAttendance, LocalTime newTime) {
        if (attendances.contains(oldAttendance)) {
            LocalDate date = oldAttendance.getDate();
            Attendance newAttendance = new Attendance(date, newTime);
            this.attendances.remove(oldAttendance);
            this.attendances.add(newAttendance);
            return newAttendance;
        }
        throw new SystemException();
    }

    public List<Attendance> sliceByDateUntilBefore(LocalDate limitDate) {
        return this.attendances.stream()
                .filter(attendance -> attendance.getDate().isBefore(limitDate))
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();
    }

    public Attendance findByDate(LocalDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDateWith(date))
                .findAny()
                .orElseThrow(() -> new HolidayAttendanceException(date));
    }

    private void validateFirstRegistration(Attendance oldAttendance) {
        if (oldAttendance.getTime().equals(Campus.NONE_ATTENDANCE_TIME)) {
            return;
        }
        throw new DuplicatedAttendanceRegistrationException();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AttendanceHistory targetAttendanceHistory)) {
            return false;
        }
        return attendances.containsAll(targetAttendanceHistory.attendances)
                && targetAttendanceHistory.attendances.containsAll(attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
