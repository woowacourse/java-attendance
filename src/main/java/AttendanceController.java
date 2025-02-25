public class AttendanceController {

    public static final String TODAY_LOCAL_DATE = "2024-12-16";
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {

        Crews crews = new Crews(CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv"));

        String nickname = inputView.getNickname();
        String localDateTimeToday = inputView.getTodayLocalDateTime();
        crews.addCrewAttendance(nickname, localDateTimeToday);
        outputView.printAttendanceResult(localDateTimeToday);
    }
}
