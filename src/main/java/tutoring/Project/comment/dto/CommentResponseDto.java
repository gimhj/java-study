package tutoring.Project.comment.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private Boolean isLock;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
