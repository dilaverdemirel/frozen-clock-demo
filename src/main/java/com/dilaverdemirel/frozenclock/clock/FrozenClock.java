package com.dilaverdemirel.frozenclock.clock;

import javax.validation.ClockProvider;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;

/**
 * @author dilaverdemirel
 * @since 10/31/20
 */
public final class FrozenClock {

    private static Date systemDate = new Date();
    private static boolean frozen = false;

    private FrozenClock() {
    }

    public static void freeze(final Date newDate) {
        systemDate = newDate;
        frozen = true;
    }

    public static void unfreeze() {
        frozen = false;
    }

    public static ClockProvider getClockProvider() {
        return () -> {
            if (frozen) {
                return Clock.fixed(Instant.ofEpochSecond(systemDate.getTime()), Clock.systemDefaultZone().getZone());
            } else {
                return Clock.systemDefaultZone();
            }
        };
    }
}
