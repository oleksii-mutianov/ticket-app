package ua.alxmute.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDto {

    private Long id;
    private String arrivalCity;
    private String departureCity;

}
