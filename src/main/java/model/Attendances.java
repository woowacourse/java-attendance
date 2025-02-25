package model;

import common.Common;
import exception.DuplicatedAttendanceRegistrationException;
import exception.FutureAttendanceModifyException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
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
            validateFutureModification(date);
            Attendance newAttendance = new Attendance(date, newTime);
            this.attendances.remove(oldAttendance);
            this.attendances.add(newAttendance);
            return newAttendance;
        }
        throw new RuntimeException("수정을 요청한 출석 객체를 찾을 수 없습니다.");
    }

    private void validateFutureModification(LocalDate date) {
        if (date.isAfter(DateGenerator.now())) {
            throw new FutureAttendanceModifyException();
        }
    }

    private void validateFirstRegistration(Attendance oldAttendance) {
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
