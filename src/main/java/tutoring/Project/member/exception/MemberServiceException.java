package tutoring.Project.member.exception;

public class MemberServiceException extends RuntimeException {

    public MemberServiceException() {

    }

    public MemberServiceException(String message) {
        super(message);
    }

    public String getServiceName() {
        return "Member";
    }
}
