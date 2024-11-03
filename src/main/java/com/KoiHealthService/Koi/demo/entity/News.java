package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "news")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String newsId;
    String title;
    String newsContent;

    @ManyToOne
    @JoinColumn(name = "veterinarianId")
    User author;

    
}
