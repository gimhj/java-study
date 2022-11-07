package tutoring.Project.member.entity;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {

    @NotEmpty(message = "필수 입력 값 입니다.")
    private String email;
    @NotEmpty(message = "필수 입력 값 입니다.")
    private String password;
}
