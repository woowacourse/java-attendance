package attendance.model;

public enum Attendence {
    출석(),
    지각(),
    결석();


    public static Attendence from(int time) {
        if (time > 30) {
            return 결석;
        }
        if (time > 5) {
            return 지각;
        }
        return 출석;
    }
}
