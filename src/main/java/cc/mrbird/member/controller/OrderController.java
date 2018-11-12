package cc.mrbird.member.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.member.domain.Order;
import cc.mrbird.member.service.OrderService;
import cc.mrbird.system.domain.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    private static final String ON = "on";

    @RequestMapping("order")
    @RequiresPermissions("order:list")
    public String index(Model model) {
       // Order order = orderService.findAllOrder(new Order());//super.get();
        //model.addAttribute("order", order);
        return "member/pay/order";
    }

    @Log("获取订单信息")
    @RequestMapping("order/list")
    @ResponseBody
    public Map<String, Object> orderList(QueryRequest request, Order order) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Order> list = this.orderService.findAllOrder(order);
        PageInfo<Order> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }

    @Log("创建订单信息x")
//    @RequestMapping("order/addnew")
    @ResponseBody
    @RequestMapping(value="order/addnew", method= RequestMethod.POST, produces="application/json;charset=utf-8;")
    public  ResponseBo regist(@RequestBody Map<String, Object> params,Model model) {
        User user = super.getCurrentUser();
        try{
            Order order=new Order();
            order.setUserId(user.getUserId());
            order.setUserName(user.getUsername());
            order.setGoodsId(Long.parseLong((String)params.get("data_id")));
            order.setPayMent("wechat".equals(params.get("payment_method"))?"微信支付":"支付宝");
            this.orderService.addOrder(order);
            return ResponseBo.ok("新增订单成功！");
        } catch (Exception e) {
            log.error("新增订单失败", e);
            return ResponseBo.error("新增订单失败，请联系网站管理员！");
        }
        //return ResponseBo.ok();
    }
   // public ResponseBo regist(Order order) {
//        try {
//            Order result = this.orderService.findByName(order.getOrdercycle());
//            if (result != null) {
//                return ResponseBo.warn("该订单名已被使用！");
//            }
//            this.orderService.registOrder(order);
//            return ResponseBo.ok();
//        } catch (Exception e) {
//            log.error("注册失败", e);
//            return ResponseBo.error("注册失败，请联系网站管理员！");
//        }
      //  return null;
   // }

    @RequestMapping("order/checkOrderName")
    @ResponseBody
    public boolean checkOrderName(String ordername, String oldordername) {
        if (StringUtils.isNotBlank(oldordername) && ordername.equalsIgnoreCase(oldordername)) {
            return true;
        }
        Order result = this.orderService.findByName(ordername);
        return result == null;
    }

    @RequestMapping("order/getOrder")
    @ResponseBody
    public ResponseBo getOrder(Long orderId) {
        try {
            Order order = null;// this.orderService.findById(orderId);

            return ResponseBo.ok(order);
        } catch (Exception e) {
            log.error("获取订单失败", e);
            return ResponseBo.error("获取订单失败，请联系网站管理员！");
        }
    }

//    @Log("获取订单信息")
//    @RequestMapping("order/list")
//    @ResponseBody
//    public Map<String, Object> orderList(QueryRequest request, Order order) {
//        PageHelper.startPage(request.getPageNum(), request.getPageSize());
//        List<Order> list = this.orderService.findOrderWithDept(order);
//        PageInfo<Order> pageInfo = new PageInfo<>(list);
//        return getDataTable(pageInfo);
//    }

    @RequestMapping("order/excel")
    @ResponseBody
    public ResponseBo orderExcel(Order order) {
        try {
            List<Order> list = this.orderService.findOrderWithDept(order);
            return FileUtils.createExcelByPOIKit("订单表", list, Order.class);
        } catch (Exception e) {
            log.error("导出订单信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("order/csv")
    @ResponseBody
    public ResponseBo orderCsv(Order order) {
        try {
            List<Order> list = this.orderService.findOrderWithDept(order);
            return FileUtils.createCsv("订单表", list, Order.class);
        } catch (Exception e) {
            log.error("导出订单信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }



    @Log("更换主题")
    @RequestMapping("order/theme")
    @ResponseBody
    public ResponseBo updateTheme(Order order) {
        try {
            //this.orderService.updateTheme(order.getTheme(), order.getOrdername());
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("修改主题失败", e);
            return ResponseBo.error();
        }
    }

    @Log("新增订单")
    @RequiresPermissions("order:add")
    @RequestMapping("order/add")
    @ResponseBody
    public ResponseBo addOrder(Order order, Long[] roles) {
        try {
//            if (ON.equalsIgnoreCase(order.getStatus()))
//                order.setStatus(Order.STATUS_VALID);
//            else
//                order.setStatus(Order.STATUS_LOCK);
            this.orderService.addOrder(order, roles);
            return ResponseBo.ok("新增订单成功！");
        } catch (Exception e) {
            log.error("新增订单失败", e);
            return ResponseBo.error("新增订单失败，请联系网站管理员！");
        }
    }

    @Log("修改订单")
    @RequiresPermissions("order:update")
    @RequestMapping("order/update")
    @ResponseBody
    public ResponseBo updateOrder(Order order, Long[] rolesSelect) {
        try {
//            if (ON.equalsIgnoreCase(order.getStatus()))
//                order.setStatus(Order.STATUS_VALID);
//            else
//                order.setStatus(Order.STATUS_LOCK);
            this.orderService.updateOrder(order, rolesSelect);
            return ResponseBo.ok("修改订单成功！");
        } catch (Exception e) {
            log.error("修改订单失败", e);
            return ResponseBo.error("修改订单失败，请联系网站管理员！");
        }
    }

    @Log("删除订单")
    @RequiresPermissions("order:delete")
    @RequestMapping("order/delete")
    @ResponseBody
    public ResponseBo deleteOrders(String ids) {
        try {
            this.orderService.deleteOrders(ids);
            return ResponseBo.ok("删除订单成功！");
        } catch (Exception e) {
            log.error("删除订单失败", e);
            return ResponseBo.error("删除订单失败，请联系网站管理员！");
        }
    }

    @RequestMapping("order/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
//        Order order = getCurrentOrder();
//        String encrypt = MD5Utils.encrypt(order.getOrdername().toLowerCase(), password);
//        return order.getPassword().equals(encrypt);
        return false;
    }

    @RequestMapping("order/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String newPassword) {
        try {
            this.orderService.updatePassword(newPassword);
            return ResponseBo.ok("更改密码成功！");
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return ResponseBo.error("更改密码失败，请联系网站管理员！");
        }
    }

    @RequestMapping("order/profile")
    public String profileIndex(Model model) {
//        Order order = super.getCurrentOrder();
//        order = this.orderService.findOrderProfile(order);
//        String ssex = order.getSsex();
//        if (Order.SEX_MALE.equals(ssex)) {
//            order.setSsex("性别：男");
//        } else if (Order.SEX_FEMALE.equals(ssex)) {
//            order.setSsex("性别：女");
//        } else {
//            order.setSsex("性别：保密");
//        }
//        model.addAttribute("order", order);
        return "system/order/profile";
    }

    @RequestMapping("order/getOrderProfile")
    @ResponseBody
    public ResponseBo getOrderProfile(Long orderId) {
        try {
            Order order = new Order();
//            order.setOrderId(orderId);
            return ResponseBo.ok(this.orderService.findOrderProfile(order));
        } catch (Exception e) {
            log.error("获取订单信息失败", e);
            return ResponseBo.error("获取订单信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("order/updateOrderProfile")
    @ResponseBody
    public ResponseBo updateOrderProfile(Order order) {
        try {
            this.orderService.updateOrderProfile(order);
            return ResponseBo.ok("更新个人信息成功！");
        } catch (Exception e) {
            log.error("更新订单信息失败", e);
            return ResponseBo.error("更新订单信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("order/changeAvatar")
    @ResponseBody
    public ResponseBo changeAvatar(String imgName) {
        try {
            String[] img = imgName.split("/");
            String realImgName = img[img.length - 1];
//            Order order = getCurrentOrder();
//            order.setAvatar(realImgName);
//            this.orderService.updateNotNull(order);
            return ResponseBo.ok("更新头像成功！");
        } catch (Exception e) {
            log.error("更换头像失败", e);
            return ResponseBo.error("更新头像失败，请联系网站管理员！");
        }
    }
}