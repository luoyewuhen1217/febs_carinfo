package cc.mrbird.member.dao;

import java.util.List;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.member.domain.Pay;

public interface PayMapper extends MyMapper<Pay> {

	List<Pay> findUserWithDept(Pay user);

	Pay findUserProfile(Pay user);
}