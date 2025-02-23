package constant;

public enum MenuOption {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_CREW_ATTENDANCE_HISTORY("3"),
    CHECK_DANGEROUS_CREW("4"),
    QUIT_REGEX("[Qq]")
    ;

    private final String menu;

    MenuOption(String menu) {
        this.menu = menu;
    }

    public String getValue() {
        return this.menu;
    }
}
