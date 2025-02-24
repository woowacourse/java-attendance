package domain;

public record CrewDto(
        String nickname,
        int absenceCount,
        int tardinessCount,
        int attendanceCount,
        int adjustedAbsenceCount,
        Punishment punishment
) {
    public static CrewDto from(Crew crew) {
        final String nickname = crew.getNickname().getNickname();
        final AttendanceCounter attendanceCounter = crew.getAttendanceCounter();
        final int absenceCount = attendanceCounter.getAbsence();
        final int tardinessCount = attendanceCounter.getTardiness();
        final int attendanceCount = attendanceCounter.getAttendanceCount();
        final int adjustedAbsenceCount = attendanceCounter.calculateAdjustedAbsenceCountWithTardinessCount();
        final Punishment punishment = Punishment.findByAbsenceCount(adjustedAbsenceCount);

        return new CrewDto(nickname, absenceCount, tardinessCount, attendanceCount, adjustedAbsenceCount, punishment);
    }
}
