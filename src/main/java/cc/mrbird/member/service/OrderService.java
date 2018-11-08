package cc.mrbird.member.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.member.domain.Order;

import java.util.List;

//@CacheConfig(cacheNames = "OrderService")
public interface OrderService extends IService<Order> {

//    OrderWithRole findById(Long orderId);

    public List<Order> findAllOrder(Order order);

    void addOrder(Order order);

    Order findByName(String orderName);

//    @Cacheable(key = "#p0")
    List<Order> findOrderWithDept(Order order);
//    @CacheEvict(key = "#p0", allEntries = true)
    void registOrder(Order order);

    void updateTheme(String theme, String orderName);


//    @CacheEvict(allEntries = true)
    void addOrder(Order order, Long[] roles);

//    @CacheEvict(key = "#p0", allEntries = true)
    void updateOrder(Order order, Long[] roles);

//    @CacheEvict(key = "#p0", allEntries = true)
    void deleteOrders(String orderIds);

    void updateLoginTime(String orderName);

    void updatePassword(String password);

    Order findOrderProfile(Order order);

    void updateOrderProfile(Order order);
}
