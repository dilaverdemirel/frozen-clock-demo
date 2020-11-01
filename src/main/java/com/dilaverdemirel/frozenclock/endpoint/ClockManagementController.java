package com.dilaverdemirel.frozenclock.endpoint;

import com.dilaverdemirel.frozenclock.clock.FrozenClock;
import com.dilaverdemirel.frozenclock.dto.FrozenClockInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dilaverdemirel
 * @since 10/31/20
 */
@RestController
@RequestMapping("/clock")
@RequiredArgsConstructor
public class ClockManagementController {

    private final ApplicationEventPublisher eventPublisher;

    @PutMapping(path = "/freeze")
    public ResponseEntity<Void> freeze(@RequestBody @Valid FrozenClockInfo frozenClockInfo) {
        FrozenClock.freeze(frozenClockInfo.getNewDate());
        refreshSystemClock();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/unfreeze")
    public ResponseEntity<Void> unfreeze() {
        FrozenClock.unfreeze();
        refreshSystemClock();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void refreshSystemClock() {
        eventPublisher.publishEvent(new RefreshEvent(this, "RefreshEvent", "Refresh synchronized clock  related beans"));
    }
}
