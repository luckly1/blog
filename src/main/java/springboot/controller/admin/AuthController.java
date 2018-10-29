package springboot.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springboot.constant.WebConst;
import springboot.controller.AbstractController;
import springboot.controller.helper.ExceptionHelper;
import springboot.dto.LogActions;
import springboot.exception.TipException;
import springboot.modal.bo.RestResponseBo;
import springboot.modal.vo.UserVo;
import springboot.service.ILogService;
import springboot.service.IUserService;
import springboot.util.Commons;
import springboot.util.MyUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:  d登陆控制
 * @Param:
 * @return:
 * @Author: xj
 * @Date: 2018/10/26 15:19
*/
@Controller
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class AuthController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private IUserService userService;

    @Resource
    private ILogService logService;

    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }


    /**
     * @Description: 登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "login")
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        Integer error_count = cache.get("login_error_count") == null ? 0 :cache.get("login_error_count");
        System.out.println(error_count);
        //20181025 xj 当错误次数达到3次时，直接返回信息,返回时间
        if(error_count>=3){
            return RestResponseBo.fail("您输入密码已经错误超过3次，请稍后尝试");
        }
        try {
            UserVo userVo = userService.login(username, password);
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userVo);
            // 设置12小时的cookie
            MyUtils.setCookie(response, userVo.getUid());
            logService.insertLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), userVo.getUid());

        } catch (Exception e) {
            error_count = null == error_count ? 1 : error_count + 1;
            if (error_count == 3) {
                cache.set("login_error_count", error_count, 2 * 60);
                return RestResponseBo.fail("您输入密码已经错误超过3次，请2分钟后尝试");
            }
            cache.set("login_error_count", error_count);
            String msg = "登录失败";
            return ExceptionHelper.handlerException(logger, msg, e);
        }
        return RestResponseBo.ok();
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            response.sendRedirect(Commons.site_login());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("注销失败", e);
        }
    }
}
