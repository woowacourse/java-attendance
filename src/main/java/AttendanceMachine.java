import domain.AttendanceBook;
import domain.AttendanceCounts;
import domain.AttendanceInfo;
import domain.AttendanceInfos;
import domain.AttendanceOperation;
import domain.AttendanceRiskLevel;
import domain.CampusDate;
import domain.CampusTime;
import domain.Crew;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Supplier;
import util.FileReader;
import view.InputView;
import view.OutputView;

public class AttendanceMachine {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceMachine(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start(LocalDate now) throws IOException {
        AttendanceBook attendanceBook = AttendanceBook.createBookByAttendances(
                FileReader.fileReadLine("attendances.csv"));

        Map<AttendanceOperation, Runnable> operationHandlers = Map.of(
                AttendanceOperation.CHECK_IN, () -> handleCheckIn(now, attendanceBook),
                AttendanceOperation.MODIFY, () -> handleModify(now, attendanceBook),
                AttendanceOperation.HISTORY, () -> handleHistory(now, attendanceBook),
                AttendanceOperation.RISK_CREW, () -> handleRiskCrew(now, attendanceBook),
                AttendanceOperation.QUIT, () -> {
                }
        );

        while (true) {
            AttendanceOperation operation = AttendanceOperation.from(inputView.readOperationChoose(now));
            if (operation == AttendanceOperation.QUIT) {
                break;
            }
            operationHandlers.getOrDefault(operation, () -> {
            }).run();
        }
    }

    private void handleCheckIn(LocalDate now, AttendanceBook attendanceBook) {
        Crew crew = retryUntilValidInput(() -> Crew.fromName(inputView.readCrewName()));
        CampusDate campusDate = CampusDate.fromDate(now);
        CampusTime campusTime = retryUntilValidInput(() -> CampusTime.from(inputView.readAttendanceTime()));

        attendanceBook.addInfoWithDateAndTime(crew, campusDate, campusTime);
        outputView.writeAttendanceCheck(AttendanceInfo.fromDateAndTime(campusDate, campusTime));
    }

    private void handleModify(LocalDate now, AttendanceBook attendanceBook) {
        Crew crew = retryUntilValidInput(() -> Crew.fromName(inputView.readModifyCrewName()));
        CampusDate campusDate = retryUntilValidInput(
                () -> CampusDate.ofDateWithDay(now, Integer.parseInt(inputView.readModifyDay())));
        CampusTime campusTime = retryUntilValidInput(() -> CampusTime.from(inputView.readModifyTime()));

        if (attendanceBook.hasInfoByCrewAndDate(crew, campusDate)) {
            AttendanceInfo beforeInfo = attendanceBook.findInfoByCrew(crew).findInfoByDate(campusDate);
            AttendanceInfo afterInfo = attendanceBook.modifyInfoWithDateAndTime(crew, campusDate, campusTime)
                    .findInfoByDate(campusDate);
            outputView.writeModifiedAttendanceCheck(beforeInfo, afterInfo);
            return;
        }
        attendanceBook.addInfoWithDateAndTime(crew, campusDate, campusTime);
        outputView.writeCreatedAttendanceCheck(AttendanceInfo.fromDateAndTime(campusDate, campusTime));
    }

    private void handleHistory(LocalDate now, AttendanceBook attendanceBook) {
        Crew crew = retryUntilValidInput(() -> Crew.fromName(inputView.readCrewName()));
        AttendanceInfos infos = attendanceBook.findInfoByCrew(crew);
        AttendanceCounts counts = infos.countsByDate(now);
        AttendanceRiskLevel riskLevel = counts.calculateAttendanceRiskLevel();
        outputView.writeCrewHistory(now, infos, counts, riskLevel);
    }

    private void handleRiskCrew(LocalDate now, AttendanceBook attendanceBook) {
        AttendanceBook riskCrewBook = attendanceBook.findRiskCrewBook(now);
        outputView.writeRiskCrewHistories(now, riskCrewBook);
    }

    private <T> T retryUntilValidInput(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.writeErrorMessage(e);
            }
        }
    }
}
