package model;

import common.Common;
import exception.DuplicatedAttendanceRegistrationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Attendance update(LocalDate date, LocalTime time) {
        Attendance oldAttendance = findByDate(date);
        validateFirstUpdate(oldAttendance);
        Attendance newAttendance = new Attendance(date, time);
        this.attendances.remove(oldAttendance);
        this.attendances.add(newAttendance);
        return newAttendance;
    }

    private void validateFirstUpdate(Attendance oldAttendance) {
        if (oldAttendance.getTime().equals(Common.noneAttendanceTime)) {
            return;
        }
        throw new DuplicatedAttendanceRegistrationException();
    }

    public Attendance findByDate(LocalDate date) { //TODO :private
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDateWith(date))
                .findAny()
                .orElseThrow(RuntimeException::new); //TODO : 다른 예외로 교체
    }

    public Attendance modify(LocalDate modifyDate, LocalTime modifyTime) {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Attendances targetAttendances)) {
            return false;
        }
        return attendances.containsAll(targetAttendances.attendances)
                && targetAttendances.attendances.containsAll(attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
