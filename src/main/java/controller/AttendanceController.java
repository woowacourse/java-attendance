package controller;

import domain.*;

import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<String> students = AttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(students);

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

        if(command.equals("3")){
            String nickname= inputView.readNickname();
            System.out.printf("이번 달 %s의 출석 기록입니다.%n",nickname);
            System.out.println();
            outputView.printCrewAttendance(crews.findCrew(nickname));

        }
    }

}
