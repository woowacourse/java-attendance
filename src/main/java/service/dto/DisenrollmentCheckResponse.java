package service.dto;

public record DisenrollmentCheckResponse(String name, int absenceCount, int lateCount, int convertedAbsenceCount,
                                         String crewStatus) {
}
