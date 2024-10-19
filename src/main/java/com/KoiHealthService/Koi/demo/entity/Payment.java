package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     String paymentId;

    @Column(nullable = false)
    String orderType;

    @Column(nullable = false)
    Long amountValue;

    @Column(nullable = false)
     String vnp_CreateDate;

    @Column(nullable = false)
    String vnp_ExpireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
     User user;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String name;
//  Nếu cần, có thể thêm danh sách các Item liên quan đến Payment
     @OneToMany(mappedBy = "payment")
     List<Item> items ;
}
