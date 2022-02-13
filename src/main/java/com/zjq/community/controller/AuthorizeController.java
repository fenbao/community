package com.zjq.community.controller;

import com.zjq.community.dto.AccessTokenDTO;
import com.zjq.community.dto.GithubUser;
import com.zjq.community.model.User;
import com.zjq.community.provider.GithubProvider;
import com.zjq.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @date 2022-01-14 16:29
 */
@Controller
public class AuthorizeController {

    @Autowired
    private UserService userService;

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

     @GetMapping("/callback")
    // 处理github登录认证后回传的参数code和state（state在这里没起作用，不影响）
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) throws IOException {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser);
        if (githubUser != null && githubUser.getId()!=null) { //登陆成功就将信息保存在session中
            // HttpSession session = request.getSession();
            // session.setAttribute("user", githubUser); //直接将整个user对象放到session中
            User user = new User();
            // userid是设置的自增的所以不用管
            String token = UUID.randomUUID().toString(); //生成随机的token
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",user.getToken()));  //通过将token写入cookie，验证token的方式进行登录判断
            return "redirect:/";
        } else { //登录失败
            System.out.println("***********");
            return "redirect:/error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        // response删除cookie的方法
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

//
//    @RequestMapping("/callback")
//    @ResponseBody
//    public String test(){
//        System.out.println("-------------");
//        return "测试请求是否能跳转";
//    }
}
