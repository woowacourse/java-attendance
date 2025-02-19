package controller;

import domain.AttendTime;
import domain.AttendanceFileReader;
import domain.Crews;
import domain.StringParser;
import java.util.List;
import view.InputView;

public class AttendanceController {

    private final InputView inputView;

    public AttendanceController(final InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        List<String> students = AttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(StringParser.parse(students));

        String command = inputView.readCommand();
        if (command.equals("1")) {
            String nickname = inputView.readNickname();
            String time = inputView.readTime();
            System.out.println(crews.ifFindNameAddTime(nickname, time));
        }
        if (command.equals("2")) {
            String nickname = inputView.readNickNameForChange();
            int date = inputView.readDateForChange();
            String time = inputView.readTimeForChange();
            AttendTime attendTime = crews.deleteAttendance(nickname, date);
            
            int year = attendTime.getAttendTime().getYear();
            int month = attendTime.getAttendTime().getMonthValue();
            int date2 = attendTime.getAttendTime().getDayOfMonth();
            System.out.print(time);
            String inputTime = String.format("%d-%d-%d %s", year, month, date2, time);
            System.out.println(crews.findCrew(nickname).attend(inputTime));
        }
    }

}
