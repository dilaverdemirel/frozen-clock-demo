## It demonstrate Spring Bean Validation customization for date based constraints

If you want to test your api that has a @PastOrPresent constraint for how it is work at future, this demo application will help you.

First step, we should create below configuration;

```java
@EnableWebMvc
@Configuration
public class FrozenClockConfig implements WebMvcConfigurer {

    @RefreshScope
    @Bean
    @Override
    public org.springframework.validation.Validator getValidator() {
        return new LocalValidatorFactoryBean() {
            @Override
            protected void postProcessConfiguration(javax.validation.Configuration<?> configuration) {
                configuration.clockProvider(FrozenClock.getClockProvider());
            }
        };
    }
}
```

it will get the ClockProvider through FrozenClock that has frozen date or system default date. FrozenClock is configured by ClockManagementController.
If you want to freeze the bean validator reference Clock instance, you can call the freeze api.
```shell script
curl -X PUT "http://localhost:6001/clock/freeze" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"newDate\": \"2020-12-31T18:10:29.574Z\"}"
```

Or if you want to unfreeze, you can call unfreeze;
```shell script
curl -X PUT "http://localhost:6001/clock/unfreeze" -H  "accept: */*"
```

**ClockManagementController**
```java
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
```

After the method call it publish a RefreshEvent. And then the beans that has annotation @RefreshScope is reinitialized by the spring.
And then new date which is sent by us is set as ClockProvider reference date.

Anymore we can send a request the our demo api that in DemoController without any problem.

```java
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
```
