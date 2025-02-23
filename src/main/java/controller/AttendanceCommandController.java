package controller;

import domain.Command;
import domain.Crews;
import java.time.LocalDateTime;
import util.CrewGenerator;
import util.CsvReader;
import util.Loop;
import view.InputView;

public class AttendanceCommandController {

    /**
     * 현재 기준이 되는 연도
     */
    public static final int REFERENCE_YEAR = 2024;
    /**
     * 현재 기준이 되는 달
     */
    public static final int REFERENCE_MONTH = 12;
    /**
     * 현재 기준이 되는 날
     */
    public static final int REFERENCE_DAY = 16;
    /**
     * 의뢰 : 2024년 12월 한 달 동안 시범적으로 최소한의 기능을 갖춘 출석 시스템을 개발하여 출석을 체계적으로 관리 현재 25년 2월이기에 2024년 12월에 제대로 동작하는지 확인하기 편하게 하기위해
     * 12월의 특정일로 고정했습니다. 다른날로 수정하고 싶으시면 FIXED_DAY를 수정하시면 됩니다. 물론 SYSTEM DATE_TIME을 LocalDateTIme.now()로 대체해서 사용하셔도
     * 무방합니다.
     */
    public static final LocalDateTime SYSTEM_DATE_TIME = LocalDateTime.of(REFERENCE_YEAR, REFERENCE_MONTH,
            REFERENCE_DAY, 0, 0);
    private static final String CSV_PATH = "src/main/resources/attendances.csv";

    public void run() {
        final Crews crews = CrewGenerator.generate(CsvReader.readFile(CSV_PATH), SYSTEM_DATE_TIME.toLocalDate());

        Loop.run(() -> {
            final Command commandEnum = Command.findByCommandNumber(InputView.readCommand(SYSTEM_DATE_TIME));
            final AttendanceCommand command = commandEnum.getCommandInstance();

            if (command == null) {
                return false;
            }
            command.execute(crews);
            return true;
        });
    }
}
