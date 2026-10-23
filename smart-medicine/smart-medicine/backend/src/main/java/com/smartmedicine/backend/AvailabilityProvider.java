package com.smartmedicine.backend;

import java.util.List;
import java.util.Map;

/** Plug in a real pharmacy data source by implementing this interface (and removing NoDataAvailabilityProvider). */
public interface AvailabilityProvider {
    record Result(List<Map<String, Object>> results, String message) {}
    Result lookup(String medicineName);
}
