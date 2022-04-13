package site.metacoding.blogv3.config.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import site.metacoding.blogv3.config.auth.LoginUser;

public class SessionIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        boolean isLogin = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (isLogin) { // mustache에서 사용하기위해 담은것.
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            HttpSession session = request.getSession();
            session.setAttribute("principal", loginUser.getUser());
        }

        return true;
    }

}
