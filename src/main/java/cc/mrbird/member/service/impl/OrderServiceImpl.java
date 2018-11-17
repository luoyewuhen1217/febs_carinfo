package cc.mrbird.member.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.member.dao.GoodsMapper;
import cc.mrbird.member.dao.OrderMapper;
import cc.mrbird.member.domain.Goods;
import cc.mrbird.member.domain.Order;
import cc.mrbird.member.service.GoodsService;
import cc.mrbird.member.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderServiceImpl extends BaseService<Order> implements OrderService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsService goodsservice;

    //@Autowired
    //private OrderRoleService orderRoleService;//

    @Override
    public List<Order> findAllOrder(Order order) {
        try {
            Example example = new Example(Order.class);
//            if (StringUtils.isNotBlank(role.getRoleName())) {
//                example.createCriteria().andCondition("role_name=", role.getRoleName());
//            }
            example.setOrderByClause("create_time");
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return new ArrayList<>();
        }
    }

    //产生订单号 时间+随机数
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

    //用UUID生成十六位数唯一订单号
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        //         0 代表前面补充0     
        //         4 代表长度为4     
        //         d 代表参数为正数型
        return machineId+ String.format("%015d", hashCodeV);
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        //创建日期
        order.setCreateTime(new Date());
        //订单号
        order.setOrderCode(getOrderIdByTime());
        //查询商品
//        Goods queryGoods=new Goods();
//        queryGoods.setGoodsid(order.getGoodsid());
//        Goods goods=goodsmapper.findGoodsProfile(queryGoods);
        Goods goods=goodsservice.findById(order.getGoodsId());
        //套餐
        order.setRechargeCycle(goods.getGoodsCycle());
        //金额
        order.setRechargeMoney(goods.getGoodsMoney());
        //支付状态
        order.setPayStatus("为支付");
//        order.setTheme(Order.DEFAULT_THEME);
//        order.setAvatar(Order.DEFAULT_AVATAR);
//        order.setPassword(MD5Utils.encrypt(order.getOrdername(), order.getPassword()));
        this.save(order);
//        setOrderRoles(order, roles);
    }

    @Override
    public Order findByName(String orderName) {
        Example example = new Example(Order.class);
        example.createCriteria().andCondition("lower(ordername)=", orderName.toLowerCase());
        List<Order> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Order> findOrderWithDept(Order order) {
        try {
            if (StringUtils.isNotBlank(order.getTimeField())) {
                String[] timeArr = order.getTimeField().split("~");
                String timeField1 = timeArr[0]+" 00:00:00";
                String timeField2 = timeArr[1]+" 23:59:59";
                order.setTimeField1(timeField1);
                order.setTimeField2(timeField2);
            }
            return this.orderMapper.findOrderWithDept(order);
        } catch (Exception e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void registOrder(Order order) {
        order.setCreateTime(new Date());
//        order.setTheme(Order.DEFAULT_THEME);
//        order.setAvatar(Order.DEFAULT_AVATAR);
//        order.setSsex(Order.SEX_UNKNOW);
//        order.setPassword(MD5Utils.encrypt(order.getOrdername(), order.getPassword()));
//        this.save(order);
//        OrderRole ur = new OrderRole();
//        ur.setOrderId(order.getOrderId());
//        ur.setRoleId(3L);
//        this.orderRoleMapper.insert(ur);
    }

    @Override
    @Transactional
    public void updateTheme(String theme, String orderName) {
        Example example = new Example(Order.class);
        example.createCriteria().andCondition("ordername=", orderName);
        Order order = new Order();
        //order.setTheme(theme);
        this.orderMapper.updateByExampleSelective(order, example);
    }

    @Override
    @Transactional
    public void addOrder(Order order, Long[] roles) {
        order.setCreateTime(new Date());
//        order.setTheme(Order.DEFAULT_THEME);
//        order.setAvatar(Order.DEFAULT_AVATAR);
//        order.setPassword(MD5Utils.encrypt(order.getOrdername(), order.getPassword()));
        this.save(order);
        setOrderRoles(order, roles);
    }

    private void setOrderRoles(Order order, Long[] roles) {
//        Arrays.stream(roles).forEach(roleId -> {
//            OrderRole ur = new OrderRole();
//            ur.setOrderId(order.getOrderId());
//            ur.setRoleId(roleId);
//            this.orderRoleMapper.insert(ur);
//        });
    }

    @Override
    @Transactional
    public void updateOrder(Order order, Long[] roles) {
//        order.setPassword(null);
//        order.setOrdername(null);
//        order.setModifyTime(new Date());
        this.updateNotNull(order);
        Example example = new Example(Order.class);
        example.createCriteria().andCondition("order_id=", order.getOrderId());
        this.orderMapper.deleteByExample(example);
        setOrderRoles(order, roles);
    }

    @Override
    @Transactional
    public void deleteOrders(String orderIds) {
        List<String> list = Arrays.asList(orderIds.split(","));
        this.batchDelete(list, "orderId", Order.class);

        //this.orderRoleService.deleteOrderRolesByOrderId(orderIds);
    }

    @Override
    public void updateLoginTime(String orderName) {

    }

    @Override
    public void updatePassword(String password) {

    }

//    @Override
//    @Transactional
//    public void updateLoginTime(String orderName) {
//        Example example = new Example(Order.class);
//        example.createCriteria().andCondition("lower(ordername)=", orderName.toLowerCase());
//        Order order = new Order();
//        order.setLastLoginTime(new Date());
//        this.orderMapper.updateByExampleSelective(order, example);
//    }
//
//    @Override
//    @Transactional
//    public void updatePassword(String password) {
//        Order order = (Order) SecurityUtils.getSubject().getPrincipal();
//        Example example = new Example(Order.class);
//        example.createCriteria().andCondition("ordername=", order.getOrdername());
//        String newPassword = MD5Utils.encrypt(order.getOrdername().toLowerCase(), password);
//        order.setPassword(newPassword);
//        this.orderMapper.updateByExampleSelective(order, example);
//    }

//    @Override
//    public OrderWithRole findById(Long orderId) {
//        List<OrderWithRole> list = this.orderMapper.findOrderWithRole(orderId);
//        List<Long> roleList = new ArrayList<>();
//        for (OrderWithRole uwr : list) {
//            roleList.add(uwr.getRoleId());
//        }
//        if (list.isEmpty()) {
//            return null;
//        }
//        OrderWithRole orderWithRole = list.get(0);
//        orderWithRole.setRoleIds(roleList);
//        return orderWithRole;
//    }

    @Override
    public Order findOrderProfile(Order order) {
        return this.orderMapper.findOrderProfile(order);
    }

    @Override
    @Transactional
    public void updateOrderProfile(Order order) {
//        order.setOrdername(null);
//        order.setPassword(null);
//        if (order.getDeptId() == null) order.setDeptId(0L);
//        this.updateNotNull(order);
    }

}
