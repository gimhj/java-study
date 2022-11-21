package tutoring.Project.board.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter @Setter
public class BoardDTO {

    @NotEmpty(message = "필수 입력 값 입니다.")
    private String title;

    @NotEmpty(message = "필수 입력 값 입니다.")
    private String content;

    private BoardState state;
    private BoardType type;

    @Nullable
    private LocalDateTime publishAt;
}
