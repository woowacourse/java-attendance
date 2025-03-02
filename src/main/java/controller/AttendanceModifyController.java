//package controller;
//
//import domain.domain.Attendance;
//import domain.AttendanceBook;
//import domain.CrewAttendances;
//import view.InputView;
//import view.OutputView;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//public class AttendanceModifyController implements Controller{
//    private final InputView inputView;
//    private final OutputView outputView;
//    private final CrewAttendances crewAttendances;
//
//    public AttendanceModifyController(
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
//        String crewName = inputView.readName();
//        LocalDate modifyDate = inputView.readModifyDate();
//        LocalTime modifyTime = inputView.readModifyTime();
//
//        AttendanceBook attendanceBook = crewAttendances.findAttendanceBookByCrewName(crewName);
//        domain.Attendance beforeAttendance = attendanceBook.findAttendanceByDate(modifyDate);
//        domain.Attendance modifiedAttendance = attendanceBook.replace(modifyDate, modifyTime);
//
//        LocalTime beforeTime = beforeAttendance.getTime().orElse(null);
//        String beforeStatus = beforeAttendance.getStatus().getExpression();
//        String modifiedStatus = modifiedAttendance.getStatus().getExpression();
//        LocalTime modifiedTime = modifiedAttendance.getTime().orElse(null);
//        outputView.printModifyResult(modifyDate, beforeTime, beforeStatus, modifiedTime, modifiedStatus);
//    }
//}
