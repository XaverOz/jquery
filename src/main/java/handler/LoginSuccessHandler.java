package handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    public final String userAuthority = "USER";
    public final String adminAuthority = "ADMIN";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if(authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals(adminAuthority))) {
            httpServletResponse.sendRedirect("/admin");
            return;
        }
        if(authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals(userAuthority))) {
            httpServletResponse.sendRedirect("/user");
        }
    }
}
