package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.UserRepository;
import com.example.demo.domain.User;

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

	/**
	 * ユーザー登録をします.
	 * 
	 * @param user ユーザー情報が詰まったオブジェクト.
	 */
	public void register(User user) {
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
