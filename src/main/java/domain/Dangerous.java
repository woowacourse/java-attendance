package domain;

public enum Dangerous {

    DISMISSAL("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    GOOD("모범", 0);

    private final String status;
    private final int count;

    Dangerous(String status, int count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }

}
