package domain;

import java.time.LocalDate;

public class Crew {

    private final String nickName;
    private final Attendances attendances;

    public Crew(String nickName) {
        this.nickName = nickName;
        this.attendances = new Attendances();
    }

    public CrewDto toDto() {
        return new CrewDto(nickName, attendances);
    }

    public void addAttendance(Attendance attendance) {
        attendances.addAttendance(attendance);
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


}
