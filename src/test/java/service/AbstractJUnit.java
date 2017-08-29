package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"classpath:spring/context-datasource.xml",
		"classpath:spring/context-root.xml",
		"classpath:spring/web/dispatcher-servlet.xml"
})
public class AbstractJUnit {
	
	protected final ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void authenticationSetup() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPTY"));
		Authentication authentication = new UsernamePasswordAuthenticationToken("tester", "", grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	protected void print(Object value){
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
