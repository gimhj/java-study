package tutoring.Project.member.exception;

public class AlreadyExistMemberException extends MemberServiceException {

    public AlreadyExistMemberException() {
        super();
    }

    public AlreadyExistMemberException(String message) {
        super(message);
    }

    @Override
    public String getServiceName() {
        return super.getServiceName();
    }
}
