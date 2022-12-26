package com.backend.capitole.core.application.port.inbound;

import com.backend.capitole.core.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceInPort {
    Price getPriceByDate(LocalDateTime date, Integer productId, Integer brandId);
}
