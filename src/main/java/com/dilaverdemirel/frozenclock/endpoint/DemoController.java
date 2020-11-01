package com.dilaverdemirel.frozenclock.endpoint;

import com.dilaverdemirel.frozenclock.dto.DemoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dilaverdemirel
 * @since 10/31/20
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @PostMapping
    public @ResponseBody
    ResponseEntity<Void> create(@Valid @RequestBody DemoDTO demoDTO) {
        log.debug("demoDTO = {}", demoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
