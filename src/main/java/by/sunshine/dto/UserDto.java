package by.sunshine.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String currency = "by";
    private Long cartId;
    private Set<Long> productIds = new HashSet<>();
    private Double valueOfCurrency = 1D;
    private Long orderCartId;
    private Set<Long> orderIds = new HashSet<>();
}
