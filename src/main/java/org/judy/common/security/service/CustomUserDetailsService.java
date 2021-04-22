package org.judy.common.security.service;

import org.judy.common.security.domain.CustomUser;
import org.judy.common.security.domain.MemberVO;
import org.judy.common.security.mapper.MemberMapper;
import org.judy.manager.domain.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.warn("userName : "+username);
		
		Manager manager = mapper.managerRead(username);
		log.warn(manager);
		
		MemberVO vo = mapper.read(username);
		
		log.warn(vo);
		
		return manager == null ? new CustomUser(vo) : new CustomUser(manager);
	}

}
