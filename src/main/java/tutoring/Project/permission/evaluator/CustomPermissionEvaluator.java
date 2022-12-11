package tutoring.Project.permission.evaluator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import tutoring.Project.member.entity.Member;
import tutoring.Project.permission.policy.Policy;

@RequiredArgsConstructor
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final Map<Class<?>, Policy> policies;

    @Override
    public boolean hasPermission(
        Authentication authentication,
        Object targetDomainObject,
        Object permission
    ) {

        return false;
    }

    @Override
    public boolean hasPermission(
        Authentication authentication,
        Serializable targetId,
        String targetType,
        Object permission
    ) {
        if ((authentication == null)
            || (targetId == null)
            || (targetType == null)
            || !(permission instanceof String)) {

            return false;
        }
        try {
            Class<?> targetClass = Class.forName(targetType);
            Long convertTargetId = (Long) targetId;
            String methodName = permission.toString();

            Policy policy = policies.get(targetClass);

            if (policy == null) {

                return false;
            }

            try {
                System.out.println(authentication);
                System.out.println(authentication.getPrincipal());
                System.out.println(authentication.getPrincipal().getClass());
                System.out.println(authentication.getPrincipal() instanceof Member);
                System.out.println(authentication.getPrincipal() instanceof Authentication);
                System.out.println(authentication instanceof Authentication);

                Method method = policy.getClass().getMethod(
                    methodName,
                    authentication.getPrincipal().getClass(),
                    Long.class
                );

                // TODO: 2022/12/03 fix error(object is not an instance of declaring class)
                return (Boolean) method.invoke(
                    methodName,
                    authentication.getPrincipal(),
                    convertTargetId
                );
            } catch (NoSuchMethodException e) {
                return false;
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}