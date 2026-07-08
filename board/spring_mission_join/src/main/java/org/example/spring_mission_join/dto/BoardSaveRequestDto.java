package org.example.spring_mission_join.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String userId;
    private String title;
    private String content;
    private MultipartFile file;
}
