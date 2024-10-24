package com.KoiHealthService.Koi.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "veterinarian_profiles")
public class VeterinarianProfile {

    @JsonProperty("username") // Include username in the response
    public String getUsername() {
        return user.getUsername();
    } //có thể dùng json property để trả về thêm 1 field tùy thích

    @JsonProperty("firstname")
    public String getFirstname() {
        return user.getFirstname();
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return user.getLastname();
    }



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String veterinarianProfilesId;

    // Many-to-One với User (nhiều profile có thể thuộc về một User)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonBackReference
    User user;

    // Many-to-One với VeterinarianSchedule (nhiều profile có thể liên kết với một lịch)
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "veterinarian_schedule_id", referencedColumnName = "scheduleId")
        @JsonBackReference
        VeterinarianSchedule veterinarianSchedule;
}



//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String veterinarianProfileId;
//
//    // Many-to-One relationship with User
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "userId")
//    User user;
//
//    // Many-to-One relationship with FishSpecialty
//    @ManyToOne
//    @JoinColumn(name = "fish_specialty_id", referencedColumnName = "fishSpecialtyId")
//    FishSpecialty fishSpecialty;
