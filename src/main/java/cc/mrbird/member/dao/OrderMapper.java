package cc.mrbird.member.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.member.domain.Order;

import java.util.List;

public interface OrderMapper extends MyMapper<Order> {

	List<Order> findOrderWithDept(Order order);
	
	//List<OrderWithRole> findOrderWithRole(Long orderId);
	
	Order findOrderProfile(Order order);
}