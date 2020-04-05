package ie.projects.phase1.server.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter(filterName="delay", urlPatterns="/*")
public class DelayFilter implements Filter {
    private String encoding;
    public DelayFilter() {
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e1){
            e1.printStackTrace();
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}