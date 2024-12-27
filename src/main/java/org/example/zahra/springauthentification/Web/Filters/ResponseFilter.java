package org.example.zahra.springauthentification.Web.Filters;


import org.springframework.stereotype.Component;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class ResponseFilter implements Filter {

    private static final List<String> SWAGGER_PATHS = Arrays.asList(
            "/api-docs",
            "/swagger-ui",
            "swagger-ui.html"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        boolean isSwaggerPath = SWAGGER_PATHS.stream()
                .anyMatch(path::contains);

        if (!isSwaggerPath) {
            chain.doFilter(request, response);
            return;
        }

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
                (HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        String responseBody = new String(responseWrapper.getContentAsByteArray(),
                StandardCharsets.UTF_8);

        if (!responseBody.isEmpty()) {
            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setStatus(responseWrapper.getStatus() < 400 ? "SUCCESS" : "ERROR");
            apiResponse.setStatusCode(responseWrapper.getStatus());
            apiResponse.setMessage(responseWrapper.getStatus() < 400 ?
                    "Request processed successfully" : "Error processing request");
            apiResponse.setData(responseBody.isEmpty() ? null :
                    new ObjectMapper().readValue(responseBody, Object.class));

            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        }

        responseWrapper.copyBodyToResponse();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}