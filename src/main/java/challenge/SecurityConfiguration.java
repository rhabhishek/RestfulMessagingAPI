package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import challenge.security.CustomBasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String REALM_NAME = "ThousandEyes";

	@Autowired
    private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("batman").password("batman").authorities("ROLE_USER")
          .and()
          .withUser("superman").password("superman").authorities("ROLE_USER")
          .and()
          .withUser("catwoman").password("catwoman").authorities("ROLE_USER")
          .and()
          .withUser("daredevil").password("daredevil").authorities("ROLE_USER")
          .and()
          .withUser("alfred").password("alfred").authorities("ROLE_USER")
          .and()
          .withUser("dococ").password("dococ").authorities("ROLE_USER")
          .and()
          .withUser("zod").password("zod").authorities("ROLE_USER")
          .and()
          .withUser("spiderman").password("spiderman").authorities("ROLE_USER")
          .and()
          .withUser("ironman").password("ironman").authorities("ROLE_USER")
          .and()
          .withUser("profx").password("profx").authorities("ROLE_USER");
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/**").
		permitAll().anyRequest().authenticated()
		.and().authorizeRequests()
		.and().csrf().disable();
		http.headers().frameOptions().disable().and()
         .httpBasic()
             .realmName(REALM_NAME)
             .authenticationEntryPoint(authenticationEntryPoint)
             .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	
}