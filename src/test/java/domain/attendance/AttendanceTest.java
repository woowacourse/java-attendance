package domain.attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTest {
    Attendance attendance;

    @BeforeEach
    void setUpAttendance(){
        attendance = new Attendance();
    }

    @Nested
    class AddAttendanceDateTest{
        @DisplayName("2024.12.12일 (목요일) 넣기")
        @Test
        void addAttendanceDate(){
            attendance.addAttendance(LocalDateTime.of(2024,12,12,10,0));
            assertThat(attendance.findByLocalDate(LocalDate.of(2024,12,12)))
                    .isInstanceOf(AttendanceDate.class);
        }

        @DisplayName("주말의 경우 입력되지 에러를 반환")
        @Test
        void addAllLocalDate(){
            LocalDateTime weekendDateTime = LocalDateTime.of(2024,12,7,10,12);

            assertThatThrownBy(() -> attendance.addAttendance(weekendDateTime)).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("캠퍼스 운영시간이 아닌 경우 에러 반환")
        @Test
        void isNotCampusOperatingTime(){
            LocalDateTime isNoOperationTime = LocalDateTime.of(2024,12,12,23,12);

            assertThatThrownBy(() -> attendance.addAttendance(isNoOperationTime)).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("미래의 경우 에러반환")
        @Test
        void isNotValidAttendanceDate(){
            IntStream.range(1,30)
                    .forEach(day ->
                            assertThatThrownBy(() -> attendance.addAttendance(LocalDateTime.of(
                                    2025, 12, day,10,10)))
                                    .isInstanceOf(IllegalArgumentException.class));
        }
    }

    @Nested
    class findAttendanceDateTest{
        @DisplayName("존재하지 않는 날짜 조회 시 에러 발생")
        @Test
        void nonExistenceLocalDateTest(){
            IntStream.range(3,7)
                    .forEach(day -> attendance.addAttendance(LocalDateTime.of(2024,12,day,10,0)));

            LocalDate missingDate = LocalDate.of(2024,12,17);
            assertThatThrownBy(() -> attendance.findByLocalDate(missingDate)).isInstanceOf(IllegalArgumentException.class);
        }

        @Nested
        class checkFoundAttendanceDateStatus{
            @DisplayName("조회 후 결석 체크")
            @Test
            void absenceTest(){
                LocalDateTime absenceMondayTime = LocalDateTime.of(2024,12,3,15,0);
                attendance.addAttendance(absenceMondayTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceMondayTime)).isAbsence()).isTrue();

                LocalDateTime absenceTime = LocalDateTime.of(2024,12,4,11,0);
                attendance.addAttendance(absenceTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceTime)).isAbsence()).isTrue();
            }

            @DisplayName("조회 후 지각 체크")
            @Test
            void tardyTest(){
                LocalDateTime absenceMondayTime = LocalDateTime.of(2024,12,9,13,6);
                attendance.addAttendance(absenceMondayTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceMondayTime)).isTardy()).isTrue();

                LocalDateTime absenceTime = LocalDateTime.of(2024,12,4,10,6);
                attendance.addAttendance(absenceTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(absenceTime)).isTardy()).isTrue();
            }

            @DisplayName("조회 후 출석 체크")
            @Test
            void attendanceTest(){
                LocalDateTime attendanceMondayTime = LocalDateTime.of(2024,12,2,13,0);
                attendance.addAttendance(attendanceMondayTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(attendanceMondayTime)).isAttendance()).isTrue();

                LocalDateTime attendanceTime = LocalDateTime.of(2024,12,5,10,0);
                attendance.addAttendance(attendanceTime);
                assertThat(attendance.findByLocalDate(LocalDate.from(attendanceTime)).isAttendance()).isTrue();
            }

            @DisplayName("출석기록 리스트로 가져오기")
            @Test
            void attendanceResultList(){
                LocalDateTime absenceMondayTime1 = LocalDateTime.of(2024,12,3,11,0);
                LocalDateTime absenceMondayTime2 = LocalDateTime.of(2024,12,5,11,0);
                LocalDateTime absenceMondayTime3 = LocalDateTime.of(2024,12,9,11,0);
                LocalDateTime absenceMondayTime4 = LocalDateTime.of(2024,12,10,11,0);

                attendance.addAttendance(absenceMondayTime1);
                attendance.addAttendance(absenceMondayTime2);
                attendance.addAttendance(absenceMondayTime3);
                attendance.addAttendance(absenceMondayTime4);

                assertThat(attendance.getSortedAttendanceResult().size()).isEqualTo(4);
            }
        }
    }

    @Nested
    class editAttendanceDateTest{
        @BeforeEach
        void addLocalDate(){
            IntStream.range(1,13)
                    .filter(day -> TimeTable.isAttendanceDay(LocalDate.of(2024,12,day)))
                    .forEach(day -> attendance.addAttendance(LocalDateTime.of(2024,12,day,10,0)));
        }

        @DisplayName("존재하는 날짜 결석으로 수정")
        @Test
        void editExistAttendanceToAbsence(){
            LocalDateTime editToAbsence = LocalDateTime.of(2024,12,11,14,0);
            attendance.editAttendance(editToAbsence);

            assertThat(attendance.findByLocalDate(LocalDate.from(editToAbsence)).isAbsence()).isTrue();
        }

        @DisplayName("존재하는 날짜 지각으로 수정")
        @Test
        void editExistAttendanceToTardy(){
            LocalDateTime editToAbsence = LocalDateTime.of(2024,12,10,10,10);
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

    @Nested
    class getStatusCountTest{
        @BeforeEach
        void setUpAttendanceDate(){
            List<LocalDateTime> attendanceList = List.of(
                    LocalDateTime.of(2024,12,2,10,3),
                    LocalDateTime.of(2024,12,3,10,3),
                    LocalDateTime.of(2024,12,4,10,3),
                    LocalDateTime.of(2024,12,5,10,3)
            );

            List<LocalDateTime> tardyList = List.of(
                    LocalDateTime.of(2024,12,6,10,8),
                    LocalDateTime.of(2024,12,9,13,6),
                    LocalDateTime.of(2024,12,10,10,10)
            );

            List<LocalDateTime> absenceList = List.of(
                    LocalDateTime.of(2024,12,11,15,3),
                    LocalDateTime.of(2024,12,12,15,3)
            );

            attendanceList.forEach(date -> attendance.addAttendance(date));
            tardyList.forEach(date -> attendance.addAttendance(date));
            absenceList.forEach(date -> attendance.addAttendance(date));
        }

        @DisplayName("출석 지각 결석 수 확인")
        @Test
        void statusCountCheck(){
            assertAll(
                    () -> assertThat(attendance.getAttendanceCount()).isEqualTo(4),
                    () -> assertThat(attendance.getTardyCount()).isEqualTo(3),
                    () -> assertThat(attendance.getAbsenceCount()).isEqualTo(2)
            );
        }

        @DisplayName("테이블에 아무것도 존재하지 않을 때 결석 수 계산")
        @Test
        void calcAbsenceCount(){
            Attendance attend = new Attendance();

            assertThat(attend.getAbsenceCount()).isEqualTo(9);
        }
    }
}
