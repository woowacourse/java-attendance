package model.attendance;

import common.Common;
import model.date.December;
import model.exception.DuplicatedAttendanceRegistrationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import model.exception.FutureAttendanceException;
import model.exception.HolidayAttendanceException;
import model.exception.SystemException;

public class AttendanceHistory {
    private final List<Attendance> attendances;

    public AttendanceHistory() {
        List<Attendance> defaultAttendances = new ArrayList<>(); //TODO : 스트림 불가?
        for (int date = 1; date <= 31; date++) {
            if (December.isHolidayAt(December.createDecemberDateWith(date))) {
                continue;
            }
            defaultAttendances.add(new Attendance(
                    LocalDate.of(2024, 12, date),
                    Common.noneAttendanceTime));
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
            validateModification(date);
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
        December.validateHoliday(date);
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDateWith(date))
                .findAny()
                .orElseThrow(SystemException::new);
    }

    private void validateFirstRegistration(Attendance oldAttendance) {
        if (oldAttendance.getTime().equals(Common.noneAttendanceTime)) {
            return;
        }
        throw new DuplicatedAttendanceRegistrationException();
    }

    private void validateModification(LocalDate date) { //TODO : 날짜 자체의 객체에 들어가는게 더 어울리는데 아쉬움
        if (date.isAfter(December.now())) {
            throw new FutureAttendanceException();
        }
        if (December.isHolidayAt(date)) {
            throw new HolidayAttendanceException(date);
        }
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
