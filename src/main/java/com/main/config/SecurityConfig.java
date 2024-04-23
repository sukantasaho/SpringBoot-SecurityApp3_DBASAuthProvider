package com.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		 //InMemory DB as the authentication info provider
		//password is not encoded
			/*auth.inMemoryAuthentication().withUser("sukanta").password("{noop}chintu@1995").roles("CUSTOMER");
			auth.inMemoryAuthentication().withUser("manoj").password("{noop}manoj@1995").roles("MANAGER");
			auth.inMemoryAuthentication().withUser("mukti").password("{noop}mukti@1995").roles("CUSTOMER","MANAGER");
			auth.inMemoryAuthentication().withUser("raja").password("{noop}raja@1995").roles("VISITOR");*/
		
		 //After password encoded
		 auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("sukanta").password("$2a$10$7MiERvyQ4FJqQEwMonwOZeqE0Oiz6b7POPrIvv.VQ9DQTjC0rbSKK").roles("CUSTOMER");
		 auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("manoj").password("$2a$10$aXfGY1AtF0jG0.N5qu6uH.q9rd1gYBouqix9QtEFonkCYZC/oeWQi").roles("MANAGER");
		 auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("mukti").password("$2a$10$vNEEv.FNzlG2sZp.vyv22.jaSMFLDVY0JOWoqwbpeBSkqQAvMNB3C").roles("CUSTOMER","MANAGER");
		 auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("raja").password("$2a$10$jGeou01zCpqeZkWqdU.FM.eB9y2iq.DV1kFAOYux0FGD1B8m4aBy.").roles("VISITOR");
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//place the authentication and authorization logics for the request url
		http.authorizeRequests().antMatchers("/").permitAll()//no Authentication and Authorization 
		.antMatchers("/offers").authenticated()//only Authentication
		.antMatchers("/showBalance").hasAnyRole("CUSTOMER","MANAGER")
		.antMatchers("/approveLoan").hasRole("MANAGER")
		.anyRequest().authenticated()
		//.and().httpBasic()
		.and().formLogin()
		//.and().rememberMe()
	    .and().logout()
		.and().exceptionHandling().accessDeniedPage("/denied")
		.and().sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true);
		 
	}
	
}
