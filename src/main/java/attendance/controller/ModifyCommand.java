package attendance.controller;

import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.Nickname;
import attendance.util.StringParser;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;

public class ModifyCommand implements Command {

    private final InputView inputView;
    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;

    public ModifyCommand(final InputView inputView, final ResultView resultView, final Clock clock,
                         final CampusScheduler campusScheduler) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        LocalDate now = LocalDate.now(clock);
        Nickname nickname = makeNickname();
        crewHistories.validateKeyExists(nickname);
        LocalDate modifyingDate = makeDate(now);
        campusScheduler.validateOperationDate(modifyingDate);
        validatePreviousDate(modifyingDate, now);
        crewHistories.validateHistoryExists(nickname, modifyingDate);

        LocalTime time = makeTime();
        LocalDateTime modifyingDateTime = LocalDateTime.of(modifyingDate, time);
        campusScheduler.validateOperationTime(modifyingDateTime);

        crewHistories.modify(nickname, modifyingDateTime);
    }

    private void validatePreviousDate(final LocalDate date, final LocalDate nowDate) {
        if (date.equals(nowDate) || date.isAfter(nowDate)) {
            throw new IllegalArgumentException("[ERROR] 과거의 날짜만 가능합니다.");
        }
    }

    private LocalTime makeTime() {
        String time = inputView.readModifyingTime();
        return StringParser.parseLocalTime(time);
    }

    private LocalDate makeDate(final LocalDate now) {
        String dayInput = inputView.readModifyingDay();
        int day = StringParser.parseInt(dayInput);
        MonthDay monthDay = makeMonthDay(now, day);
        return LocalDate.of(now.getYear(), monthDay.getMonthValue(), monthDay.getDayOfMonth());
    }

    private MonthDay makeMonthDay(final LocalDate now, final int day) {
        try {
            return MonthDay.of(now.getMonthValue(), day);
        } catch (DateTimeException exception) {
            throw new IllegalArgumentException("존재하지 않은 날짜(일)입니다.");
        }
    }

    private Nickname makeNickname() {
        String nickname = inputView.readModifyingNickname();
        return new Nickname(nickname);
    }
}
