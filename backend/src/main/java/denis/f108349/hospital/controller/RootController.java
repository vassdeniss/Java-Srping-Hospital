package denis.f108349.hospital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RootController {
    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello from reactive backend!");
    }
}
