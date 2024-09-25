package com.KoiHealthService.Koi.demo.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse <T>{
    int code = 200;
    String message;
    T result;
}
