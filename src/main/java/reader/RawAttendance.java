package reader;

public record RawAttendance(
        String data
) {
    public static RawAttendance from(String data) {
        return new RawAttendance(data);
    }

    public String[] splitByDelimiter(String delimiter) {
        return data.split(delimiter);
    }
}
