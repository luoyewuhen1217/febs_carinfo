package cc.mrbird.member.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.common.util.pay.JsonUtils;
import cc.mrbird.common.util.redis.RedisHelper;
import cc.mrbird.member.config.AlipayConfig;
import cc.mrbird.member.domain.Goods;
import cc.mrbird.member.domain.Order;
import cc.mrbird.member.service.GoodsService;
import cc.mrbird.member.service.OrderService;
import cc.mrbird.system.domain.User;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlipayConfig aliPayConfig;
    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsservice;

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


    //产生订单号 时间+随机数
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }

    @Log("打开阿里支付界面")
    @RequestMapping(value = "order/alipay/{jsoonPay}", method = RequestMethod.GET, produces = "application/json;charset=utf-8;")
    public void jsoonPay(@PathVariable("jsoonPay") String jsonPay, HttpServletResponse response) {
        try {
            String result = "<form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=mxuC%2F8cmX1qJIUacJfBw3z%2BhXGkUL1a0F6I2uHsTtGPsGnStaNkHM0xpDeFOVuQQDcnx%2FMzx%2BYFsGZdksR664Ur8S9X49K1fPkTJWUr%2B%2FLojLNKBB9YGk%2Fj%2FLNCjNE8QxdR0JgWYKQ684UARPL%2F9TT1SSKLHZSUmVlxaEwGAmpiGNcFETrHposbG%2BjtfUvLIQHH6JaYfGVEnrpN2I36PueqjWr7dSdnlxWeM0VBsrvfd7u5pa8hsBaBihVxdhpmL5NwgxWIApya0UsUmlm3ztFZk4LDCvWMm8Q1QItONLCYMWRf5Mh1YHe79Ab54zlMxuVz2OzYnFrZwlKGL2NaxOg%3D%3D&return_url=http%3A%2F%2Fqiweb.shangyixx.com%2Falipay%2Freturn_url.jsp&notify_url=http%3A%2F%2Fqiweb.shangyixx.com%2F%2Falipay%2Fnotify_url.jsp&version=1.0&app_id=2016092000553510&sign_type=RSA2&timestamp=2018-11-17+14%3A23%3A52&alipay_sdk=alipay-sdk-java-3.4.27.ALL&format=json\">\n" + "<input type=\"hidden\" name=\"biz_content\" value=\"{&quot;out_trade_no&quot;:&quot;20181117142322735&quot;,&quot;total_amount&quot;:&quot;55&quot;,&quot;subject&quot;:&quot;1&quot;,&quot;body&quot;:&quot;1&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}\">\n" + "<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n" + "</form>\n" + "<script>document.forms[0].submit();</script>";
            result = RedisHelper.get(jsonPay);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Log("创建订单信息x")
//    @RequestMapping("order/addnew")
    @ResponseBody
    @RequestMapping(value = "order/addnew", method = RequestMethod.POST, produces = "application/json;charset=utf-8;")
    public ResponseBo createOrder(@RequestBody Map<String, Object> params, Model model, HttpServletResponse response) {
        User user = super.getCurrentUser();
        try {
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setUserName(user.getUsername());
            order.setGoodsId(Long.parseLong((String) params.get("data_id")));
            order.setPayMent("wechat".equals(params.get("payment_method")) ? "微信支付" : "支付宝");
            Goods goods = goodsservice.findById(order.getGoodsId());
            //套餐
            order.setRechargeCycle(goods.getGoodsCycle());
            //金额
            order.setRechargeMoney(goods.getGoodsMoney());

            //订单号
            order.setOrderCode(getOrderIdByTime());
            this.orderService.addOrder(order);


            //////
            // 金额保留两位
//            money = (float) (Math.round(money * 100)) / 100;
//
//            // 生成订单
//            OrderInfo orderInfo = orderInfoService.createOrder(subject, body, money, aliPayConfig.getSellerId());

            // 1、设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            // 页面跳转同步通知页面路径
            alipayRequest.setReturnUrl(aliPayConfig.return_url);
            // 服务器异步通知页面路径
            alipayRequest.setNotifyUrl(aliPayConfig.notify_url);

            // 2、SDK已经封装掉了公共参数，这里只需要传入业务参数，请求参数查阅开头Wiki
            Map<String, String> map = new HashMap<>(16);
            map.put("out_trade_no", order.getOrderCode());
            map.put("total_amount", goods.getGoodsMoney());
            map.put("subject", goods.getGoodsCycle());
            map.put("body", goods.getRemark());
            // 销售产品码
            map.put("product_code", "FAST_INSTANT_TRADE_PAY");

            alipayRequest.setBizContent(JsonUtils.objectToJson(map));

            response.setContentType("text/html;charset=utf-8");
            try {
                // 3、生成支付表单
                AlipayTradePagePayResponse alipayResponse = alipayClient.pageExecute(alipayRequest);
                if (alipayResponse.isSuccess()) {
                    String result = alipayResponse.getBody();
                    RedisHelper.set(order.getOrderCode(), result, 0);
                    return ResponseBo.ok(order.getOrderCode());
//                    response.getWriter().write(result);
                } else {
                    log.error("【支付表单生成】失败，错误信息：{}", alipayResponse.getSubMsg());
//                    response.getWriter().write("error");
                }
            } catch (Exception e) {
                log.error("【支付表单生成】异常，异常信息：{}", e.getMessage());
                e.printStackTrace();
            }
            //////
//            return ResponseBo.ok("新增订单成功！");

        } catch (Exception e) {
            log.error("新增订单失败", e);
            return ResponseBo.error("新增订单失败，请联系网站管理员！");
        }
        //return ResponseBo.ok();
        return null;
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

    /**
     * 同步跳转
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("/aliPay/returnUrl")
    public String returnUrl(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();

        // 获取支付宝GET过来反馈信息（官方固定代码）
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            System.out.println("values:::"+values);
            System.out.println("name:::"+name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                System.out.println("valueStr--11111111111--------************------------:"+valueStr);
            }
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.alipay_public_key, aliPayConfig.charset, aliPayConfig.sign_type); // 调用SDK验证签名

        // 返回界面
        if (signVerified) {
            System.out.println("前往支付成功页面");




            return "member/pay/order";
        } else {
            System.out.println("前往支付失败页面");
            return "member/pay/order";
        }
    }

    /**
     * 支付宝服务器异步通知
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("/aliPay/notifyUrl")
    public void notifyUrl(HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);

            System.out.println("values-------------"+values);
            System.out.println("name------------------"+name);

            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                System.out.println("valueStr--------************------------:"+valueStr);
            }


        }
    }

}