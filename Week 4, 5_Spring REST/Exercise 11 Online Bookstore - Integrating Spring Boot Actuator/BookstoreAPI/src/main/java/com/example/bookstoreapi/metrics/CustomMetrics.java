package com.example.bookstoreapi.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CustomMetrics {

    private final MeterRegistry meterRegistry;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerMetrics() {
        meterRegistry.counter("custom.book.view.count").increment();
        meterRegistry.gauge("custom.book.available.count", 100);
    }
}

