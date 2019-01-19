package com.cuit.talent.utils.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cuit.talent.utils.valueobj.Message;
import com.cuit.talent.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    private static Logger log=LoggerFactory.getLogger(RequestInterceptor.class);
    //存储不需要拦截的url
    private static final String[] IGNORE_URI = {"/user/login","/error"};
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private Message message;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String servletPath=request.getServletPath();
        log.info("请求路径："+servletPath);
        //遍历数组确定该路径是否需要拦截
        for(String str:IGNORE_URI){
            if(servletPath.contains(str)){
                flag=true;
                log.info("该请求不需要拦截！");
            }
        }
        if (!flag) {
                String token = request.getHeader("Authorization");
                if (StringUtils.isEmpty(token)){
                    log.info("无Token");
                    message.setCode(0);
                    message.setMsg("无Token");
                    JSONObject object = (JSONObject) JSONObject.toJSON(message);
                    returnJson(response, object);
                    flag = false;
                }else {
                    Claims claims = jwtHelper.getClaimByToken(token);
                    if (StringUtils.isEmpty(claims)) {
                        log.info("Token错误或者过期");
                        message.setCode(0);
                        message.setMsg("Token错误或者Token过期");
                        JSONObject object = (JSONObject) JSONObject.toJSON(message);
                        returnJson(response, object);
                        flag = false;
                    } else {
                            log.info("Token 验证成功");
                            flag = true;
                    }
                }
        }
        return flag;
    }
    private void returnJson(HttpServletResponse response, JSONObject mes) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = null;
        try {
             writer = response.getWriter();
             writer.print(mes);
        }catch (Exception e){
            log.error("输出错误");
        }finally {
            if (writer != null){
                writer.close();
            }
        }

    }
}
