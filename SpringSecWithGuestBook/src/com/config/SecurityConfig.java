package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.filter.CustomFilter;
import com.handler.LoginFailureHandler;
import com.handler.LoginSuccesHandler;
import com.provider.UserAuthProvider;
import com.service.UserAuthService;

@EnableWebSecurity
//?
//@EnableGlobalMethodSecurity //<security:global-method-security secured-annotations="enabled" />
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        //ROLE_ 이 자동으로 붙으니까 ROLE_ 붙이면 안됨. hasAnyRole은 여러개 쓸수있ㄷ음. hasRole은 한개만 쓸수있음.
        		.antMatchers("/test*","/list*","/update*","/write*").permitAll()
        		//.antMatchers("/list").hasAnyRole("USER")
        		.antMatchers("/writeForm","/write").hasAnyRole("USER")
        		.antMatchers("/login").permitAll()
               	 //어떤 요청이든지 다 권한이 필요하다. 만약 anyRequest().permitAll() 면 어떤 요청이든지 허가.
        		.anyRequest().authenticated();
        		 //Spring Security 에서 기본적으로 제공하는 로그인 폼페이지 쓸거면 등록.
                //.and().httpBasic();
        //CROF 설정을 해제합니다. - 초기 개발시에만 설정합니다 - csrf보안설정 여부 설정을 주석처리하면 자동으로 csrf 필터가 활성화된다. login.jsp 에서 인풋태그에 넣어주고 넘겨야 잘됨.
        http.csrf().disable();
        
        //로그인설정
        //참고 https://to-dy.tistory.com/80?category=720806
        http.formLogin()
        		// 로그인 실행 페이지는 / 와 /login 두 페이지에서 로그인 실행 할것이다. (로그인 form 페이지의 url)
                .loginPage("/")
                .loginPage("/login")
                
                //로그인 버튼 누를시 로그인 프로세싱할 경로 : /auth : 3번필터(AuthenticationFilter) 소환시키는 키워드
                //로그인 버튼이 눌러지면 spring security 엔진이 AuthProvider의 authenticate 메소드를 호출하여 로그인 검사를 수행한다.
                //즉, /auth url이 요청되면 로그인 절차는 spring security 엔진에 의해서 자동으로 진행된다.
                //참고 : .loginProcessingUrl("넣는경로") 는 이름이 아무거나 상관없다 꼭 auth일 필요없음. 대신, jsp form태그안에서 action에 이와같은 명칭이 들어가야
                //		해당 url을 스프링 시큐리티가 가로채 처리하게된다.
                //.loginProcessingUrl("/auth")
                .loginProcessingUrl("/loginProcess")
                
                //로그인 성공할경우 해당 Bean의 onAuthenticationSuccess 메소드 호출
                .successHandler(loginSuccessHandler())
                
                //로그인 성공시 이동될 url 작성 
                //Q. 로그인 성공시 이동될 Url을 http.formLogin() 에서 해줘야하는지,
                //	 아니면, LoginSuccesHandler - onAuthenticationSuccess 메소드에서 defaultTargetUrl 변수에 값을 지정해줘야하는지.
                //.defaultSuccessUrl("/main")
                
                //로그인 실패할경우 해당 Bean의 onAuthenticationFailure 메소드 호출
                .failureHandler(loginFaliureHandler())
                
                //form id의 name 속성값
                .usernameParameter("userId")
                //form pw의 name 속성값
                .passwordParameter("password")
                
                .and()
                
                .logout()

        //logout() 설정 참고 https://to-dy.tistory.com/82?category=720806
        //http.logout() //정리 : logout경로로 요청이 들어올 경우 / 로 리다이렉트하고 세션 초기화.
        		//로그아웃 처리할 URL /logout 은 security가 알아서 만들기때문에 이경로로 요청만 하면됨.
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //로그아웃 성공시 이동될 URL "/" ( 메인 페이지? 같은 느낌?)
                //.logoutSuccessUrl("/")
                .logoutSuccessUrl("/login")
                //로그아웃시 세션 삭제여부 (세션초기화)
                .invalidateHttpSession(true);
                
        http.sessionManagement()
        		//최대 허용 가능한 세션 수( 중복 로그인 허용 개수 ) = 1개로 지정.
                .maximumSessions(1)
                //로그인 개수가 가득 찼을때 다른이용자가 로그인하게되면 튕기게 할건지 안할건지 - 신규로그인 허용시키고 기존 사용자 튕기게함.
                .maxSessionsPreventsLogin(false)
                .and();
        
        //커스텀필터 테스트
        http.addFilterBefore(customFilter(),BasicAuthenticationFilter.class);
        //http.addFilterBefore(customFilter(),UsernamePasswordAuthenticationFilter.class);
        
        
        //로그인시 UserAuthProvider의 authenticate메소드를 호출해서 로그인 프로세스에대한 처리로직을 수행한다.
        //Bean으로 등록한 프로바이더가 있으면 따로 등록시켜줄 필요 없음.
        //http.authenticationProvider(userAuthProvider());
    }

    @Bean
    public LoginSuccesHandler loginSuccessHandler(){
        LoginSuccesHandler loginSuccessHandler = new LoginSuccesHandler();
        //loginSuccessHandler.setDefaultTargetUrl("/main");
        loginSuccessHandler.setDefaultTargetUrl("/list");
        loginSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        return loginSuccessHandler;
    }

    @Bean
    public LoginFailureHandler loginFaliureHandler(){
        LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
        loginFailureHandler.setDefaultFailureUrl("/login?status=fail");
        //login.jsp 에서 input 태그에 명칭한 id 의 name
        loginFailureHandler.setInputUserIdname("userId");
        loginFailureHandler.setInputPasswordname("password");
        loginFailureHandler.setErrorMsg("ERRORMSG");
        //loginFailureHandler.setDefaultFailureUrl("/main");
        return loginFailureHandler;
    }

    @Bean
    public UserDetailsService userAuthService(){
        return new UserAuthService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
    	//암호화 알고리즘을 골라서 bean을 만들 수 있다. 
    	//NoOpPasswordEncoder : deprecate 되었다. 테스트할 때만 쓰자
    	//BCryptPasswordEncoder : bcrypt 해쉬 알고리즘을 이용 (추천)
    	//StandardPasswordEncoder : sha 해쉬 알고리즘을 이용
    	
        return new BCryptPasswordEncoder();
    }

    //만약 AuthenticationProvider 대신 DaoAuthenticationProvider 를 사용해서 구현할 경우,
    //기본적으로 PlaintextPasswordEncoder(=NoOpPasswordEncoder)를 사용한다.
    //ex) public DaoAuthenticationProvider() { this.setPasswordEncoder((PasswordEncoder)(new PlaintextPasswordEncoder())); }
    //즉, 위에서 정의한 public PasswordEncoder passwordEncoder 를 따로 정의할 필요가 없어지는듯함.
    @Bean
    public AuthenticationProvider userAuthProvider(){
        UserAuthProvider provider = new UserAuthProvider();
        provider.setUserDetailsService(userAuthService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    public CustomFilter customFilter() {
    	return new CustomFilter();
    }

}
