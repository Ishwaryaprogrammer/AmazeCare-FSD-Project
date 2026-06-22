package com.amaze.mapper;

import com.amaze.dto.AvailabilityDto;
import com.amaze.dto.AvailabilityReqDto;
import com.amaze.dto.AvailabilityResDto;
import com.amaze.model.Availability;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvailabilityMapper {


    public static Availability mapDtoToEntity(@Valid AvailabilityReqDto dto) {
        Availability availability=new Availability();
        availability.setDay(dto.day());
        availability.setStartTime(dto.startTime());
        availability.setEndTime(dto.endTime());
        availability.setDuration(dto.duration());
        return availability;
    }

    public Availability mapDtoToExistingEntity(Availability availability, @Valid AvailabilityReqDto dto) {
        availability.setDay(dto.day());
        availability.setStartTime(dto.startTime());
        availability.setEndTime(dto.endTime());
        availability.setDuration(dto.duration());
        return availability;
    }

    public static AvailabilityResDto mapPageToDto(Page<Availability> availabilityPage) {
        List<AvailabilityDto> availabilityDtos = new ArrayList<>();
        for (Availability availability : availabilityPage.getContent()) {
            AvailabilityDto availabilityDto = mapEntityToAvailabiltiyDto(availability);
            availabilityDtos.add(availabilityDto);
        }
        return new AvailabilityResDto(
                availabilityPage.getTotalPages(),
                availabilityPage.getTotalElements(),
                availabilityDtos
        );

    }


    public static AvailabilityDto mapEntityToAvailabiltiyDto(Availability availability){
        return new AvailabilityDto(
                availability.getId(),
                availability.getDay(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getDuration()
        );
    }
}
