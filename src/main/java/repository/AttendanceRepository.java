package repository;

import domain.CrewRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    private List<CrewRecord> crewRecords = new ArrayList<>();

    public CrewRecord attend(String inputName, LocalDateTime inputDateAndTime) {
        for (CrewRecord crewRecord : crewRecords) {
            if (crewRecord.isAttend(inputName, inputDateAndTime)) {
                throw new IllegalArgumentException("이미 출석한 경우 수정만 가능합니다.");
            }
        }
        CrewRecord newRecord = new CrewRecord(inputName, inputDateAndTime);
        crewRecords.add(newRecord);
        return newRecord;
    }

    public CrewRecord edit(String name, int dayOfMonth, String time) {
        for(CrewRecord crewRecord : crewRecords) {
            if(crewRecord.getName().equals(name) && crewRecord.getDateTime().getDayOfMonth() == dayOfMonth) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = LocalDate.of(2024, 12, dayOfMonth).atStartOfDay().format(formatter);
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime newDateTime = LocalDateTime.parse(date + " " + time, formatter);

                crewRecord.editRecord(newDateTime);
                return crewRecord;
            }
        }
        return null;
    }

    public List<CrewRecord> findByName(String name) {
        List<CrewRecord> records = new ArrayList<>();
        for (CrewRecord crewRecord : crewRecords) {
            if (name.equals(crewRecord.getName())) {
                records.add(crewRecord);
            }
        }
        return records;
    }
}
