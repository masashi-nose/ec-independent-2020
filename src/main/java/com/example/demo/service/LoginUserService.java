package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

/**
 * 
 * ログインに関する業務処理を行うサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class LoginUserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * メールアドレスからユーザー情報を検索します.
	 * 
	 * @param email メールアドレス
	 * @return　ユーザー情報が詰まったオブジェクト
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
