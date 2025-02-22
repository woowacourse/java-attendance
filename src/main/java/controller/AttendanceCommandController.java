package controller;

import domain.Command;
import domain.Crews;
import java.time.LocalDateTime;
import util.Constants;
import util.CrewGenerator;
import util.CsvReader;
import util.Loop;
import view.InputView;

public class AttendanceCommandController {

    private static final LocalDateTime FIX_DATE_TIME = LocalDateTime.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH,
            Constants.FIXED_DAY, 0, 0, 0, 0);

    public void run() {
        final Crews crews = CrewGenerator.generate(CsvReader.readFile(Constants.CSV_PATH), FIX_DATE_TIME.toLocalDate());

        Loop.run(() -> {
            final Command commandEnum = Command.findByCommandNumber(InputView.readCommand(FIX_DATE_TIME));
            final AttendanceCommand command = commandEnum.getCommandInstance();

            if (command == null) {
                return false;
            }
            command.execute(crews);
            return true;
        });
    }
}
