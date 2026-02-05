package com.example.week4hw.week4hwcurrconv.Entity;

import lombok.Data;

@Data
public class QuotaDetail {

    private Long total;
    private Long used;
    private Long remaining;
}