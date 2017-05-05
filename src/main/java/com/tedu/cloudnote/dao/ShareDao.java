package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Share;

public interface ShareDao {
	//根据shareId查看分享笔记信息
	Share findByShareId(String shareId);
	List<Share> findLikeTitle(Map<String,Object> map);
	void save(Share share);
}
