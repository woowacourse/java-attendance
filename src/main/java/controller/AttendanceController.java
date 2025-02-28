package controller;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceStatus;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static controller.Command.*;

public class AttendanceController {
    private final CrewGroup crews;
    private Command currentCommand;

    public AttendanceController() {
        crews = new CrewGroup();
    }

    public void run(){
        while(true){
            OutputView.printWelcomeMessage();
            currentCommand = InputView.getCommand();
            if(currentCommand == EXIT){ break;}
            operateCommand();
        }
    }

    private void operateCommand(){
        try{
            if(currentCommand == ATTEND){
                attendCommand();
            }
            if(currentCommand == EDIT){

            }
            if(currentCommand == FIND_CREW_RECORD){

            }
            if(currentCommand == FIND_WARNING_CREWS){

            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void attendCommand(){
        String attendCrewName = InputView.getCrewName();
        Crew findCrew = crews.findByName(attendCrewName);

        LocalDateTime attendTime = InputView.getAttendTime();
        Attendance crewAttendance = findCrew.getAttendanceRecord();
        crewAttendance.addAttendance(attendTime);
        AttendanceStatus status = crewAttendance.findByLocalDate(LocalDate.from(attendTime)).getStatus();
        OutputView.printAddAttendance(attendTime,status);
    }
}
