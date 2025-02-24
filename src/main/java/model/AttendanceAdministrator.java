package model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceAdministrator {


//    public static Map<String, LocalDateTime> findAttendanceInfo(String crewInput) {
//        List<String> parsedCrewInput = Arrays.stream(crewInput.split("\n")).toList();
//        Map<String, LocalDateTime> times = new HashMap<>();
//        parsedCrewInput.forEach(c -> {
//            String[] nameAndDateTime = c.split(",");
//            String[] dateAndTime = nameAndDateTime[1].split(" ");
//            String[] yearAndMonthAndDate = dateAndTime[0].split("-");
//            String[] hourAndMinute = dateAndTime[1].split(":");
//            times.put(nameAndDateTime[0], LocalDateTime.of(
//                    Integer.parseInt(yearAndMonthAndDate[0]),
//                    Integer.parseInt(yearAndMonthAndDate[1]),
//                    Integer.parseInt(yearAndMonthAndDate[2]),
//                    Integer.parseInt(hourAndMinute[0]),
//                    Integer.parseInt(hourAndMinute[1])
//            ));
//        });
//        return times;
//    }
}
