package view;

import model.AttendanceCountsDto;
import model.ExpulsionType;

public record ExpulsionInfoDto(AttendanceCountsDto countsDto, ExpulsionType expulsionType) {
}
