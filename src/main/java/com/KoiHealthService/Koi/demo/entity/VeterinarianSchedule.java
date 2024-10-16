package com.KoiHealthService.Koi.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "veterinarian_schedules")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarianSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String scheduleId;
    String availableDate;
    LocalTime startTime;
    LocalTime endTime;
    
    // Many-to-many relationship with User (as Veterinarians)
    @ManyToMany(mappedBy = "veterinarianSchedules", fetch = FetchType.LAZY)   //map cái biến veterinarianSchedules bên User
    @JsonBackReference
    private List<User> veterinarians;
    
}
