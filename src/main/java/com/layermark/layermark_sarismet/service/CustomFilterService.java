package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.exception.bad_request.BadRequestException;
import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import com.layermark.layermark_sarismet.security.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomFilterService {
    private CustomMessageSource messageSource;
    private static final String USER_NAMES_DO_NOT_MATCH = "error.usernames-do-not-match";
    private static final String UNABLE_TO_GET_TOKEN = "error.token-not-taken";
    private static final String TOKEN_EXPIRED = "error.token-expired";
    private static final String TOKEN_NOT_VALID = "error.token-not-valid";

    @Autowired
    private JWTUtility jwtTokenUtil;

    @Autowired
    public void setMessageSource(CustomMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public void checkUserNameMatch(HttpServletRequest request,String username){
        final String requestTokenHeader = request.getHeader("Authorization");
        String usernameFromToken = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                usernameFromToken = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if(!username.equals(usernameFromToken)){
                    throw new BadRequestException(messageSource.getMessage(USER_NAMES_DO_NOT_MATCH));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                throw new BadRequestException(messageSource.getMessage(UNABLE_TO_GET_TOKEN));
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                throw new BadRequestException(messageSource.getMessage(TOKEN_EXPIRED));
            }
        }else{
            throw new BadRequestException(messageSource.getMessage(TOKEN_NOT_VALID));
        }
    }
}
