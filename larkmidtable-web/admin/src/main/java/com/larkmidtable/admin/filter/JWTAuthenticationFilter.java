package com.larkmidtable.admin.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larkmidtable.admin.core.conf.ExcecutorConfig;
import com.larkmidtable.admin.core.util.I18nUtil;
import com.larkmidtable.admin.entity.JobDatasource;
import com.larkmidtable.admin.entity.JwtUser;
import com.larkmidtable.admin.entity.LoginUser;
import com.larkmidtable.admin.mapper.JobLogMapper;
import com.larkmidtable.admin.util.AESUtil;
import com.larkmidtable.admin.util.DruidDataSource;
import com.larkmidtable.admin.util.IPUtils;
import com.larkmidtable.admin.util.JwtTokenUtils;
import com.larkmidtable.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.larkmidtable.core.util.Constants.SPLIT_COMMA;


@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Integer> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;
	@Resource
	public JobLogMapper jobLogMapper;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            rememberMe.set(loginUser.getRememberMe());
            //记录日志
			String ipAddr = IPUtils.getIpAddr(request);
			JobDatasource jd =new JobDatasource();
			String url = ExcecutorConfig.getExcecutorConfig().getUrl();
			String[] urlArray = url.split("\\?");
			String[] split = urlArray[0].split("\\/");
			jd.setDatasourceName(split[split.length-1]);
			jd.setJdbcUrl(ExcecutorConfig.getExcecutorConfig().getUrl());
			jd.setJdbcUsername(AESUtil.encrypt(ExcecutorConfig.getExcecutorConfig().getUsername()));
			jd.setJdbcPassword(AESUtil.encrypt(ExcecutorConfig.getExcecutorConfig().getPassword()));
			jd.setJdbcDriverClass(ExcecutorConfig.getExcecutorConfig().getDriverClassname());
			DruidDataSource.executeSql(jd,"insert into lark_operate_log(operate,user,address,createtime)values('login','"+loginUser.getUsername()+"','"+ipAddr+"','"+sdf.format(new Date())+"')",new HashMap<>());
			return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            logger.error("attemptAuthentication error :{}",e);
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {


        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
//		authenticationManager.writeLog(jwtUser.getUsername(), IPUtils.getIpAddr(request));
        boolean isRemember = rememberMe.get() == 1;

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            role = authority.getAuthority();
        }

        String token = JwtTokenUtils.createToken(jwtUser.getId(),jwtUser.getUsername(), role, isRemember);
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> maps = new HashMap<>();
        maps.put("data", JwtTokenUtils.TOKEN_PREFIX + token);
        maps.put("roles", role.split(SPLIT_COMMA));
        response.getWriter().write(JSON.toJSONString(new ReturnT<>(maps)));
    }



    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSON(new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("login_param_invalid"))).toString());
    }
}
