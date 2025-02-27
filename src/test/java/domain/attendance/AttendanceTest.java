package domain.attendance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static domain.attendance.TimeTable.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AttendanceTest {
    Attendance attendance;

    @BeforeEach
    void setUpAttendance(){
        attendance = new Attendance();
    }

    @Nested
    class AddAttendanceDateTest{
        @DisplayName("2025.2.27일 (목요일) 넣기")
        @Test
        void addAttendanceDate(){
            attendance.addAttendance(LocalDateTime.of(2025,2,27,10,0));
            assertThat(attendance.findByLocalDate(LocalDate.of(2025,2,27)))
                    .isInstanceOf(AttendanceDate.class);
        }

        @DisplayName("주말의 경우 입력되지 않음")
        @Test
        void addAllLocalDate(){
            IntStream.range(1,28)
                    .forEach(day -> attendance.addAttendance(LocalDateTime.of(2025,2,day,10,0)));

            IntStream.range(1,28)
                    .filter(day -> !isAttendanceDay(LocalDate.of(2025,2,day)))
                    .forEach(day -> assertThatThrownBy(
                            () -> attendance.findByLocalDate(LocalDate.of(2025,2,day))).isInstanceOf(IllegalArgumentException.class));
        }
    }

    @Nested
    class findAttendanceDateTest{
        @DisplayName("존재하지 않는 날짜 조회 시 에러 발생")
        @Test
        void nonExistenceLocalDateTest(){
            IntStream.range(3,7)
                    .forEach(day -> attendance.addAttendance(LocalDateTime.of(2025,2,day,10,0)));

            LocalDate missingDate = LocalDate.of(2025,2,17);
            assertThatThrownBy(() -> attendance.findByLocalDate(missingDate)).isInstanceOf(IllegalArgumentException.class);
        }

        @Nested
        class checkFoundAttendanceDateStatus{
            @DisplayName("조회 후 결석 체크")
            @Test
            void absenceTest(){
                LocalDateTime absenceMondayTime = LocalDateTime.of(2025,2,3,15,0);
                attendance.addAttendance(absenceMondayTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceMondayTime)).isAbsence()).isTrue();

                LocalDateTime absenceTime = LocalDateTime.of(2025,2,4,11,0);
                attendance.addAttendance(absenceTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceTime)).isAbsence()).isTrue();
            }

            @DisplayName("조회 후 지각 체크")
            @Test
            void tardyTest(){
                LocalDateTime absenceMondayTime = LocalDateTime.of(2025,2,3,13,6);
                attendance.addAttendance(absenceMondayTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceMondayTime)).isTardy()).isTrue();

                LocalDateTime absenceTime = LocalDateTime.of(2025,2,4,10,6);
                attendance.addAttendance(absenceTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceTime)).isTardy()).isTrue();
            }

            @DisplayName("조회 후 출석 체크")
            @Test
            void attendanceTest(){
                LocalDateTime absenceMondayTime = LocalDateTime.of(2025,2,3,11,0);
                attendance.addAttendance(absenceMondayTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceMondayTime)).isAttendance()).isTrue();

                LocalDateTime absenceTime = LocalDateTime.of(2025,2,4,10,0);
                attendance.addAttendance(absenceTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceTime)).isAttendance()).isTrue();
            }
        }
    }

    @Nested
    class editAttendanceDateTest{
        @BeforeEach
        void addLocalDate(){
            IntStream.range(1,28).
                    forEach(day -> attendance.addAttendance(LocalDateTime.of(2025,2,day,10,0)));
        }

        @DisplayName("존재하는 날짜 결석으로 수정")
        @Test
        void editExistAttendanceToAbsence(){
            LocalDateTime editToAbsence = LocalDateTime.of(2025,2,11,14,0);
            attendance.editAttendance(editToAbsence);

            assertThat(attendance.findByLocalDate(LocalDate.from(editToAbsence)).isAbsence()).isTrue();
        }

        @DisplayName("존재하는 날짜 지각으로 수정")
        @Test
        void editExistAttendanceToTardy(){
            LocalDateTime editToAbsence = LocalDateTime.of(2025,2,11,10,10);
            attendance.editAttendance(editToAbsence);

            assertThat(attendance.findByLocalDate(LocalDate.from(editToAbsence)).isTardy()).isTrue();
        }

        @DisplayName("존재하지 않는 날짜 수정 시 에러 발생")
        @Test
        void editNoneExistAttendance(){
            LocalDateTime missingDate = LocalDateTime.of(2025,2,9,13,0);

            assertThatThrownBy(() -> attendance.editAttendance(missingDate)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
