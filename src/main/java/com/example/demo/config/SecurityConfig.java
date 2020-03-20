package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //設定用のクラス
@EnableWebSecurity //Spring Securityのウェブ用機能を利用する.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService memberDetailService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/fonts/**");
		
	}
	
	/**
	 *認可の設定、ログイン/ログアウトに関する設定.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()	//認可に関する設定
		.antMatchers("/**").permitAll()
//		.antMatchers("/admin/**").hasRole("ADMIN")	// /admin/から始まるパスはADMIN権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
//		.antMatchers("/user/**").hasRole("USER")	// /user/から始まるパスはUSER権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
		.anyRequest().authenticated();	// それ以外のパスは認証が必要
		
		http.formLogin()	//ログインに関する設定
			.loginPage("/login-user/toLogin")	// ログイン画面に遷移させるパス(ログイン認証が必要なパスを指定してかつログインされていないとこのパスに遷移される)
			.loginProcessingUrl("/login")	// ログインボタンを押した際に遷移させるパス(ここに遷移させれば自動的にログインが行われる)
			.failureUrl("/login-user/toLogin?error=true")	//ログイン失敗に遷移させるパス
			.defaultSuccessUrl("/", false)	// 第1引数:デフォルトでログイン成功時に遷移させるパス
											// 第2引数: true :認証後常に第1引数のパスに遷移 
											// false:認証されてなくて一度ログイン画面に飛ばされてもログインしたら指定したURLに遷移
			.usernameParameter("email")	// 認証時に使用するユーザ名のリクエストパラメータ名(今回はメールアドレスを使用)
			.passwordParameter("password");	// 認証時に使用するパスワードのリクエストパラメータ名
		
		http.logout()	// ログアウトに関する設定
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))	// ログアウトさせる際に遷移させるパス
			.logoutSuccessUrl("/")	// ログアウト後に遷移させるパス(ここではログイン画面を設定)
			.deleteCookies("JSESSIONID")	// ログアウト後、Cookieに保存されているセッションIDを削除
			.invalidateHttpSession(true);	// true:ログアウト後、セッションを無効にする false:セッションを無効にしない
	
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(memberDetailService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	/**
	 * bcryptアルゴリズムでパスワードのハッシュ化を行います.
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
