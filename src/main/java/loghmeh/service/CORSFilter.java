package loghmeh.service;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName="cors", urlPatterns="/*")
public class CORSFilter implements Filter {
    private String encoding;
    public CORSFilter() {
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        if (servletRequest.getCharacterEncoding() == null)
            servletRequest.setCharacterEncoding(encoding);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());



        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","content-type");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","Authorization");
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        servletResponse.setContentType("application/json; charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
        chain.doFilter(request, servletResponse);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        encoding = fConfig.getInitParameter("requestEncoding");
        if (encoding == null) encoding = "UTF-8";
    }
}