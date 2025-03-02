package domain;

public enum DismissStatus {

    WARNING("경고"),
    NEED_MEETING("면담"),
    DISMISS("제적"),
    ELSE("");

    DismissStatus(String status) {
        this.status = status;
    }

    public String status;
}
