package by.openbanking.openbankingservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
public final class TestController {

    public static class InputModel {

        @Size(min = 10)
        public String data;
    }

    @RequestMapping(value = "/asdf",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<String> asdf(
            @Valid @RequestBody InputModel inputModel
    ) {
        return ResponseEntity.ok("OK");
    }
}
