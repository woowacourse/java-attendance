package constant;

public enum PathConstant {

    ATTENDANCE_FILE_PATH("attendances.csv"),
    ;

    private final String path;

    PathConstant(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
