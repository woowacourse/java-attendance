package attendance.controller;

import attendance.domain.*;
import attendance.domain.AttendanceStatusChecker.AttendanceStatus;
import attendance.dto.CheckExpulsionResultDto;
import attendance.view.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceController {

    public static final String ATTENDANCE_FILE_PATH = "src/main/resources/";
    public static final String ATTENDANCE_FILE_NAME = "attendances.csv";
    private final GeneralView generalView;
    private final AttendanceConfirmView attendanceConfirmView;
    private final AttendanceModifyView attendanceModifyView;
    private final CrewAttendanceCheckView crewAttendanceCheckView;
    private final CheckAllExpulsionCrewView checkAllExpulsionCrewView;

    public AttendanceController(final GeneralView generalView,
                                final AttendanceConfirmView attendanceConfirmView,
                                final AttendanceModifyView attendanceModifyView,
                                final CrewAttendanceCheckView crewAttendanceCheckView,
                                final CheckAllExpulsionCrewView checkAllExpulsionCrewView) {
        this.generalView = generalView;
        this.attendanceConfirmView = attendanceConfirmView;
        this.attendanceModifyView = attendanceModifyView;
        this.crewAttendanceCheckView = crewAttendanceCheckView;
        this.checkAllExpulsionCrewView = checkAllExpulsionCrewView;
    }

    public void run() {
        AttendanceBook attendanceBook = initializeAttendanceBook();
        while(true) {
            try {
                FeatureCommand featureCommand = generalView.readCommandWithToday(LocalDate.now());
                branchByFeatureCommand(featureCommand, attendanceBook);
            } catch (IllegalArgumentException exception) {
                generalView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private AttendanceBook initializeAttendanceBook() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> crewAttendanceTexts = fileLineReader.readAllLines(ATTENDANCE_FILE_PATH, ATTENDANCE_FILE_NAME);
        crewAttendanceTexts.removeFirst();
        AttendanceBookInitializer attendanceBookInitializer = new AttendanceBookInitializer();
        Map<Crew, List<AttendanceDateTime>> crewAttendances = attendanceBookInitializer.parseTexts(crewAttendanceTexts);
        return new AttendanceBook(crewAttendances);
    }

    private void branchByFeatureCommand(final FeatureCommand featureCommand, final AttendanceBook attendanceBook) {
        if (featureCommand.equals(FeatureCommand.ATTENDANCE_CONFIRMATION)) {
            confirmAttendance(attendanceBook);
        }
        if (featureCommand.equals(FeatureCommand.ATTENDANCE_MODIFICATION)) {
            modifyAttendance(attendanceBook);
        }
        if (featureCommand.equals(FeatureCommand.CREW_ATTENDANCE_CHECK)) {
            checkCrewAttendance(attendanceBook);
        }
        if (featureCommand.equals(FeatureCommand.EXPULSION_CREW_CHECK)) {
            checkAllExpulsionCrews(attendanceBook);
        }
        if (featureCommand.equals(FeatureCommand.QUIT)) {
            System.exit(0);
        }
    }

    private void confirmAttendance(final AttendanceBook attendanceBook) {
        Crew crew = getCrewIfExistInAttendanceBook(attendanceConfirmView.readCrewNickname(), attendanceBook);
        LocalDateTime dateTime = attendanceConfirmView.readAttendanceTime();
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(dateTime);
        attendanceBook.validateDuplicateAttendanceDate(crew, attendanceDateTime);
        attendanceBook.saveAttendanceDateTime(crew, attendanceDateTime);
        AttendanceStatus attendanceStatus = AttendanceStatusChecker.checkStatus(attendanceDateTime);
        attendanceConfirmView.printAttendanceResult(attendanceDateTime, attendanceStatus);
    }

    private Crew getCrewIfExistInAttendanceBook(final String nickname, final AttendanceBook attendanceBook) {
        Crew crew = new Crew(nickname);
        attendanceBook.validateRegisteredCrew(crew);
        return crew;
    }

    private void modifyAttendance(final AttendanceBook attendanceBook) {
        Crew crew = getCrewIfExistInAttendanceBook(attendanceModifyView.readCrewNickname(), attendanceBook);
        int dayToModify = attendanceModifyView.readDayToModify();
        AttendanceDateTime originalDateTime = attendanceBook.findAttendanceDateTimeByCrewAndDay(crew, dayToModify);
        attendanceBook.removeAttendanceDateTime(crew, originalDateTime);
        LocalTime newTime = attendanceModifyView.readTimeToModify();
        AttendanceDateTime newDateTime = originalDateTime.changeTime(newTime);
        attendanceBook.saveAttendanceDateTime(crew, newDateTime);
        AttendanceStatus originalAttendanceStatus = AttendanceStatusChecker.checkStatus(originalDateTime);
        AttendanceStatus newAttendanceStatus = AttendanceStatusChecker.checkStatus(newDateTime);
        attendanceModifyView.printAttendanceModifyResult(originalDateTime, originalAttendanceStatus, newDateTime, newAttendanceStatus);
    }

    private void checkCrewAttendance(final AttendanceBook attendanceBook) {
        Crew crew = getCrewIfExistInAttendanceBook(crewAttendanceCheckView.readCrewNickname(), attendanceBook);
        List<AttendanceDateTime> crewAttendanceDateTimes = findCrewAttendancesThisMonth(attendanceBook, crew);
        crewAttendanceCheckView.printCrewAttendances(crew, crewAttendanceDateTimes);
        Map<AttendanceStatus, Long> attendanceStatuses = AttendanceStatusChecker.checkStatuses(crewAttendanceDateTimes);
        crewAttendanceCheckView.printAttendanceStatuses(attendanceStatuses);
        ExpulsionStatus expulsionStatus = ExpulsionStatus.from(AttendanceStatusChecker.calculateAllAbsent(crewAttendanceDateTimes));
        crewAttendanceCheckView.printExpulsionStatus(expulsionStatus);
    }

    private List<AttendanceDateTime> findCrewAttendancesThisMonth(final AttendanceBook attendanceBook, final Crew crew) {
        LocalDate today = LocalDate.now();
        List<AttendanceDateTime> crewAttendanceDateTimes = new ArrayList<>();
        for (int day=1; day<=today.getDayOfMonth()-1; day++) {
            try {
                crewAttendanceDateTimes.add(attendanceBook.findAttendanceDateTimeByCrewAndDay(crew, day));
            } catch (IllegalArgumentException exception) {
                AttendanceDateTime absentDateTime = AttendanceDateTime.createAbsentDateTime(today.withDayOfMonth(day));
                LocalDateTime absendLocalDateTime = absentDateTime.getLocalDateTime();
                if (AttendanceDateTime.isWeekend(absendLocalDateTime) || Holiday.isHoliday(absendLocalDateTime)) {
                    continue;
                }
                crewAttendanceDateTimes.add(absentDateTime);
            }
        }
        return crewAttendanceDateTimes;
    }

    private void checkAllExpulsionCrews(final AttendanceBook attendanceBook) {
        checkAllExpulsionCrewView.printTitle();
        List<CheckExpulsionResultDto> expulsionResults = new ArrayList<>();
        Set<Crew> allCrews = attendanceBook.getAllCrews();
        for (Crew crew : allCrews) {
            List<AttendanceDateTime> attendancesThisMonth = findCrewAttendancesThisMonth(attendanceBook, crew);
            Map<AttendanceStatus, Long> attendanceStatuses = AttendanceStatusChecker.checkStatuses(attendancesThisMonth);
            ExpulsionStatus expulsionStatus = ExpulsionStatus.from(AttendanceStatusChecker.calculateAllAbsent(attendancesThisMonth));
            if (!expulsionStatus.equals(ExpulsionStatus.NONE)) {
                expulsionResults.add(new CheckExpulsionResultDto(crew.getNickname(), attendanceStatuses.get(AttendanceStatus.ABSENT),
                        attendanceStatuses.get(AttendanceStatus.LATE), expulsionStatus));
            }
        }
        checkAllExpulsionCrewView.printCrewExpulsions(expulsionResults);
    }
}
