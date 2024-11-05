package com.KoiHealthService.Koi.demo.dto.request.news;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsUpdateRequest {
    String title;
    String newsContent;
    String authorId;
    String image;
}
