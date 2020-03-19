package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

/**
 * usersテーブルを操作するリポジトリです.
 * 
 * @author masashi.nose
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * usersテーブル１行分の情報を取得するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};

	/** テーブル名 */
	private static final String TABLE_NAME = "users";

	/**
	 * DBへユーザー情報を登録する.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO " + TABLE_NAME
				+ " (id, name, email, password, zipcode, address, telephone) values (id = :id, name = :name, email = :email, password = :password, zipcode = :zipcode, address = :address, telephone = :telephone)";
		template.update(sql, param);

	}

	/**
	 * メールアドレスでユーザー情報を検索する.
	 * 
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone from " + TABLE_NAME
				+ " WHERE email = :email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
		return user;
	}

}
