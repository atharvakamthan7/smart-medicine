package com.smartmedicine.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name = "pharmacies", indexes = @Index(columnList = "workspaceId"))
public class Pharmacy {
    @Id public String id;
    @JsonIgnore public String workspaceId;
    @NotBlank @Size(max = 100) public String name;
    @NotBlank @Pattern(regexp = "^[0-9+()\\-\\s]{5,25}$", message = "enter a valid phone number") public String phone;
    @NotBlank @Size(max = 160) public String area;
    @Size(max = 160) public String notes;
}
