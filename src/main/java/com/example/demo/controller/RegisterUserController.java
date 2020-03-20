package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.form.RegisterUserForm;
import com.example.demo.service.RegisterUserService;

/**
 * usersテーブルを操作するコントローラー.
 * 
 * @author masashi.nose
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	/**
	 * 
	 * 登録画面へ遷移します。
	 * 
	 * @return 登録画面
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "register";
	}

	/**
	 * ユーザー情報を登録します. 登録情報に誤りがある場合、登録画面へ戻り、エラー表示させます. 登録成功の場合、ログイン画面へ遷移します.
	 * 
	 * @param user ユーザー情報の詰まったオブジェクト
	 * @return （成功）ログイン画面/（失敗）登録画面
	 */
	@RequestMapping("/register-user")
	public String register(@Validated RegisterUserForm form, BindingResult result) {
		User existUser = registerUserService.findByEmail(form.getEmail());

		if (!(existUser == null)) {
			result.rejectValue("email", "", "入力されたメールアドレスは既に登録されています。");
		}

		if (!(form.getPassword().equals(form.getConfirmPassword()))) {
			result.rejectValue("password", "", "パスワードが一致していません。");
		}

		if (result.hasErrors()) {
			return toRegister();
		}

		User user = new User();
		 
		BeanUtils.copyProperties(form, user);

		registerUserService.register(user);

		return "redirect:/login-user/toLogin";

	}

}
