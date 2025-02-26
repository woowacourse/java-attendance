package domain;

public enum DismissStatus {
    WARNING("경고"),
    NEED_MEETING("면담"),
    DISMISS("제적"),
    ELSE("");

    DismissStatus(String dismissStatus) {
        this.dismissStatus = dismissStatus;
    }

    public String dismissStatus;
}
