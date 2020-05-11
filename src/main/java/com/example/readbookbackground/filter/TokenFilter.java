package com.example.readbookbackground.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.readbookbackground.util.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = { "/*" }, filterName = "tokenFilter")
public class TokenFilter implements Filter {

    @Autowired
    private RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rep = (HttpServletResponse) servletResponse;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        rep.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        rep.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");


        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=utf-8");
        String token = req.getHeader("token");//header方式
        boolean isFilter = false;

        String method = ((HttpServletRequest) servletRequest).getMethod();
        if (method.equals("OPTIONS")) {
            rep.setStatus(HttpServletResponse.SC_OK);
        }else{
            if (null == token || token.isEmpty()) {
                String ss=((HttpServletRequest) servletRequest).getServletPath();
                if("/User/Login".equals(ss)){
                    isFilter=true;
                }else{
                    //用户授权认证没有通过!客户端请求参数中无token信息
                    System.out.println("token为空"+method+"||"+ss);
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("code","error");
                    jsonObject.put("data","请求中无token信息");
                    servletResponse.getWriter().print(jsonObject.toJSONString());
                    isFilter=false;
                }
            } else {
                //获取用户id
                String idStr=((HttpServletRequest) servletRequest).getHeader("accountName");
                //开始认证token和用户id
                String token2=(String) redisService.get("token_"+idStr);
                isFilter=token.equals(token2);
            }

            //根据结果决定是否放行
            if (isFilter) {
                System.out.println("token验证成功，放行");
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
