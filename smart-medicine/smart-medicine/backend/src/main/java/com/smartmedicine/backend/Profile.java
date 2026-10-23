package com.smartmedicine.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name = "user_profiles")
public class Profile {
    @Id @JsonIgnore public String workspaceId;
    @Size(max = 70) public String name;
    @Email @Size(max = 120) public String email;
    @Size(max = 25) public String phone;
}
