package com.example.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.security.mapper.LoginMapper;
import com.example.security.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private LoginMapper usersMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws
            UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper();
        wrapper.eq("username",s);
        Users users = usersMapper.selectOne(wrapper);
        if(users == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        System.out.println(users);
        List<GrantedAuthority> auths=null;
        if(users.getUsername().equals("zhang")){
           auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_master"); //添加权限管理员和角色master
        }else if (users.getUsername().equals("li")){
            auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role,ROLE_visitor");
        }else {  //wang
            auths = AuthorityUtils.commaSeparatedStringToAuthorityList("normal");
        }

        return new User(users.getUsername(),
                new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
