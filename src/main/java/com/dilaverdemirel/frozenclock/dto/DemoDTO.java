package com.dilaverdemirel.frozenclock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * @author dilaverdemirel
 * @since 10/31/20
 */
@Getter
@Setter
@ToString
public class DemoDTO {
    @NotEmpty
    private String id;

    @PastOrPresent
    @NotNull
    private Date operationDate;
}
