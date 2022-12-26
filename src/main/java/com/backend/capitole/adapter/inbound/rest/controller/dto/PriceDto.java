package com.backend.capitole.adapter.inbound.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceDto {
    private String productId;
    private String brandId;
    private String priceList;
    private String startDate;
    private String endDate;
    private String currency;
    private Double price;
}
