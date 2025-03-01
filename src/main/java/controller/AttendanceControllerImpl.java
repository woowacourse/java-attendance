package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import factory.AttendanceBookFactory;
import io.dto.ExpelWarningCrewResponse;
import io.dto.MenuSelect;
import io.view.InputView;
import io.view.OutputView;
import service.today_provider.TodayProvider;

public class AttendanceControllerImpl implements AttendanceController {
    
    private final Map<MenuSelect, Runnable> handlers = new HashMap<>();
    private final InputView inputView;
    private final TodayProvider todayProvider;
    
    private boolean runFlag = true;
    
    public AttendanceControllerImpl(
            final InputView inputView,
            final OutputView outputView,
            final TodayProvider todayProvider,
            final AttendanceBookFactory attendanceBookFactory
    ) {
        this.inputView = inputView;
        this.todayProvider = todayProvider;
        var attendanceBook = attendanceBookFactory.create();
        registerHandlers(inputView, outputView, todayProvider, attendanceBook);
    }
    
    private void registerHandlers(
            final InputView inputView, final OutputView outputView,
            final TodayProvider todayProvider, final AttendanceBook attendanceBook
    ) {
        handlers.put(MenuSelect.출석_확인, () -> handleAttend(inputView, outputView, todayProvider, attendanceBook));
        handlers.put(MenuSelect.출석_수정, () -> handleAttendanceModify(inputView, outputView, todayProvider, attendanceBook));
        handlers.put(MenuSelect.크루별_출석_기록_확인, () -> handleFindAttendanceRecord(inputView, outputView, attendanceBook));
        handlers.put(MenuSelect.제적_위험자_확인, () -> handleFindExpelWarnings(outputView, attendanceBook));
        handlers.put(MenuSelect.종료, () -> runFlag = false);
    }
    
    private static void handleAttend(
            final InputView inputView, final OutputView outputView,
            final TodayProvider todayProvider, final AttendanceBook attendanceBook
    ) {
        var nickname = inputView.getAttendNickname();
        var time = inputView.getAttendTime();
        var today = todayProvider.today();
        attendanceBook.attend(nickname, today, time);
        
        var attendanceStatus = attendanceBook.getAttendanceStatusOf(nickname, today);
        outputView.outputAttendResult(today, time, attendanceStatus);
    }
    
    private static void handleAttendanceModify(
            final InputView inputView, final OutputView outputView,
            final TodayProvider todayProvider, final AttendanceBook attendanceBook
    ) {
        var nickname = inputView.getModifyNickname();
        var today = todayProvider.today();
        var targetDate = inputView.getModifyDate(today);
        var newTime = inputView.getModifyTime();
        
        var oldTime = attendanceBook.getAttendanceTimeOf(nickname, targetDate);
        var oldStatus = attendanceBook.getAttendanceStatusOf(nickname, targetDate);
        attendanceBook.modify(nickname, targetDate, newTime);
        var newStatus = attendanceBook.getAttendanceStatusOf(nickname, targetDate);
        
        if (oldTime.isEmpty()) {
            outputView.outputAttendanceModifyResult(targetDate, newTime, newStatus);
            return;
        }
        outputView.outputAttendanceModifyResult(targetDate, oldTime.get(), oldStatus, newTime, newStatus);
    }
    
    private static void handleFindAttendanceRecord(
            final InputView inputView, final OutputView outputView,
            final AttendanceBook attendanceBook
    ) {
        var nickname = inputView.getRecordFindNickname();
        var attendances = attendanceBook.getAllAttendances(nickname);
        
        outputView.outputAttendanceRecords(
                nickname,
                attendances,
                attendanceBook.countAttendanceStatusOf(nickname, AttendanceStatus.출석),
                attendanceBook.countAttendanceStatusOf(nickname, AttendanceStatus.지각),
                attendanceBook.countAttendanceStatusOf(nickname, AttendanceStatus.결석),
                attendanceBook.getExpelWarningOf(nickname)
        );
    }
    
    private static void handleFindExpelWarnings(final OutputView outputView, final AttendanceBook attendanceBook) {
        var expelWarnings = attendanceBook.getExpelWarnings();
        
        var expelWarningResponses = expelWarnings.entrySet().stream()
                .map(entry -> new ExpelWarningCrewResponse(
                        entry.getKey(),
                        attendanceBook.countAttendanceStatusOf(entry.getKey(), AttendanceStatus.지각),
                        attendanceBook.countAttendanceStatusOf(entry.getKey(), AttendanceStatus.결석),
                        entry.getValue()))
                .collect(Collectors.toSet());
        
        outputView.outputExpelWarningCrews(expelWarningResponses);
    }
    
    @Override
    public void run() {
        while (runFlag) {
            var today = todayProvider.today();
            var selectedMenu = inputView.getSelectedMenu(today);
            handlers.get(selectedMenu).run();
        }
    }
}
