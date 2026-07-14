package org.example.basicboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String userId;
    private String content;
}
