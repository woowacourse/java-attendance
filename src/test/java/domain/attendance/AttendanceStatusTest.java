package domain.attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

import static domain.attendance.AttendanceStatus.*;
import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.*;

class AttendanceStatusTest {
    @Nested
    class StatusTest{
        @Nested
        class isAttendance{
            @DisplayName("월요일 출석 테스트")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13})
            void mondayAttendanceTest(int hour){
                LocalTime attendanceTime = LocalTime.of(hour,5);
                assertThat(calcAttendanceStatus(MONDAY,attendanceTime)).isEqualTo(ATTENDANCE);

                LocalTime lateTime = LocalTime.of(13,6);
                assertThat(calcAttendanceStatus(MONDAY,lateTime)).isNotEqualTo(ATTENDANCE);
            }

            @DisplayName("월요일 제외 출석 테스트")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
            void exceptMondayAttendanceTest(int hour){
                LocalTime attendanceTime = LocalTime.of(hour,5);
                Arrays.stream(DayOfWeek.values())
                        .filter(dayOfWeek -> dayOfWeek != MONDAY)
                        .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,attendanceTime)).isEqualTo(ATTENDANCE));

                LocalTime lateTime = LocalTime.of(10,6);
                Arrays.stream(DayOfWeek.values())
                        .filter(dayOfWeek ->  dayOfWeek != MONDAY)
                        .forEach(dayOfWeek ->  assertThat(calcAttendanceStatus(dayOfWeek,lateTime)).isNotEqualTo(ATTENDANCE));
            }
        }

        @Nested
        class isTardy{
            @DisplayName("월요일 지각 테스트")
            @ParameterizedTest
            @ValueSource(ints = 30)
            void mondayTardyTest(int minLimit){
                for(int min = 6; min <= minLimit; min++){
                    LocalTime tardyTime = LocalTime.of(13,min);
                    assertThat(calcAttendanceStatus(MONDAY,tardyTime)).isEqualTo(TARDY);
                }

                LocalTime attendanceTime = LocalTime.of(13,5);
                assertThat(calcAttendanceStatus(MONDAY,attendanceTime)).isNotEqualTo(TARDY);

                LocalTime absenceTime = LocalTime.of(13,minLimit+1);
                assertThat(calcAttendanceStatus(MONDAY,absenceTime)).isNotEqualTo(TARDY);
            }

            @DisplayName("월요일 제외 지각 테스트")
            @ParameterizedTest
            @ValueSource(ints = {30})
            void exceptMondayTardyTest(int minLimit){
                for(int min = 6; min <= minLimit; min++){
                    LocalTime tardyTime = LocalTime.of(10,min);
                    Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY)
                            .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,tardyTime)).isEqualTo(TARDY));
                }

                LocalTime attendanceTime = LocalTime.of(10,5);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY)
                        .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,attendanceTime)).isNotEqualTo(TARDY));

                LocalTime absenceTime = LocalTime.of(10,minLimit+1);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY)
                        .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,absenceTime)).isNotEqualTo(TARDY));
            }
        }

        @Nested
        class isAbsence{
            @DisplayName("월요일 결석 테스트")
            @ParameterizedTest
            @ValueSource(ints = 31)
            void mondayAbsenceTest(int minStart){
                for(int min = minStart; min < 60; min++){
                    LocalTime absenceTime = LocalTime.of(13,min);
                    assertThat(calcAttendanceStatus(MONDAY,absenceTime)).isEqualTo(ABSENCE);
                }

                LocalTime tardyTime = LocalTime.of(13,5);
                assertThat(calcAttendanceStatus(MONDAY,tardyTime)).isNotEqualTo(ABSENCE);

                LocalTime attendanceTime = LocalTime.of(13,0);
                assertThat(calcAttendanceStatus(MONDAY,attendanceTime)).isNotEqualTo(ABSENCE);
            }

            @DisplayName("월요일 제외 결석 테스트")
            @ParameterizedTest
            @ValueSource(ints = 31)
            void exceptMondayAbsenceTest(int minStart){
                for(int min = minStart; min < 60; min++){
                    LocalTime absenceTime = LocalTime.of(10,min);
                    Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY)
                            .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,absenceTime)).isEqualTo(ABSENCE));
                }

                LocalTime tardyTime = LocalTime.of(10,5);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY)
                        .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,tardyTime)).isNotEqualTo(ABSENCE));

                LocalTime attendanceTime = LocalTime.of(10,0);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY)
                        .forEach(dayOfWeek -> assertThat(calcAttendanceStatus(dayOfWeek,attendanceTime)).isNotEqualTo(ABSENCE));
            }
        }
    }
}