package cc.mrbird.system.dao;

import java.util.List;
import java.util.Map;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.domain.UserWithRole;

public interface UserMapper extends MyMapper<User> {

	List<User> findUserWithDept(User user);
	
	List<UserWithRole> findUserWithRole(Long userId);
	
	User findUserProfile(User user);

	void updateUserVip(List userNameList);

	List<User> findUserWithUserName();
}