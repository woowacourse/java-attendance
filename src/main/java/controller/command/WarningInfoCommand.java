package controller.command;

import domain.AttendanceBook;
import java.util.function.Consumer;
import view.OutputView;

public class WarningInfoCommand implements Consumer<AttendanceBook> {
    private final OutputView outputView;

    public WarningInfoCommand(OutputView outputView) {
        this.outputView = outputView;
    }

    @Override
    public void accept(AttendanceBook attendanceBook) {
        /*
        제적 위험자 조회 결과
        - 빙티: 결석 3회, 지각 4회 (면담)
        - 이든: 결석 2회, 지각 5회 (면담)
        - 빙봉: 결석 1회, 지각 6회 (면담)
        - 쿠키: 결석 2회, 지각 3회 (면담)
        - 짱수: 결석 0회, 지각 6회 (경고)
         */
    }
}
