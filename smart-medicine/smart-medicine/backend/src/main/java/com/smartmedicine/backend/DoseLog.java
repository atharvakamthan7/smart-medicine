package com.smartmedicine.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name = "dose_logs", indexes = @Index(columnList = "workspaceId,log_date"))
public class DoseLog {
    @Id @JsonIgnore public String id;
    @JsonIgnore public String workspaceId;
    @NotBlank public String medicineId;
    @NotBlank @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "must be yyyy-MM-dd") @Column(name = "log_date") public String date;
    @NotBlank @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "must be HH:mm") @Column(name = "dose_time") public String time;
}
