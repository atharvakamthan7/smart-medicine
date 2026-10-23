package com.smartmedicine.backend;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, String> {
    List<Pharmacy> findByWorkspaceIdOrderByNameAsc(String ws);
    Optional<Pharmacy> findByIdAndWorkspaceId(String id, String ws);
    long countByWorkspaceId(String ws);
    void deleteByWorkspaceId(String ws);
}
