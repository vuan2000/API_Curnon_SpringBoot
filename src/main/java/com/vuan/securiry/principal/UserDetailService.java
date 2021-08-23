package com.vuan.securiry.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vuan.dao.UserDao;
import com.vuan.entity.User;

@Service
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	UserDao userDao;
	
	//check user
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUserName(username);
		if(user.getUsername() == null) throw new UsernameNotFoundException("Not found User ");
		return UserPrincipal.build(user);
	}
	public static void main(String[] args) {
		int arr [] = {4};
		System.out.print(arr[0]);
	}
}
