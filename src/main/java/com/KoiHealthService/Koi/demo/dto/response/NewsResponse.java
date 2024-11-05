package com.KoiHealthService.Koi.demo.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsResponse {
    String newsId;
    String title;
    String newsContent;
    String authorId;
    String image;
}
