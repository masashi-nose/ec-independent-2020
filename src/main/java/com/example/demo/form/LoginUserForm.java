package com.example.demo.form;

/**
 * 
 * ログインフォームからリクエストパラメータを受け取るフォーム.
 * 
 * @author masashi.nose
 *
 */
public class LoginUserForm {

	/** メールアドレス */
	private String email;
	/** パスワード */
	private String password;

	@Override
	public String toString() {
		return "LoginUserForm [email=" + email + ", password=" + password + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
