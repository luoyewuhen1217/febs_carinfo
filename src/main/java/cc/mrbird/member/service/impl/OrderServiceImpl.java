package cc.mrbird.member.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.member.dao.GoodsMapper;
import cc.mrbird.member.dao.OrderMapper;
import cc.mrbird.member.domain.Goods;
import cc.mrbird.member.domain.Order;
import cc.mrbird.member.service.GoodsService;
import cc.mrbird.member.service.OrderService;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
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

    @Autowired
    private UserService userservice;

    @Autowired
    private OrderService orderservice;

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
            example.setOrderByClause("ORDER_ID desc");
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

        //查询商品
//        Goods queryGoods=new Goods();
//        queryGoods.setGoodsid(order.getGoodsid());
//        Goods goods=goodsmapper.findGoodsProfile(queryGoods);
//        Goods goods=goodsservice.findById(order.getGoodsId());
//        //套餐
//        order.setRechargeCycle(goods.getGoodsCycle());
//        //金额
//        order.setRechargeMoney(goods.getGoodsMoney());
        //支付状态
        order.setPayStatus("2");// 未支付
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
        //order.setOrderId(null);
        //order.setPassword(null);
        //if (order.getDeptId() == null) order.setDeptId(0L);
        this.updateNotNull(order);
    }

    /***
     *
     * @param order 订单
     * @param user 用户
     */
    @Override
    @Transactional
    public void  updateUserAndOrder(Order order, User user ){
        Date date=null;
        if("1".equals(user.getVipStatus())){//如果用户之前已经到期就从当前日期开始计算到期时间
            date=user.getVipTime();
        }else{
            date=new Date();
            user.setVipStatus("0");//未到期
        }
        Date vipdate=getExpiryTimeByPayTime(date,order);
        //更新订单
        order.setExpiryTime(vipdate);
        //更新用户
        user.setVipTime(vipdate);
        userservice.UpdateUserOfPay(user);
//        this.save(order);
        this.updateOrderProfile(order);
    }

    @Override
    public Order queryOrderById(Order order) {
        try {
            return this.selectByKey(order);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return new Order();
        }
    }


    /**
     * 通过订单ID设置支付方式、充值金额、充值周期、支付时间、到期时间等信息
     * @param order
     */
    @Override
    @Transactional
    public void updateOrderByOrderId(Order order) {
        order.setPayMent("");
        order.setPayStatus("");
        order.setRechargeMoney("");
        order.setRechargeCycle("");
        Date date = new Date();
        this.getExpiryTimeByPayTime(date,order);
        order.setPayTime(new Date());


        order.setExpiryTime(new Date());




    }

    private Date getExpiryTimeByPayTime(Date date,Order order) {

        // Calendar calendar = new GregorianCalendar();
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_YEAR, 1);//增加一天,负数为减少一天
        //calendar.add(calendar.DAY_OF_MONTH, 1);//增加一天
        //calendar.add(calendar.DATE,1);//增加一天
        //calendar.add(calendar.WEEK_OF_MONTH, 1);//增加一个礼拜
        //calendar.add(calendar.WEEK_OF_YEAR,1);//增加一个礼拜
        //calendar.add(calendar.MARCH,1);// 增加一个月
        //calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
        if (order.getRechargeCycle().equals("1天")) {
            calendar.add(calendar.DAY_OF_MONTH, 1);
        }
        if (order.getRechargeCycle().equals("1个月")) {
            calendar.add(calendar.MARCH, 1);
        }
        if (order.getRechargeCycle().equals("3个月")) {
            calendar.add(calendar.MARCH, 3);
        }
        if (order.getRechargeCycle().equals("6个月")) {
            calendar.add(calendar.MARCH, 6);
        }
        if (order.getRechargeCycle().equals("1年")) {
            calendar.add(calendar.YEAR, 1);
        }
        if (order.getRechargeCycle().equals("3年")) {
            calendar.add(calendar.YEAR, 3);
        }
        if (order.getRechargeCycle().equals("5年")) {
            calendar.add(calendar.YEAR, 5);
        }

        date = calendar.getTime();


        return date;
    }


}
