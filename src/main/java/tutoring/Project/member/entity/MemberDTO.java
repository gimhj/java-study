package tutoring.Project.member.entity;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {

    @NotEmpty(message = "필수 입력 값 입니다.")
    private String name;
    private String nickname;
    @NotEmpty(message = "필수 입력 값 입니다.")
    private String email;
    @NotEmpty(message = "필수 입력 값 입니다.")
    private String password;
    private String phone;
    private LocalDate birth;

    private String city;
    private String street;
    private String zipcode;
}
