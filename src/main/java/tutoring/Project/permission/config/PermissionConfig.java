package tutoring.Project.permission.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import tutoring.Project.permission.evaluator.CustomPermissionEvaluator;
import tutoring.Project.permission.policy.Policy;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class PermissionConfig extends GlobalMethodSecurityConfiguration {

    private final List<Policy> policies;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {

        DefaultMethodSecurityExpressionHandler expressionHandler =
            new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(initPermissionEvaluator());

        return expressionHandler;
    }

    private CustomPermissionEvaluator initPermissionEvaluator() {
        Map<Class<?>, Policy> map = new HashMap<>();
        for (Policy policy : policies) {
            map.put(policy.getTargetClass(), policy);
        }

        return new CustomPermissionEvaluator(map);
    }
}
