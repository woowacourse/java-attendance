package domain;

import java.time.LocalDate;

public class Crew {

    private final String nickName;
    private final Attendances attendances;

    public Crew(String nickName) {
        this.nickName = nickName;
        this.attendances = new Attendances();
    }

    public Crew(Crew crew) {
        this.nickName = crew.nickName;
        this.attendances = new Attendances(crew.attendances);
    }

    public String getNickName() {
        return nickName;
    }

    public Attendances getAttendances() {
        return new Attendances(attendances);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Boolean isEqualTo(String nickname) {
        return this.nickName.equals(nickname);
    }

    public Boolean isAlreadyAttend(LocalDate date) {
        return attendances.isAlreadyAttended(date);
    }

    public void recordAbsence() {
        attendances.recordAbsence();
    }

    public Attendance findByDate(Integer dayOfMonth) {
        return attendances.findByDate(dayOfMonth);
    }

    public Integer getAbsentCount() {
        return attendances.getAbsentCount();
    }

    public Integer getLateCount() {
        return attendances.getLateCount();
    }

    public PenaltyStatus getPenaltyStatus() {
        return attendances.getPenaltyStatus();
    }


}
