//package controller;
//
//import domain.domain.Attendance;
//import domain.AttendanceCustomDate;
//import domain.CrewAttendances;
//import domain.Month;
//import exception.CrewNotExistException;
//import exception.DuplicateAttendanceException;
//import view.InputView;
//import view.OutputView;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Locale;
//
//public class AttendanceCheckController implements Controller {
//    private final InputView inputView;
//    private final OutputView outputView;
//    private final CrewAttendances crewAttendances;
//
//    public AttendanceCheckController(
//            InputView inputView,
//            OutputView outputView,
//            CrewAttendances crewAttendances
//    ) {
//        this.inputView = inputView;
//        this.outputView = outputView;
//        this.crewAttendances = crewAttendances;
//    }
//
//    @Override
//    public void run() {
//        LocalDate now = AttendanceCustomDate.now().toLocalDate();
//        validateDate(now);
//        String name = inputView.readName();
//        String timeInput = inputView.readTime();
//        String[] minuteAndHour = timeInput.split(":");
//        LocalDateTime dateTime = LocalDateTime.of(
//                now.getYear(),
//                now.getMonthValue(),
//                now.getDayOfMonth(),
//                Integer.parseInt(minuteAndHour[0]),
//                Integer.parseInt(minuteAndHour[1])
//        );
//        registerAttendance(name, dateTime.toLocalDate(), dateTime.toLocalTime());
//    }
//
//    private void validateDate(LocalDate date) {
//        Month month = Month.of(date.getMonthValue());
//        final int day = date.getDayOfMonth();
//        if (month.isHoliday(day)) {
//            String formattedDate = date.format(
//                    DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
//            );
//            throw new IllegalArgumentException(formattedDate + "은 등교일이 아닙니다.");
//        }
//    }
//
//    private void registerAttendance(String name, LocalDate date, LocalTime time) {
//        try {
//            domain.Attendance attendance = crewAttendances.createNewAttendance(name, date, time);
//            String status = attendance.getStatus().getExpression();
//            outputView.printAttendanceResult(attendance.getDate(), attendance.getTime().get(), status);
//        } catch (DuplicateAttendanceException e) {
//            outputView.recommendModifyFunction(e.getMessage());
//        } catch (CrewNotExistException e) {
//            outputView.printExceptionMessage(e.getMessage());
//        }
//    }
//}
