package view;

import domain.AllCrew;
import domain.Crew;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FileInputView {
    public void readAttendanceFile(AllCrew allCrew) {
        try {
            FileReader fileReader = new FileReader("src/main/resource/attendance.csv");
            Scanner scanner = new Scanner(fileReader);
            readFilePerLine(allCrew, scanner);
        } catch (FileNotFoundException e) {
            System.out.println("없는 파일입니다.");
        }
    }

    private void readFilePerLine(AllCrew allCrew, Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String crewName = line.split(",")[0];
            addNewCrewWhenNotExisted(allCrew, crewName);
            initializeCrewInfo(allCrew, line, crewName);
        }
    }

    private void addNewCrewWhenNotExisted(AllCrew allCrew, String crewName) {
        if (!allCrew.isContainedCrewName(crewName)) {
            allCrew.addCrew(new Crew(crewName));
        }
    }

    private void initializeCrewInfo(AllCrew allCrew, String line, String crewName) {
        String[] attendanceDateTime = line.split(",")[1].split(" ");
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(attendanceDateTime[0]),
                Integer.parseInt(attendanceDateTime[1]),
                Integer.parseInt(attendanceDateTime[2]),
                Integer.parseInt(attendanceDateTime[3]),
                Integer.parseInt(attendanceDateTime[4]));
        allCrew.addCrewAttendanceByName(crewName, localDateTime);
    }
}
