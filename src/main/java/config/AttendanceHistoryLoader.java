package config;

import domain.Attendance;
import domain.Crew;
import domain.Crews;
import domain.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AttendanceHistoryLoader {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Crews loadCrews() {
        Crews crews = new Crews(new ArrayList<>());

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            skipItemTitle(reader);
            loadAttendanceHistory(reader, crews);
        } catch (IOException e) {
            throw new IllegalStateException("크루원들의 출석 기록을 읽는 중 오류가 발생했습니다.", e);
        }

        return crews;
    }

    private void skipItemTitle(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void loadAttendanceHistory(BufferedReader reader, Crews crews) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nicknameAndDateTime = parseLine(line);
            Crew crew = crews.getOrRegisterCrew(nicknameAndDateTime[0]);

            registerAttendance(crew, nicknameAndDateTime[1]);
        }
    }

    private String[] parseLine(String line) {
        return line.split(",");
    }

    private void registerAttendance(Crew crew, String datetime) {
        String[] dateAndTime = datetime.split(" ");
        String date = dateAndTime[0];
        String time = dateAndTime[1];

        crew.addAttendance(new Attendance(new Day(LocalDate.parse(date, dateFormatter)), LocalTime.parse(time, timeFormatter)));
    }
}
