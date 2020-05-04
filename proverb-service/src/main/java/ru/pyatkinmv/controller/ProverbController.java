package ru.pyatkinmv.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pyatkinmv.service.ProverbService;

@Controller
@RequiredArgsConstructor
public class ProverbController {

    private final ProverbService proverbService;

    @GetMapping("/supply")
    public ResponseEntity<String> supply() {
        return new ResponseEntity<>(proverbService.supply().toString(), HttpStatus.OK);
    }
}
