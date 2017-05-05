package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.User;

public interface CollectionDao {
	List<User> findAllUser();
	User findById(String userId);
}
