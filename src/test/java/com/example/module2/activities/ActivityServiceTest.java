package com.example.module2.activities;

import com.example.module2.entities.Activity;

import com.example.module2.repositories.ActivityRepository;
import com.example.module2.repositories.UserRepository;
import com.example.module2.services.*;
import com.example.module2.web.dtos.activityDtos.ActivityMapper;
import com.example.module2.web.dtos.activityDtos.ActivityMapperImpl;
import com.example.module2.web.dtos.activityDtos.ActivityRequestDto;
import com.example.module2.web.dtos.activityDtos.ActivityResponseDto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ActivityServiceTest {

    private ActivityRepository activityRepository;
    private ActivityService underTest;
    private Activity activity;

    @BeforeEach
    void setUp() {
        activityRepository = mock(ActivityRepository.class);
        underTest = new ActivityServiceImpl(activityRepository, new ActivityMapperImpl());

        this.activity = new Activity("football", 2.0, 7.50, new HashSet<>());

    }

    @Test
    void getAllActivitiesShouldBeZero() {
        when(activityRepository.findAll()).thenReturn(new ArrayList<>());
        Set<ActivityResponseDto> activityResponseDtos = this.underTest.getAllActivities();
        assertThat(activityResponseDtos.size()).isEqualTo(0);
    }

    @Test
    void getAllActivitiesShouldReturnAllActivities() {
        List<Activity> list = new ArrayList<>();
        Activity activityOne = new Activity("dinko", 1.30, 9.50, new HashSet<>());
        Activity activityTwo = new Activity("boja", 2, 2.22, new HashSet<>());
        Activity activityThree = new Activity("mama", 2.30, 3.12, new HashSet<>());

        list.add(activityOne);
        list.add(activityTwo);
        list.add(activityThree);

        when(activityRepository.findAll()).thenReturn(list);

        //test
        Set<ActivityResponseDto> actualActivityList = underTest.getAllActivities();

        assertEquals(3, actualActivityList.size());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void createActivity() {
        ActivityRequestDto activityRequestDto = validActivityRequestDto();
        Activity activity = new Activity("football", 2.0, 7.50, new HashSet<>());
        //when
        underTest.createActivity(activityRequestDto);
        //then
        ArgumentCaptor<Activity> activityArgumentCaptor = ArgumentCaptor.forClass(Activity.class);

        verify(activityRepository).save(activityArgumentCaptor.capture());

        Activity capturedActivity = activityArgumentCaptor.getValue();

        assertThat(capturedActivity.getName()).isEqualTo(this.activity.getName());
    }

    @Test
    void deleteActivity() {
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        underTest.deleteActivityById(1);
        verify(activityRepository).deleteById(1);
    }

    private ActivityRequestDto validActivityRequestDto() {
        return new ActivityRequestDto() {{
            setName("football");
            setDuration(2);
            setPrice(7.50);
        }};
    }


}
