package model;

public enum MenuOption {

    ATTENDANCE_CHECK(1),
    ATTENDANCE_MODIFY(2),
    STUDENT_RECORD_CHECK(3),
    DISMISSAL_SUBJECT_CHECK(4),
    QUICK("Q");

    private final String option;

    MenuOption(int option) {
        this.option = String.valueOf(option);
    }

    MenuOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public int getNumberOption() {
        return Integer.parseInt(option);
    }
}
