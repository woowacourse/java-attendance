package controller;

import domain.Crew;
import domain.CrewRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import util.FileParser;

public class ResourceLoader {
    public static void loadAttendanceTimes() {
        FileParser.loadAttendanceRecords()
                .forEach(record -> addAttendanceTime(
                        record.nickname(),
                        record.date(),
                        record.time()
                ));
    }

    private static void addAttendanceTime(String nickname, LocalDate date, LocalTime time) {
        Crew crew;
        if(!CrewRepository.exists(nickname)) {
            crew = new Crew(nickname);
            CrewRepository.addCrew(crew);
        }
        crew = CrewRepository.findByNickname(nickname);
        crew.addAttendanceTime(date, time);
    }
}
