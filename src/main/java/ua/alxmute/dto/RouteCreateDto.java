package ua.alxmute.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class RouteCreateDto {

    @NotNull
    @Size(min = 2, max = 255)
    private String arrivalCity;

    @NotNull
    @Size(min = 2, max = 255)
    private String departureCity;

}
