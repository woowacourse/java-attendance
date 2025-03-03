package fixture;

import domain.AttendanceRecord;
import domain.Crew;
import domain.LectureTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordsGenerator {

    public static List<AttendanceRecord> generate(LocalDate from, LocalDate to,
                                                  Crew crew, int lateCount, int absentCount) {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            if (!LectureTime.isLectureDate(date)) {
                continue;
            }
            if (lateCount > 0) {
                final int delayForLate = 6;
                attendanceRecords.add(
                        AttendanceRecord.of(crew, date,
                                LectureTime.from(date).getStartTime().plusMinutes(delayForLate))
                );
                lateCount--;
                continue;
            }
            if (absentCount > 0) {
                final int delayForAbsent = 31;
                attendanceRecords.add(
                        AttendanceRecord.of(crew, date,
                                LectureTime.from(date).getStartTime().plusMinutes(delayForAbsent))
                );
                absentCount--;
                continue;
            }

            // 정상 출석
            attendanceRecords.add(
                    AttendanceRecord.of(crew, date, LectureTime.from(date).getStartTime())
            );
        }

        if (lateCount > 0 || absentCount > 0) {
            throw new IllegalArgumentException("날짜 범위를 키우세요");
        }
        return attendanceRecords;
    }
}
