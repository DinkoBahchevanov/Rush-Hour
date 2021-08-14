package com.example.module2.services;

import com.example.module2.entities.Activity;
import com.example.module2.exceptions.activities.ActivityAlreadyExistsException;
import com.example.module2.exceptions.activities.ActivityNotFoundException;
import com.example.module2.repositories.ActivityRepository;
import com.example.module2.web.dtos.activityDtos.ActivityMapper;
import com.example.module2.web.dtos.activityDtos.ActivityRequestDto;
import com.example.module2.web.dtos.activityDtos.ActivityResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityServiceImpl implements ActivityService {

    public final ActivityRepository activityRepository;
    public final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityResponseDto createActivity(ActivityRequestDto activityRequestDto) {
        Activity activity = activityMapper.mapActivityDtoToActivity(activityRequestDto);
        activityRepository.save(activity);

        return activityMapper.mapActivityToActivityResponseDto(activity);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteActivityById(int activityId) {
        if (!activityRepository.findById(activityId).isPresent()){
            throw new ActivityNotFoundException(activityId);
        }
        activityRepository.deleteById(activityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Set<ActivityResponseDto> getAllActivities() {
        List<ActivityResponseDto> activityResponseDtos = activityMapper
                .mapActivityListToActivityResponseDtoList(activityRepository.findAll());
        return new HashSet<>(activityResponseDtos);
    }

    @Override
    public ActivityResponseDto updateActivityById(int activityId, ActivityRequestDto activityRequestDto) {
        Activity activity = activityRepository.getById(activityId);
        if (activity.getName() == null) {
            throw new ActivityNotFoundException(activityId);
        }
        activity = activityMapper.mapActivityDtoToActivity(activityRequestDto);
        activity.setId(activityId);
//        activity.setName(activityRequestDto.getName());
//        activity.setDuration(activityRequestDto.getDuration());
//        activity.setPrice(activityRequestDto.getPrice());
        return activityMapper.mapActivityToActivityResponseDto(activityRepository.save(activity));
    }
}
