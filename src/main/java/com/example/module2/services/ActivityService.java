package com.example.module2.services;

import com.example.module2.web.dtos.activityDtos.ActivityRequestDto;
import com.example.module2.web.dtos.activityDtos.ActivityResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Set;

public interface ActivityService {

    ActivityResponseDto create(ActivityRequestDto activityRequestDto);

    ResponseEntity<HttpStatus> delete(int activityId);

    Set<ActivityResponseDto> getAll();

    ActivityResponseDto update(int activityId, ActivityRequestDto activityRequestDto);
}
