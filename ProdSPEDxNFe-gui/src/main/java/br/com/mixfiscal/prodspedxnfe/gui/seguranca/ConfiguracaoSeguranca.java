package br.com.mixfiscal.prodspedxnfe.gui.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
                .antMatchers("/", "/index.jsf", "/javax.faces.resource/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .usernameParameter("usuario")
                    .passwordParameter("senha")
                    .loginPage("/login.jsf")
                    .permitAll()
                .and()
                .logout()
                    .permitAll();
        //http.csrf().csrfTokenRepository(csrfTokenRepository());
        http.headers().frameOptions().sameOrigin();
    }
        
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("test").password("1234").roles("USER");
    }
    
    private CsrfTokenRepository csrfTokenRepository() 
    { 
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
        repository.setSessionAttributeName("_csrf");
        return repository; 
    }
}
