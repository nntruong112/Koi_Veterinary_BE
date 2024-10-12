//package com.KoiHealthService.Koi.demo.dto.response;
//
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class ApiResponse <T>{
//    @Builder.Default
//    int code = 200;
//    String message;
//    T result;
//}
package com.KoiHealthService.Koi.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Builder.Default
    int code = 200;
    String message;
    T result;

    // Method to set the result and return the ApiResponse object for chaining
    public ApiResponse<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
