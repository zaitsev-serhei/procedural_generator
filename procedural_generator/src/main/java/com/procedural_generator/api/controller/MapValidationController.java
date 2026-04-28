package com.procedural_generator.api.controller;


import com.procedural_generator.api.dto.response.ValidationResultDto;
import com.procedural_generator.domain.model.MapGeneration;
import com.procedural_generator.validation.ValidationResultMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maps")
public class MapValidationController {

    private final ValidationResultMapper validator;

    public MapValidationController(ValidationResultMapper validator) {
        this.validator = validator;
    }

    @PostMapping("/validate")
    public ValidationResultDto validate(@RequestBody MapGeneration map) {
        return validator.validate(map);
    }
}
