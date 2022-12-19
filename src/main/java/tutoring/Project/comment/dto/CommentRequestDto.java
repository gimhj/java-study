package tutoring.Project.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CommentRequestDto {

    @NotNull
    private Long boardId;
    @NotBlank(message = "필수 입력 값 입니다.")
    private String content;
    private Boolean isLock;

    @Nullable
    private Long parentId;
}
