package com.example.demo.interseptor;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.config.JwtGenerator;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProductServiceInterceptor implements HandlerInterceptor {

    private UserRepository userRepository;

    private JwtGenerator jwtGenerator;

    @Autowired
    public ProductServiceInterceptor(UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Cookie[] cookies = request.getCookies();
        HashMap<String, String> map = new HashMap<String, String>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }
        if (map.containsKey("token")) {
            String token = jwtGenerator.parseJwt(map.get("token"));
            if (token != null) {
                Optional<User> user = userRepository.findById(token);
                if (user.isPresent())
                    request.setAttribute("user", user.get());
                else
                    request.setAttribute("user", null);
            } else
                request.setAttribute("user", null);
        } else
            request.setAttribute("user", null);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
    }

}
