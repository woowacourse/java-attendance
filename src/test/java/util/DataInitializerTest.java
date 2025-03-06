package util;

import static org.junit.jupiter.api.Assertions.*;

import domain.AttendanceManager;
import domain.AttendanceRecords;
import domain.CrewAttendanceBook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataInitializerTest {


    @DisplayName("파일에 있는 출석 정보들을 크루 출석부에 반영하여 반환한다")
    @Test
    void initCrewAttendanceBook() {
        // given
        AttendanceManager attendanceManager = new AttendanceManager(CrewAttendanceBook.createEmpty());
        DataInitializer dataInitializer = new DataInitializer(new FileLoader(), new StringParser(), attendanceManager);
        String filePath = "src/main/resources/attendances.csv";

        // when
        CrewAttendanceBook crewAttendanceBook = dataInitializer.initCrewAttendanceBook(filePath);

        // then
        AttendanceRecords attendanceRecords = crewAttendanceBook.retrieveAttendanceRecordsByName("쿠키");
        Assertions.assertThat(attendanceRecords.attendances().size()).isEqualTo(8);
    }
}
