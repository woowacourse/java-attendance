package domain.command;

import domain.*;
import util.Converter;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceCheckCommand implements Command {

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceCheckCommand(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void execute() {
        checkHoliday(LocalDate.now());

        Crew crew = crews.findByNickname(inputView.getNickname());
        if (crew.isAlreadyAttend(LocalDate.now())) {
            System.out.println("이미 출석 완료되었습니다. 수정 기능을 이용해주세요.");
            return;
        }

        registerAttendance(crew);
    }

    private void registerAttendance(Crew crew) {
        LocalTime attendanceTime = Converter.convertStringToLocalTime(inputView.getAttendanceTime());
        Attendance attendance = new Attendance(new Day(LocalDate.now()), attendanceTime);
        crew.addAttendance(attendance);
        outputView.printAttendanceInformation(attendance.toDto());
    }

    private void checkHoliday(LocalDate todayDate) {
        Day today = new Day(todayDate);
        int month = todayDate.getMonth().getValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeekName = DayOfWeek.getNameById(todayDate.getDayOfWeek().getValue());

        if (today.checkHoliday()) {
            throw new IllegalArgumentException("[ERROR] " + month + "월 " + dayOfMonth + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }
}
