package com.example.module2.web.controllers;

import com.example.module2.services.ActivityService;
import com.example.module2.web.dtos.activityDtos.ActivityRequestDto;
import com.example.module2.web.dtos.activityDtos.ActivityResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public Set<ActivityResponseDto> getAllActivities() {
        return activityService.getAll();
    }

    @PostMapping
    public ActivityResponseDto createActivity(@Valid @RequestBody ActivityRequestDto activityRequestDto) {
        return activityService.create(activityRequestDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteActivity(@PathVariable("id") int activityId) {
        return activityService.delete(activityId);
    }

    @PutMapping("/{id}")
    ActivityResponseDto updateActivity(@PathVariable("id") int activityId, @RequestBody ActivityRequestDto activityRequestDto) {
        return activityService.update(activityId, activityRequestDto);
    }
}
