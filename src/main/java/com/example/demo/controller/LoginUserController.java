package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.LoginUserForm;
import com.example.demo.service.LoginUserService;

/**
 * 
 * ログイン操作に関するコントローラ
 * 
 * @author masashi.nose
 *
 */
@Controller
@RequestMapping("/login-user")
public class LoginUserController {

	@Autowired
	private LoginUserService loginUserService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public LoginUserForm setUpForm() {
		return new LoginUserForm();
	}

//	/**
//	 * ログイン画面に遷移します.
//	 * 
//	 * @return ログイン画面
//	 */
//	@RequestMapping("/toLogin")
//	public String toLogin() {
//		return "login";
//	}

	/**
	 * 
	 * ログイン画面へ遷移します.
	 * 
	 * @param model　リクエストスコープ作成
	 * @param error　
	 * @return ログイン画面
	 */
	@RequestMapping("/toLogin")
	public String toLogin(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}

		return "login";
	}
	
	/**
	 * ログアウトします.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "item_list";
		
	}

}
