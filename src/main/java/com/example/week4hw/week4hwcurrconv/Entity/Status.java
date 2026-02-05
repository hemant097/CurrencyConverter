package com.example.week4hw.week4hwcurrconv.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Status {

    @JsonProperty("account_id")
    private Long accountId;

    private Quotas quotas;
}
