package com.smartmedicine.backend;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoseLogRepository extends JpaRepository<DoseLog, String> {
    List<DoseLog> findByWorkspaceIdAndDate(String ws, String date);
    boolean existsByWorkspaceIdAndMedicineIdAndDateAndTime(String ws, String medicineId, String date, String time);
    void deleteByWorkspaceIdAndMedicineId(String ws, String medicineId);
    void deleteByWorkspaceId(String ws);
}
