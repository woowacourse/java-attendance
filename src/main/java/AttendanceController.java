import java.time.format.DateTimeFormatter;

public class AttendanceController {

    public static final String TODAY_LOCAL_DATE = "2024-12-16";
    public static final DateTimeFormatter TODAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {


        Crews crews = new Crews(CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv"));

        Command command = inputView.getCommand();
        try {
            if (command.equals(Command.ATTEND_TODAY)) {
                attendToday(crews);
            }
            if(command.equals(Command.SHOW_CREW_ATTENDANCES)){
                String nickname=inputView.getNickname();
                Crew crew= crews.findCrewByNickname(nickname).orElseThrow(()->new IllegalArgumentException("없는 사용자입니다."));

                outputView.printAttendanceTimeLine(crew);

            }
        } catch (Exception e) {
            System.out.println("[ERROR]" + e.getMessage());
        }
    }

    private void attendToday(Crews crews) {
        String nickname = inputView.getNickname();
        String localDateTimeToday = inputView.getTodayLocalDateTime();
        AttendTime attendTime = crews.addCrewAttendance(nickname, localDateTimeToday);
        outputView.printAttendanceResult(localDateTimeToday,attendTime.checkAttendanceStatus());
    }
}
