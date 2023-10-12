package com.zeniapp.segmentmiddleware.guards;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.gson.Gson;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.entities.Session;
import com.zeniapp.segmentmiddleware.enums.AccountRole;
import com.zeniapp.segmentmiddleware.exceptions.InvalidApiKeyException;
import com.zeniapp.segmentmiddleware.exceptions.UnauthorizedException;
import com.zeniapp.segmentmiddleware.services.SessionService;
import com.zeniapp.segmentmiddleware.utils.ApiKeyAuthentication;
import com.zeniapp.segmentmiddleware.utils.JwtAuthentication;
import com.zeniapp.segmentmiddleware.utils.JwtUtils;
import com.zeniapp.segmentmiddleware.utils.SessionUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AdminAuthGuard extends OncePerRequestFilter {
    @Getter
    private String apiKeyHeaderName;

    @Getter
    private String apiKeyHeaderValue;
    
    @Getter
    private String jwtSecret;

    @Getter
    private Long jwtDuration;

    @Autowired
    private SessionService sessionService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        RequestMatcher adminPathMatcher = new NegatedRequestMatcher(new AntPathRequestMatcher("/api/v1/admin/**"));
        return adminPathMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader("Authorization");

            if (jwt != null && jwt.length() > 0) {
                Map<String, Object> claims = JwtUtils.decodeJwt(jwt, this.jwtSecret, new String[] { "sessionId" });

                String sessionId = (String) claims.get("sessionId");

                Session session = SessionUtils.checkSession(this.sessionService, sessionId, this.jwtDuration);

                String role = session.getAccount().getRole();
                
                if (!role.equals(AccountRole.ADMIN.toString())) {
                    throw new UnauthorizedException();
                }

                claims.forEach((key, value) -> request.setAttribute(key, value));
                request.setAttribute("accountId", session.getAccount().getId());

                Authentication authentication = new JwtAuthentication(jwt, AuthorityUtils.NO_AUTHORITIES);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else {
                String apiKey = request.getHeader(this.apiKeyHeaderName);

                if (apiKey == null || !apiKey.equals(this.apiKeyHeaderValue)) {
                    throw new InvalidApiKeyException();
                }

                Authentication authentication = new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception exception) {
            AdminAuthGuard.log.error("error occurred during the API authentication and authorization");
            AdminAuthGuard.log.error("error message is " + exception.getMessage());

            Integer httpStatus;
            ErrorResponseDto body;

            if (exception instanceof InvalidApiKeyException) {
                httpStatus = HttpServletResponse.SC_FORBIDDEN;
                body = ((InvalidApiKeyException) exception).getErrorResponseDto();
            }
            else if (exception instanceof UnauthorizedException) {
                httpStatus = HttpServletResponse.SC_UNAUTHORIZED;
                body = ((UnauthorizedException) exception).getErrorResponseDto();
            }
            else if (exception instanceof ExpiredJwtException) {
                httpStatus = HttpServletResponse.SC_FORBIDDEN;

                ErrorResponseDto errorResponseDto = new ErrorResponseDto();
                errorResponseDto.setError(true);
                errorResponseDto.setName(HttpStatus.FORBIDDEN.getReasonPhrase());
                errorResponseDto.setMessages(Arrays.asList(new String[] { "session expired" }));

                body = errorResponseDto;
            }
            else if (exception instanceof SignatureException) {
                httpStatus = HttpServletResponse.SC_FORBIDDEN;

                ErrorResponseDto errorResponseDto = new ErrorResponseDto();
                errorResponseDto.setError(true);
                errorResponseDto.setName(HttpStatus.FORBIDDEN.getReasonPhrase());
                errorResponseDto.setMessages(Arrays.asList(new String[] { "wrong jwt signature" }));

                body = errorResponseDto;
            }
            else {
                httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                body = new ErrorResponseDto();
                body.setError(true);
                body.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                body.setMessages(Arrays.asList(new String[] { "generic error occurred" }));
            }

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(httpStatus);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            
            String json = new Gson().toJson(body);
            ServletOutputStream httpServletResponseOutputStream = httpServletResponse.getOutputStream();
            httpServletResponseOutputStream.print(json);
            httpServletResponseOutputStream.close();
        }
        
        filterChain.doFilter(request, response);
    }
}
