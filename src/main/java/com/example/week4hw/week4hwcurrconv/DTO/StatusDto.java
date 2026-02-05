package com.example.week4hw.week4hwcurrconv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StatusDto {

    private Long accountId;

    private Long remaining;

    public static StatusDto of(Long accountId, Long remaining){
        return new StatusDto(accountId,remaining);
    }
}
