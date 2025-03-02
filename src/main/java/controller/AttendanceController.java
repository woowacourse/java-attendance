package controller;

//public class AttendanceController {
//
//    public static final LocalDateTime REFERENCE_DATE_TIME = LocalDateTime.of(2024, 12, 17, 10, 0);
//
//    private final CrewGenerator crewGenerator;
//
//    public AttendanceController() {
//        this.crewGenerator = new CrewGenerator();
//        crewGenerator.generate();
//    }
//
//    public void run() {
//        final Crews crews = crewGenerator.generate();
//        final Nickname nickname = readNickname();
//        final Crew crew = crews.findByNickname(nickname);
//        final AttendanceTime attendanceTime = readAttendanceTime();
//        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from(attendanceTime);
//
//        crew.attend(attendanceDateTime);
//
//        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime,
//                AttendanceTimePolicy.findByAttendanceDateTime(attendanceDateTime));
//
//        OutputView.printAttendanceCheck(attendanceDateTime.getDateTime(), attendanceStatus.name());
//    }
//
//    private Nickname readNickname() {
//        final String inputNickname = InputView.readNickname();
//        return new Nickname(inputNickname);
//    }
//
//    private AttendanceTime readAttendanceTime() {
//        final String inputDate = InputView.readTime();
//        return AttendanceTime.from(inputDate);
//    }
//}
