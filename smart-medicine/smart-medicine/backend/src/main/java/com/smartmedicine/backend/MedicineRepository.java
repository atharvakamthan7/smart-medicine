package com.smartmedicine.backend;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    List<Medicine> findByWorkspaceIdOrderByTimeAsc(String ws);
    Optional<Medicine> findByIdAndWorkspaceId(String id, String ws);
    boolean existsByIdAndWorkspaceId(String id, String ws);
    long countByWorkspaceId(String ws);
    void deleteByWorkspaceId(String ws);
}
