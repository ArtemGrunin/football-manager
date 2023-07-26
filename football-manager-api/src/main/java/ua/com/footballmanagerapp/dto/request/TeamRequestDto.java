package ua.com.footballmanagerapp.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TeamRequestDto {
    private String name;
    private String city;
    private String country;
    private BigDecimal budget;
    @DecimalMin(value = "0.0", message = "Commission should not be less than 0%")
    @DecimalMax(value = "10.0", message = "Commission should not be more than 10%")
    private BigDecimal transferCommission;
}
