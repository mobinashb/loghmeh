package loghmeh.service.authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import loghmeh.configs.AuthenticationConfig;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "jwt", urlPatterns = "/*")
public class JWTFilter implements Filter {

    private final static String AUTH_HEADER_KEY = AuthenticationConfig.AUTHENTICATION_HEADER_KEY;
    private final static String AUTH_PREFIX_KEY = AuthenticationConfig.AUTHENTICATION_PREFIX_KEY;

    public JWTFilter(){

    }

    public void destroy(){

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if(isUnauthorizedURI(httpServletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String jwt = getBearerToken(httpServletRequest, httpServletResponse);
        if(jwt != null){
            try {
                String id = Authentication.verify(jwt);
                servletRequest.setAttribute("id", id);
                filterChain.doFilter(servletRequest, servletResponse);
            }
            catch (JWTVerificationException e1){
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                httpServletResponse.setContentLength(0);
            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private String getBearerToken(HttpServletRequest request, HttpServletResponse response){
        String authHeader = request.getHeader(AUTH_HEADER_KEY);
        if((authHeader != null) && (authHeader.startsWith(AUTH_PREFIX_KEY))) {
            return authHeader.substring(AUTH_PREFIX_KEY.length());
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }

    private boolean isUnauthorizedURI(HttpServletRequest request){
        String uri = request.getRequestURI();
        if(uri.equals("/v1/login") || (uri.equals("/v1/user") && request.getMethod().equals("POST"))){
            return true;
        }
        return false;
    }
}
