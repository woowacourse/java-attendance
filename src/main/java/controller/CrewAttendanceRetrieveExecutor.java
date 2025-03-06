package controller;

import controller.dto.CrewAttendanceRecord;
import domain.AttendanceManager;
import domain.AttendanceRecords;
import domain.AttendanceStatus;
import domain.AttendanceStatusCalculator;
import domain.AttendanceStatusCounter;
import domain.CrewPenaltyPolicy;
import java.time.LocalDate;
import java.util.Collections;
import util.ApplicationDate;
import view.ConsoleView;

public class CrewAttendanceRetrieveExecutor implements MenuExecutor{

    private final ConsoleView consoleView;
    private final ApplicationDate applicationDate;
    private final AttendanceManager attendanceManager;
    private final AttendanceStatusCalculator attendanceStatusCalculator;

    public CrewAttendanceRetrieveExecutor(
            ConsoleView consoleView,
            ApplicationDate applicationDate,
            AttendanceManager attendanceManager,
            AttendanceStatusCalculator attendanceStatusCalculator
    ) {
        this.consoleView = consoleView;
        this.applicationDate = applicationDate;
        this.attendanceManager = attendanceManager;
        this.attendanceStatusCalculator = attendanceStatusCalculator;
    }

    @Override
    public void execute() {
        String nickName = consoleView.requestNickName();
        LocalDate date = applicationDate.getApplicationDate();

        AttendanceRecords attendanceRecords = retrieveSortedFillAttendances(nickName, date);
        AttendanceStatusCounter attendanceStatusCounter = attendanceStatusCalculator.calculateAllCount(attendanceRecords);
        CrewPenaltyPolicy crewPenaltyPolicy = calculatePenalty(attendanceStatusCounter);

        consoleView.printCrewAttendanceRecord(CrewAttendanceRecord.of(attendanceRecords, attendanceStatusCounter,
                crewPenaltyPolicy));
    }

    private AttendanceRecords retrieveSortedFillAttendances(String nickName, LocalDate date) {
        AttendanceRecords attendanceRecords = attendanceManager.retrieveFilledAttendanceUntilDate(nickName, date);
        Collections.sort(attendanceRecords.attendances());
        return attendanceRecords;
    }

    private CrewPenaltyPolicy calculatePenalty(AttendanceStatusCounter attendanceStatusCounter) {
        int lateCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.LATE);
        int absenceCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.ABSENCE);
        int totalAbsenceCount = CrewPenaltyPolicy.calculateTotalAbsence(lateCount, absenceCount);
        return CrewPenaltyPolicy.judge(totalAbsenceCount);
    }
}
