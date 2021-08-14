package com.example.module2.web.dtos.activityDtos;

import com.example.module2.entities.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mappings({
            @Mapping(target="name", source="activityRequestDto.name"),
            @Mapping(target="duration", source="activityRequestDto.duration"),
            @Mapping(target="price", source="activityRequestDto.price")
    })
    Activity mapActivityDtoToActivity(ActivityRequestDto activityRequestDto);

    @Mappings({
            @Mapping(target="name", source="activity.name"),
            @Mapping(target="duration", source="activity.duration"),
            @Mapping(target="price", source="activity.price")
    })
    ActivityResponseDto mapActivityToActivityResponseDto(Activity activity);

    List<ActivityResponseDto> mapActivityListToActivityResponseDtoList(List<Activity> users);
}
