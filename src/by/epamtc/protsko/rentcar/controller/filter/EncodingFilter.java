package by.epamtc.protsko.rentcar.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final String ENCODING_PARAMETER_NAME = "encoding";
    private static final String ENCODING_VALUE = "UTF-8";
    private static final String CONTENT_TYPE_VALUE = "text/html; charset=UTF-8";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter(ENCODING_PARAMETER_NAME);
        if (encoding == null) {
            encoding = ENCODING_VALUE;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (null == servletRequest.getCharacterEncoding()) {
            servletRequest.setCharacterEncoding(encoding);
        }
        servletResponse.setContentType(CONTENT_TYPE_VALUE);
        servletResponse.setCharacterEncoding(encoding);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
