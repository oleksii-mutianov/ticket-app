package ua.alxmute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateDto {

    @NotNull
    @Min(0)
    private Long routeId;

    @NotNull
    @Future
    private LocalDateTime departureTime;

}
