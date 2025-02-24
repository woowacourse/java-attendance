package attendance.model.crew;

public enum CrewStatus {

    WARNING("경고"),
    CONSULTATION("면담"),
    EXPULSION("제적"),
    NORMAL("정상");

    private final String name;

    CrewStatus(String name) {
        this.name = name;
    }
}
