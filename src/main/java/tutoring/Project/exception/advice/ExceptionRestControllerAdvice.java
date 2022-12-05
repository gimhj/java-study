package tutoring.Project.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tutoring.Project.exception.ExceptionResponse;
import tutoring.Project.member.exception.MemberServiceException;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    // TODO: 2022/12/04 모든 ServiceException 에서 사용할 수 있도록 변경
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ExceptionResponse getExceptionResult(MemberServiceException e) {

        return new ExceptionResponse(
            HttpStatus.CONFLICT.value(),
            e.getServiceName(),
            e.getMessage()
        );
    }
}
