package model;

import common.Common;
import exception.DuplicatedAttendanceRegistrationException;
import exception.FutureAttendanceModifyException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceHistory {
    private final List<Attendance> attendances;

    public AttendanceHistory() {
        List<Attendance> defaultAttendances = IntStream.range(1, 32)
                .mapToObj(date -> new Attendance(
                        LocalDate.of(2024, 12, date),
                        Common.noneAttendanceTime))
                .collect(Collectors.toList()); //TODO : toList면 불변이 되어 수정 불가능해짐
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
            validateDate(date);
            Attendance newAttendance = new Attendance(date, newTime);
            this.attendances.remove(oldAttendance);
            this.attendances.add(newAttendance);
            return newAttendance;
        }
        throw new RuntimeException("수정을 요청한 출석 객체를 찾을 수 없습니다.");
    }

    private void validateDate(LocalDate date) {
        December.validateHoliday(date);
        if (date.isAfter(DateGenerator.now())) {
            throw new FutureAttendanceModifyException();
        }
    }

    private void validateFirstRegistration(Attendance oldAttendance) {
        //TODO : 날짜 예외 넣기
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

    public List<Attendance> findAll() {
        return Collections.unmodifiableList(this.attendances);
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
