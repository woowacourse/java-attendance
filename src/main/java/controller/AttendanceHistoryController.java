//package controller;
//
//import domain.*;
//import view.InputView;
//import view.OutputView;
//
//import java.time.LocalDate;
//import java.util.Map;
//
//public class AttendanceHistoryController implements Controller {
//    private final InputView inputView;
//    private final OutputView outputView;
//    private final CrewAttendances crewAttendances;
//
//    public AttendanceHistoryController(
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
//        String name = inputView.readName();
//        LocalDate nowDate = AttendanceCustomDate.now().toLocalDate();
//        Map<LocalDate, domain.Attendance> histories = crewAttendances.getAttendances(name, nowDate.withDayOfMonth(1), nowDate);
//        domain.AttendanceStatistic statistic = crewAttendances
//                .getAttendanceStatistic(name, nowDate.withDayOfMonth(1), nowDate);
//        CrewStatus crewStatus = statistic.getCrewStatus();
//        outputView.printHistoryResult(name, histories, statistic.getValue(), crewStatus);
//    }
//}
