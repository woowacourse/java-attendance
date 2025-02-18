package attendance.model;

public class Crew {

    private final String name;
    private final AttendenceHistory attendenceHistory;

    public Crew(String name) {
        validate(name);
        this.name = name;
        this.attendenceHistory = new AttendenceHistory();
    }

    private void validate(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("크루의 이름은 1자 이상 5자 이하여야 합니다.");
        }
        if(name.length() >= 5) {
            throw new IllegalArgumentException("크루의 이름은 1자 이상 5자 이하여야 합니다.");
        }
    }

    public void addAttendenceDetail(AttendenceDetail attendenceDetail) {
        attendenceHistory.addAttendenceDetail(attendenceDetail);
    }

    public String getName() {
        return name;
    }

    public AttendenceHistory getAttendenceHistory() {
        return attendenceHistory;
    }
}
