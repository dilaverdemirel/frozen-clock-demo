package com.dilaverdemirel.frozenclock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dilaverdemirel
 * @since 10/31/20
 */
@Getter
@Setter
@ToString
public class FrozenClockInfo {
    @NotNull
    private Date newDate;
}
