package ru.pyatkinmv.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pyatkinmv.model.Proverb;
import ru.pyatkinmv.service.ProverbService;

@Controller
@RequiredArgsConstructor
public class ProverbController {

    private final ProverbService proverbService;

    @GetMapping(value = "/supply")
    public ResponseEntity<Proverb> supply() {
        return new ResponseEntity<>(proverbService.supply(), HttpStatus.OK);
    }
}
