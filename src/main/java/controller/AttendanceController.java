package controller;

import domain.AttendanceBook;
import domain.AttendanceTime;
import domain.Crew;
import domain.CrewAttendance;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class AttendanceController {

    private final AttendanceBook attendanceBook;

    public AttendanceController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String commandCode = scanner.next();
        if (commandCode.equals("Q")) {
            System.out.println("프로그램을 종료합니다.");
            return;
        }
        if (commandCode.equals("1")) {
            String crewName = scanner.next();
            Crew crew = Crew.of(crewName);
            CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);
            String rawTime = scanner.next();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(rawTime, timeFormatter);
            LocalDate date = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
            AttendanceTime attendanceTime = AttendanceTime.of(date, time);
            crewAttendance.attend(attendanceTime);
            System.out.printf("%s (%s)\n", time.format(timeFormatter), getStatus(date, time));
            return;
        }
        if (commandCode.equals("2")) {
            String crewName = scanner.next();
            Crew crew = Crew.of(crewName);
            CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);
            int dayOfMonth = Integer.parseInt(scanner.next());
            LocalDate date = LocalDate.of(2024, 12, dayOfMonth);
            String rawTime = scanner.next();
            LocalTime time = LocalTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_TIME);
            AttendanceTime attendanceTime = AttendanceTime.of(date, time);
            Optional<AttendanceTime> previous = crewAttendance.modify(attendanceTime);
            String timeStamp = "--:--";
            if (previous.isPresent()) {
                timeStamp = previous.get().toLocalDateTime().toString();
            }
            // TODO: 바뀐 시간 출력
            System.out.printf("%s -> %s\n", timeStamp, time);
            return;
        }
        if (commandCode.equals("3")) {
            String crewName = scanner.next();
            Crew crew = Crew.of(crewName);
            CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);
            // TODO : attendanceBook에서 출석 기록을 반환하는 리스트 호출
            return;
        }
        if (commandCode.equals("4")) {
            // TODO: 제적 위험자 조회
            return;
        }
        System.out.println("\"1, 2, 3, 4, Q\" 만 입력할 수 있습니다");
    }

    private String getStatus(LocalDate date, LocalTime time) {
        LocalTime start = LocalTime.of(10, 0);
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            start = LocalTime.of(13, 0);
        }
        long minutes = Duration.between(start, time).toMinutes();
        if (minutes <= 5) {
            return "출석";
        }
        if (minutes <= 30) {
            return "지각";
        }
        return "결석";
    }
}
