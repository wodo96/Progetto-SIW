package it.uniroma3.siw.taskmanager.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.taskmanager.model.Credentials;


@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			
			//authorization paragraph: here we define WHO can access WHICH pages
			.authorizeRequests()
			//anyone(authenticated or not) can access the welcome page, the login page, and the registration page
			.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/users/register").permitAll()
			//anyone (authenticated or not) can send POST requests to the login endpoint and the register endpoint
			.antMatchers(HttpMethod.POST, "/login", "/users/register").permitAll()
			//only authenticated users with ADMIN authority can access the admin pag
			.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
			.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
			//all authenticated users can access all the remaining other pages
			.anyRequest().authenticated()
			
			//login paragraph: here we define how to login
			//use formLogin protocol to perform login
			.and().formLogin()
			//after login is successful, redirect to the logged user homepage
			.defaultSuccessUrl("/home")
			
			//logout paragraph: we are going to define here how to logout
			.and().logout()
			.logoutUrl("/logout") //logout is performed when sending a GET to "/logout"
			.logoutSuccessUrl("/index") //after logout is successful, redirect to /index page
			.invalidateHttpSession(true)
			.clearAuthentication(true).permitAll();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		//use autowired datasource to access the saved credentials
		.dataSource(this.dataSource)
		//retrive username and a role
		.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
		//retrieve username, password and a boolean flag specifying the user is enabled or not (always enabled)
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}