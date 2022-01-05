package com.example.module2.services;

import com.example.module2.entities.Activity;
import com.example.module2.exceptions.activityExc.ActivityNotFoundException;
import com.example.module2.repositories.ActivityRepository;
import com.example.module2.web.dtos.activityDtos.ActivityMapper;
import com.example.module2.web.dtos.activityDtos.ActivityRequestDto;
import com.example.module2.web.dtos.activityDtos.ActivityResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityServiceImpl implements ActivityService{

    public final ActivityRepository activityRepository;
    public final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityResponseDto create(@Valid @RequestBody ActivityRequestDto activityRequestDto) {
        Activity activity = activityMapper.mapActivityDtoToActivity(activityRequestDto);
        return activityMapper.mapActivityToActivityResponseDto(activityRepository.save(activity));
    }

    @Override
    public ResponseEntity<HttpStatus> delete(int activityId) {
        if (!activityRepository.findById(activityId).isPresent()){
            throw new ActivityNotFoundException(activityId);
        }
        activityRepository.deleteById(activityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Set<ActivityResponseDto> getAll() {
        List<ActivityResponseDto> activityResponseDtos = activityMapper
                .mapActivityListToActivityResponseDtoList(activityRepository.findAll());
        return new HashSet<>(activityResponseDtos);
    }

    @Override
    public ActivityResponseDto update(int activityId, @Valid @RequestBody ActivityRequestDto activityRequestDto) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (!activity.isPresent()) {
            throw new ActivityNotFoundException(activityId);
        }
        Activity updatedActivity = activityMapper.mapActivityDtoToActivity(activityRequestDto);
        updatedActivity.setId(activityId);

        return activityMapper.mapActivityToActivityResponseDto(activityRepository.save(updatedActivity));
    }
}
