public enum Current {
    TODAY(13);

    private final int day;

    Current(final int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getYesterday() {
        return day - 1;
    }
}
