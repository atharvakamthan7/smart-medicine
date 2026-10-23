package com.smartmedicine.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name = "medicines", indexes = @Index(columnList = "workspaceId"))
public class Medicine {
    @Id public String id;
    @JsonIgnore public String workspaceId;
    @NotBlank @Size(max = 100) public String name;
    @NotBlank @Size(max = 70) public String person;
    @Size(max = 100) public String dose;
    @NotBlank @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "must be HH:mm") @Column(name = "remind_time") public String time;
    @Min(0) @Max(100000) @Column(name = "qty_left") public int qty;
    @Min(0) @Max(100000) @Column(name = "low_level") public int low;
    @Size(max = 300) public String notes;
}
