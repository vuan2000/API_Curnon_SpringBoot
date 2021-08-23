package com.vuan.securiry.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.vuan.entity.User;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails {
	private int id;
	private String name;
	private String age;
	private String role;
	private Boolean enabled;
	private String username;
	private String password;
	private String address;
	private String gender;
	private String phone;
	private String email;
	private String avatar;
	
	public UserPrincipal() {
		
	}
	
	public UserPrincipal(int id, String name, String age, String role, Boolean enabled, String username,
			String password, String address, String gender, String phone, String email, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.role = role;
		this.enabled = enabled;
		this.username = username;
		this.password = password;
		this.address = address;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.avatar = avatar;
	}
	
	//set user vao he thong
	public static UserPrincipal build(User user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return new UserPrincipal(user.getId() ,user.getName() ,user.getAge() ,user.getRole() ,user.getEnabled() ,
				user.getUsername() ,user.getPassword() ,user.getAddress() ,user.getGender() ,
				user.getPhone() ,user.getEmail() ,user.getAvatar());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}