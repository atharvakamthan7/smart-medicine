package com.smartmedicine.backend;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoDataAvailabilityProvider implements AvailabilityProvider {
    @Override
    public Result lookup(String medicineName) {
        return new Result(List.of(), "No verified pharmacy stock data source is connected yet, so availability for \"" + medicineName + "\" is unknown. Please call your pharmacy to confirm.");
    }
}
