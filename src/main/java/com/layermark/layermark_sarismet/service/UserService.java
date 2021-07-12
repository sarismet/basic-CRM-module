package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.exception.bad_request.UserEmailNotVerifiedException;
import com.layermark.layermark_sarismet.exception.bad_request.UserIsAlreadyRegisteredException;
import com.layermark.layermark_sarismet.exception.not_found.ResourceNotFoundException;
import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import com.layermark.layermark_sarismet.model.UserRole;
import com.layermark.layermark_sarismet.model.*;

import com.layermark.layermark_sarismet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomMessageSource messageSource;
    private EmailService emailService;

    private static final String USER_IS_ALREADY_REGISTERED = "error.user-is-already-registered";
    private static final String USER_IS_SUCCESSFULLY_REGISTERED = "success.user-is-successfully-registered";
    private static final String TOKEN_EXPIRED = "error.token-expired";
    private static final String EMAIL_IS_SENT = "success.verification-email-is-sent.Check-your-email";
    private static final String USER_NOT_FOUND = "error.user-not-found";
    private static final String USER_IS_ALREADY_VERIFIED = "error.user-is-already-verified";
    private static final String USER_EMAIL_IS_NOT_VERIFIED = "error.user-email-is-not-verified";
    private static final String NEW_PASSWORD_DOES_NOT_MATCH = "error.password-does-not-match";

    @Value("${spring.token.expired.time}")
    private long tokenExpiredTime;

    @Autowired
    public void setMessageSource(CustomMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username);
        System.out.println("VERIFIED MI "+user.getVerified());
        if (user == null) {
            throw new ResourceNotFoundException(messageSource.getMessage(USER_NOT_FOUND));
        }
        if (!user.getVerified()) {
            throw new UserEmailNotVerifiedException(messageSource.getMessage(USER_EMAIL_IS_NOT_VERIFIED));
        }
        List<SimpleGrantedAuthority> roles = null;
        if (user.getRole().equals("ADMIN")) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else if (user.getRole().equals("BASIC")) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(user.getUsername(),user.getPassword(),roles);
    }

    public CustomUser save(CustomUserDTO user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            CustomUser newUser = new CustomUser();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole(UserRole.ADMIN.name());
            newUser.setEmail(user.getEmail());
            newUser.setVerified(false);
            return userRepository.save(newUser);
        }
        else{
            throw new UserIsAlreadyRegisteredException(messageSource.getMessage(USER_IS_ALREADY_REGISTERED));
        }
    }

    public String registerUser(HttpServletRequest request, CustomUserDTO user) {
        this.save(user);
        emailService.sendEmail(request,user.getUsername());
        return messageSource.getMessage(EMAIL_IS_SENT);
    }

    public String verifyUser(CustomToken customToken){
        CustomUser customUser = userRepository.findByUsername(customToken.getUsername());
        if(customUser!=null){
            if(customUser.getVerified()){
                return messageSource.getMessage(USER_IS_ALREADY_VERIFIED);
            }
            String userID = customUser.getUserID();
            String username = customToken.getUsername();
            System.out.println(" username VERIFY "+username);
            System.out.println(" getTimestamp VERIFY "+customToken.getTimestamp());
            Date nw = new Date();
            long milliseconds = nw.getTime();
            if ((milliseconds - customToken.getTimestamp())>tokenExpiredTime){
                userRepository.deleteByUserID(userID);
                return messageSource.getMessage(TOKEN_EXPIRED);
            }
            customUser.setVerified(true);
            userRepository.save(customUser);
        }else{
            throw new UserIsAlreadyRegisteredException(messageSource.getMessage(USER_NOT_FOUND));
        }
        return messageSource.getMessage(USER_IS_SUCCESSFULLY_REGISTERED);
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest){
        CustomUser customUser = userRepository.findByUsername(changePasswordRequest.getUsername());
        if(customUser!=null){
            if(!customUser.getVerified()){
                return messageSource.getMessage(USER_EMAIL_IS_NOT_VERIFIED);
            }
            if(changePasswordRequest.getOldPassword().equals(customUser.getPassword())){
                customUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(customUser);
            }else{
                throw new UserIsAlreadyRegisteredException(messageSource.getMessage(NEW_PASSWORD_DOES_NOT_MATCH));
            }
        }else{
            throw new UserIsAlreadyRegisteredException(messageSource.getMessage(USER_NOT_FOUND));
        }
        return "";
    }
}
