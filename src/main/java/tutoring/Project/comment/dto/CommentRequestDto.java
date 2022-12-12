package tutoring.Project.comment.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDto {

    @NotNull
    private Long boardId;
    @NotEmpty(message = "필수 입력 값 입니다.")
    private String content;
    private Boolean isLock;
}
