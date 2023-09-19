package com.zeniapp.segmentmiddleware.guards;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
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
import com.zeniapp.segmentmiddleware.enums.AccountRole;
import com.zeniapp.segmentmiddleware.exceptions.InvalidApiKeyException;
import com.zeniapp.segmentmiddleware.exceptions.UnauthorizedException;
import com.zeniapp.segmentmiddleware.utils.JwtUtils;
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
public class AuthGuard extends OncePerRequestFilter {
    @Getter
    private String apiKeyHeaderName;

    @Getter
    private String apiKeyHeaderValue;
    
    @Getter
    private String jwtSecret;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        RequestMatcher matcher = new NegatedRequestMatcher(new AntPathRequestMatcher("/api/v1/accounts/**"));
        return matcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader("Authorization");

            if (jwt != null && jwt.length() > 0) {
                Map<String, Object> claims = JwtUtils.decodeJwt(jwt, this.jwtSecret, new String[] {
                    "accountId", "username", "email", "role"
                });

                String role = (String) claims.get("role");
                
                if (!role.equals(AccountRole.ADMIN.toString())) {
                    throw new UnauthorizedException();
                }

                claims.forEach((key, value) -> request.setAttribute(jwt, response));

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
            AuthGuard.log.error("error occurred during the API authentication and authorization");
            AuthGuard.log.error("error message is " + exception.getMessage());

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
                errorResponseDto.setMessages(Arrays.asList(new String[] { "jwt expired" }));

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
