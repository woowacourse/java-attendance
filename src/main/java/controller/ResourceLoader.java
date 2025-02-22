package controller;

import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import repository.CrewRepository;
import util.FileParser;

public class ResourceLoader {
    public static void loadCrewRepository() {
        FileParser.loadAttendanceRecords()
                .forEach(record -> addAttendanceTime(
                        record.nickname(),
                        record.date(),
                        record.time()
                ));
    }

    private static void addAttendanceTime(String nickname, LocalDate date, LocalTime time) {
        Crew crew;
        if (!CrewRepository.exists(nickname)) {
            crew = new Crew(nickname);
            CrewRepository.addCrew(crew);
        }
        crew = CrewRepository.findByNickname(nickname);
        crew.insertAttendanceTime(date, time);
    }
}
