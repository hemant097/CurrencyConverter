package com.example.week4hw.week4hwcurrconv.Mapper;

import com.example.week4hw.week4hwcurrconv.DTO.StatusDto;
import com.example.week4hw.week4hwcurrconv.Entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public StatusDto toDto(Status status){
        Long remaining = status.getQuotas().getMonth().getRemaining();
        return StatusDto.of(status.getAccountId(),remaining);
    }
}
