package com.hxzy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("参数s: "+ s); // 登录表单的用户名

        // 根据用户名查询数据库，获取用户对象

        String sql = "select * from t_admin where loginacct = ?";
        Map<String, Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql, s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("用户不存在");
        }

        System.out.println(map);

        Set<GrantedAuthority> authorities = new HashSet<>(); // 该集合封装 角色和权限

        // 查询角色
        String rolesql = "SELECT " +
                "tr.`name`,tr.id  FROM t_admin_role ta " +
                "left join t_role tr on ta.roleid = tr.id " +
                "where ta.adminid = ?";

        List<Map<String, Object>> roles = jdbcTemplate.queryForList(rolesql, map.get("id"));

        // 查询权限
        for (Map<String, Object> role : roles) {

            //  添加角色
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.get("name").toString()));

            String persql = "select tp.title from t_role_permission trp " +
                    "left JOIN t_permission tp on trp.permissionid = tp.id " +
                    "where trp.roleid = ?";

            List<String> per = jdbcTemplate.queryForList(persql, String.class, role.get("id"));

            for (String s1 : per) {
                // 添加权限
                authorities.add(new SimpleGrantedAuthority(s1));
            }
        }

        System.out.println(authorities);

        return new User(map.get("loginacct").toString()
                ,map.get("userpswd").toString()
                , authorities); // 权限应该查询数据库
    }

}
