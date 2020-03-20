package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

/**
 * ユーザー登録の業務処理を行うサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザー登録をします.
	 * 登録の際、パスワードはここでハッシュ化されます.
	 * 
	 * @param user ユーザー情報が詰まったオブジェクト.
	 */
	public void register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.insert(user);
	}

	/**
	 * メールアドレスからユーザー情報を検索します. 
	 * 該当がなければnullを返します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報が詰まったオブジェクト.
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
