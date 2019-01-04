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
    public void alipayjsoonPay(@PathVariable("jsoonPay") String jsonPay, HttpServletResponse response) {
        try {
            String result = "<form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=mxuC%2F8cmX1qJIUacJfBw3z%2BhXGkUL1a0F6I2uHsTtGPsGnStaNkHM0xpDeFOVuQQDcnx%2FMzx%2BYFsGZdksR664Ur8S9X49K1fPkTJWUr%2B%2FLojLNKBB9YGk%2Fj%2FLNCjNE8QxdR0JgWYKQ684UARPL%2F9TT1SSKLHZSUmVlxaEwGAmpiGNcFETrHposbG%2BjtfUvLIQHH6JaYfGVEnrpN2I36PueqjWr7dSdnlxWeM0VBsrvfd7u5pa8hsBaBihVxdhpmL5NwgxWIApya0UsUmlm3ztFZk4LDCvWMm8Q1QItONLCYMWRf5Mh1YHe79Ab54zlMxuVz2OzYnFrZwlKGL2NaxOg%3D%3D&return_url=http%3A%2F%2Fqiweb.shangyixx.com%2Falipay%2Freturn_url.jsp&notify_url=http%3A%2F%2Fqiweb.shangyixx.com%2F%2Falipay%2Fnotify_url.jsp&version=1.0&app_id=2016092000553510&sign_type=RSA2&timestamp=2018-11-17+14%3A23%3A52&alipay_sdk=alipay-sdk-java-3.4.27.ALL&format=json\">\n" + "<input type=\"hidden\" name=\"biz_content\" value=\"{&quot;out_trade_no&quot;:&quot;20181117142322735&quot;,&quot;total_amount&quot;:&quot;55&quot;,&quot;subject&quot;:&quot;1&quot;,&quot;body&quot;:&quot;1&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}\">\n" + "<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n" + "</form>\n" + "<script>document.forms[0].submit();</script>";
            result = RedisHelper.get(jsonPay);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Log("打开微信支付界面")
    @RequestMapping(value = "order/wechat/{jsoonPay}", method = RequestMethod.GET, produces = "application/json;charset=utf-8;")
    public void weixinjsoonPay(@PathVariable("jsoonPay") String jsonPay, HttpServletResponse response) {
        try {
            String result = "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta charset=\"utf-8\">\n" +
                    "<meta name=\"keywords\" content=\"\" />\n" +
                    "<meta name=\"description\" content=\"\" />\n" +
                    "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                    "<title>微信支付收银台</title>\n" +
                    "<meta content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" name=\"viewport\">\n" +
                    "<link rel=\"shortcut icon\" href=\"https://wx.gtimg.com/core/favicon.ico\" type=\"image/x-icon\"/>\n" +
                    "<link href=\"https://wx.gtimg.com\" rel=\"dns-prefetch\" />\n" +
                    "<link rel=\"stylesheet\" href=\"https://wx.gtimg.com/pay_h5/12306/pindex/css/pindex.css?v=20170921_1\">\n" +
                    "</head>\n" +
                    "\n" +
                    "                                                                                    \n" +
                    "                                 <body class=\"index  \" onclick=\"pgvWatchClick({coordinateId: 'logo'});\">\n" +
                    "        <div class=\"wrap order-info  \">\n" +
                    "        <div id=\"logo\" class=\"logo-wechatpay\"></div>\n" +
                    "        <div id=\"T_orderDetailContainer\" class=\"payment-qrcode  \">\n" +
                    "            <div class=\"data-record\">\n" +
                    "                <div class=\"order-amount\">\n" +
                    "                    <dl>\n" +
                    "                        <dt>%s订单</dt>\n" +
                    "                        <dd><span class=\"wechatrmb\">¥</span><span class=\"wechatnum\">%s</span></dd>\n" +
                    "                    </dl>\n" +
                    "                </div>\n" +
                    "                <div id=\"T_tradeInfo\" class=\"order-detail\">\n" +
                    "                    <!-- 交互说明\n" +
                    "                         1. 给order-detail添加show-datum,显示订单详情\n" +
                    "                    -->\n" +
                    "                    <!--<a class=\"switch\" id=\"T_showOrderInfoBtn\" href=\"javascript:;\">订单详情<i class=\"arrow arrow-in\"></i><i class=\"arrow arrow-out\"></i></a>-->\n" +
                    "                    <div class=\"datum\">\n" +
                    "                        <dl>\n" +
                    "                            <dt>收款方</dt>\n" +
                    "                            <dd>%s</dd>\n" +
                    "                        </dl>\n" +
                    "                        <dl>\n" +
                    "                            <dt>下单时间</dt>\n" +
                    "                            <dd>%s</dd>\n" +
                    "                        </dl>\n" +
                    "                        <dl>\n" +
                    "                            <dt>订单号</dt>\n" +
                    "                            <dd>%s</dd>\n" +
                    "                        </dl>\n" +
                    "                    </div>\n" +
                    "                                    </div>\n" +
                    "            </div>\n" +
                    "            <div id=\"T_qrContainer\" class=\"scan-qrcode\">\n" +
                    "                <div id=\"T_qrText\" class=\"qrcode-tit\">\n" +
                    "                                            订单将在30分钟后关闭，请及时付款\n" +
                    "                                    </div>\n" +
                    "                <div class=\"qrcode-img\">\n" +
                    "                    <div id=\"T_qrImg\">\n" +
                    "                    \t<img src=\"%s\"/>\n" +
                    "                        <div id=\"T_qrImgLoading\"></div>\n" +
                    "                    </div>\n" +
                    "                    <div id=\"T_qrRefreshBtn\" class=\"status hide\">\n" +
                    "                        <div class=\"icon\"></div>\n" +
                    "                        <div class=\"mask\"></div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <div class=\"qrcode-msg\">\n" +
                    "                    <p id=\"T_qrCodeMsg\">\n" +
                    "                                            请使用微信扫一扫完成支付\n" +
                    "                                        </p>\n" +
                    "                    <p class=\" hide\">若已经安装微信，可以尝试<a id=\"T_padStartApp\" href=\"javascript:;\">拉起微信支付</a></p>\n" +
                    "                </div>\n" +
                    "            </div>              \n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div id=\"T_paySuccContainer\" class=\"page-msg hide\">\n" +
                    "            <div class=\"icon-area\">\n" +
                    "                <i class=\"ico ico-succ\"></i>\n" +
                    "            </div>\n" +
                    "            <div class=\"text-area\">\n" +
                    "                <p class=\"major\">支付成功</p>\n" +
                    "                <p class=\"minor\"><span id=\"T_second\">5</span> 秒后自动返回商户</p>\n" +
                    "            </div>\n" +
                    "            <div class=\"oper-area\">\n" +
                    "                <a id=\"T_jumpBtn\" class=\"btn btn-primary\" href=\"javascript:;\">立即返回</a>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div id=\"T_timeoutContainer\" class=\"page-msg  hide\">\n" +
                    "            <div class=\"icon-area\">\n" +
                    "                <i class=\"ico ico-fail\"></i>\n" +
                    "            </div>\n" +
                    "            <div class=\"text-area\">\n" +
                    "                <p class=\"major\">交易超时失效</p>\n" +
                    "                <p class=\"minor  \">订单号：W20181222203652408</p>\n" +
                    "                <p class=\"minor\">当前订单的最晚付款时间为：<span id=\"T_orderDeadline\">2018-12-22 14:23:48</span>，目前已过期。请重新下单</p>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div id=\"T_errorContainer\" class=\"page-msg  hide\">\n" +
                    "            <div class=\"icon-area\">\n" +
                    "                <i class=\"ico ico-fail\"></i>\n" +
                    "            </div>\n" +
                    "            <div class=\"text-area\">\n" +
                    "                                    <p class=\"major\">订单未支付</p>\n" +
                    "                    <p class=\"minor  \">订单号：</p>\n" +
                    "                                <!--<div class=\"oper-area hide\">\n" +
                    "                    <a class=\"btn btn-primary\" id=\"T_errorOut\" href=\"javascript:;\">关闭</a>\n" +
                    "                </div>-->\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <input name=\"qrStatus\" type=\"hidden\" value=\"SUCCESS\" id=\"T_qrStatus\"/>\n" +
                    "        <input name=\"qrId\" type=\"hidden\" value=\"W20181222203652408\" id=\"T_qrId\"/>\n" +
                    "        <input name=\"qrCodeReqUrl\" type=\"hidden\" value=\"https://payapp.weixin.qq.com/t/getqr\" id=\"T_qrCodeReqUrl\"/>\n" +
                    "        <input name=\"qrCodeUrl\" type=\"hidden\" value=\"https://payapp.weixin.qq.com/t/qr?tp0i4qECT8aKjbEL8\" id=\"T_qrCodeUrl\"/>\n" +
                    "        <input name=\"qrStatusLoopUrl\" type=\"hidden\" value=\"https://payapp.weixin.qq.com/t/porderquery\" id=\"T_qrStatusLoopUrl\"/>\n" +
                    "        <input name=\"orderStatus\" type=\"hidden\" value=\"UNPAY\" id=\"T_orderStatus\"/>\n" +
                    "        <input name=\"orderTimeout\" type=\"hidden\" value=\"1785\" id=\"T_orderTimeout\" />\n" +
                    "        <input name=\"openLink\" type=\"hidden\" value=\"\" id=\"T_openLink\" />\n" +
                    "        <input name=\"deviceType\" type=\"hidden\" value=\"PC\" id=\"T_deviceType\" />\n" +
                    "        <input name=\"qrReturnUrl\" type=\"hidden\" value=\"\" id=\"T_qrReturnUrl\" />\n" +
                    "        <input name=\"qrCodeStatus\" type=\"hidden\" value=\"NO\" id=\"T_qrCodeStatus\" />\n" +
                    "        <input type=\"hidden\" name=\"qrCodeExpire\" value=\"300\" id=\"T_qrCodeExpire\" />\n" +
                    "        \n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"wrap jumps-info  hide\">\n" +
                    "        <div class=\"prompt\">\n" +
                    "            <div class=\"icon-area\">\n" +
                    "                <div class=\"ico-wechat\"></div>\n" +
                    "                <div id=\"T_jumpWait\" class=\"ani-loader\"></div>\n" +
                    "            </div>\n" +
                    "            <div class=\"text-area\">\n" +
                    "                <p id=\"T_jumpTips\">正在前往微信支付，请勿关闭</p>\n" +
                    "                <p id=\"T_jumpAlert\"></p>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class=\"remark\">切换<a id=\"T_jumpShowQrpay\" href=\"javascript:;\">二维码支付</a></div>\n" +
                    "    </div>\n" +
                    "        <script type=\"text/javascript\" src=\"https://wx.gtimg.com/third/jquery/jquery-1.11.2.min.js?v=20170905\"></script>\n" +
                    "   \n" +
                    "    <script type=\"text/javascript\">\n" +
                    "        if(typeof(pgvMain) == 'function') pgvMain();\n" +
                    "        if (typeof(pgvWatchClick) != 'function') {\n" +
                    "            pgvWatchClick = function() {}\n" +
                    "        }\n" +
                    "\n" +
                    "        if (typeof(wxgsdk) != 'undefined') {\n" +
                    "            wxgsdk.setBasicTime({\n" +
                    "                pid : 260\n" +
                    "            });\n" +
                    "            wxgsdk.send();\n" +
                    "        }\n" +
                    "    </script>\n" +
                    "    <noscript><h1 style=\"color:red\">您的浏览器不支持JavaScript，请更换浏览器或开启JavaScript设置!</h1></noscript>\n" +
                    "</body>\n" +
                    "</html>";
            //result = RedisHelper.get(jsonPay);



            // 查询订单
            Order order = new Order();
            //order.setOrderCode(params.get("out_trade_no"));
            List<Order> list = this.orderService.findAllOrder(order);
            for(int i=0;i<list.size();i++){
                if(list.get(i).getOrderCode().equals(jsonPay)){
                    order=list.get(i);
                    break;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ordertime=sdf.format(order.getCreateTime());


            //xx套餐 xx金额 xx收款方 xx下单时间 xx 订单号 xx 二维码地址
            String output = String.format(result,"会员"+ order.getRechargeCycle()
                    ,order.getRechargeMoney()
                    ,"随州市尚亿专用现车公司"
                    ,ordertime
                    ,jsonPay
                    ,"http://qiwebdd.shangyixx.com/wxpay/precreate/order?code="+jsonPay
                    );
//            String output = String.format("%s = %d", "joe", 35);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(output);
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
            if (goods.getVipMoney() != null && !goods.getVipMoney().equals("")){
                //普通VIP价格
                order.setRechargeMoney(goods.getVipMoney());
            }
//            if (goods.getBusinessMoney() != null && !goods.getBusinessMoney().equals("")){
//                //商户VIP价格
//                order.setRechargeMoney(goods.getBusinessMoney());
//            }

            //订单号
            order.setOrderCode(getOrderIdByTime());
            this.orderService.addOrder(order);


            //////
            // 金额保留两位
//            money = (float) (Math.round(money * 100)) / 100;
//
//            // 生成订单
//            OrderInfo orderInfo = orderInfoService.createOrder(subject, body, money, aliPayConfig.getSellerId());

            //支付宝生成订单，微信单独
            if("alipay".equals(params.get("payment_method"))) {
                // 1、设置请求参数
                AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
                // 页面跳转同步通知页面路径
                alipayRequest.setReturnUrl(aliPayConfig.return_url);
                // 服务器异步通知页面路径
                alipayRequest.setNotifyUrl(aliPayConfig.notify_url);

                // 2、SDK已经封装掉了公共参数，这里只需要传入业务参数，请求参数查阅开头Wiki
                Map<String, String> map = new HashMap<>(16);
                map.put("out_trade_no", order.getOrderCode());

                if (goods.getVipMoney() != null && !goods.getVipMoney().equals("")) {
                    //普通VIP价格
                    map.put("total_amount", goods.getVipMoney());
                }
//            if (goods.getBusinessMoney() != null && !goods.getBusinessMoney().equals("")){
//                //商户VIP价格
//                map.put("total_amount", goods.getBusinessMoney());
//            }

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

            }else{
                response.setContentType("text/html;charset=utf-8");
                return ResponseBo.ok(order.getOrderCode());
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
        /**
         *
         * values:::[Ljava.lang.String;@298e1901
         * name:::charset
         * valueStr--11111111111--------************------------:utf-8
         * values:::[Ljava.lang.String;@3953bdc4
         * name:::out_trade_no
         * valueStr--11111111111--------************------------:20181223114133795
         * values:::[Ljava.lang.String;@471cdb34
         * name:::method
         * valueStr--11111111111--------************------------:alipay.trade.page.pay.return
         * values:::[Ljava.lang.String;@67eeb42e
         * name:::total_amount
         * valueStr--11111111111--------************------------:55.00
         * values:::[Ljava.lang.String;@3e71ffc3
         * name:::sign
         * valueStr--11111111111--------************------------:M9MPnQRtnS36Rj+R/V1lN6xMM2aDxWog/yeRIBx19s5EPvKipasO9Dmz7fsKlOBwJFCTIN4Y6NCF3s5FxA7Ynrg3yfhmX3pYSHSPeoXgAjDqCKFd2NmBYgEtfHQRQhtJ6xyIejae8Q6FKwUIieaOiuSs117Bik/1jYStG2IGJslBMsoP1AMS1ixE4LzN9hQTOGpjbZ9tV2U61jccn0tO0AWiN8RdZwAASrcHVlC7UR9QR8D4SRh9iRQrkb1hfzAgbchdNT8+3twIDXOMyJ8KgiCDrJfKdv4I7hVUG4eNFX4xxhFivj9kElOnXjUFU7gIe0gCgEJPjcKgDWOUpfN5Ww==
         * values:::[Ljava.lang.String;@1a68d854
         * name:::trade_no
         * valueStr--11111111111--------************------------:2018122322001408120500664013
         * values:::[Ljava.lang.String;@3b0c7556
         * name:::auth_app_id
         * valueStr--11111111111--------************------------:2016092000553510
         * values:::[Ljava.lang.String;@29d1f9dd
         * name:::version
         * valueStr--11111111111--------************------------:1.0
         * values:::[Ljava.lang.String;@18ef84c3
         * name:::app_id
         * valueStr--11111111111--------************------------:2016092000553510
         * values:::[Ljava.lang.String;@449955f2
         * name:::sign_type
         * valueStr--11111111111--------************------------:RSA2
         * values:::[Ljava.lang.String;@1c672ef2
         * name:::seller_id
         * valueStr--11111111111--------************------------:2088102176445206
         * values:::[Ljava.lang.String;@3a5e33b2
         * name:::timestamp
         * valueStr--11111111111--------************------------:2018-12-23 11:42:32
         * 11:46:45.378 sms [QuartzScheduler_MyScheduler-MAX2018121545534983426_ClusterManager] ERROR c.a.druid.filter.stat.StatFilter - slow sql 5038 millis. SELECT * FROM QRTZ_SCHEDULER_STATE WHERE SCHED_NAME = 'MyScheduler'[]
         * 11:46:45.380 sms [MyScheduler_QuartzSchedulerThread] ERROR c.a.druid.filter.stat.StatFilter - slow sql 5040 millis. SELECT TRIGGER_NAME, TRIGGER_GROUP, NEXT_FIRE_TIME, PRIORITY FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_STATE = ? AND NEXT_FIRE_TIME <= ? AND (MISFIRE_INSTR = -1 OR (MISFIRE_INSTR != -1 AND NEXT_FIRE_TIME >= ?)) ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC["WAITING",1545536828329,1545536788340]
         */
        boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.alipay_public_key, aliPayConfig.charset, aliPayConfig.sign_type); // 调用SDK验证签名

        // 返回界面
        if (signVerified) {
            System.out.println("前往支付成功页面");
            // 支付成功后操作
            // 查询订单
            Order order = new Order();
            //order.setOrderCode(params.get("out_trade_no"));
            List<Order> list = this.orderService.findAllOrder(order);
            for(int i=0;i<list.size();i++){
                if(list.get(i).getOrderCode().equals(params.get("out_trade_no"))){
                    order=list.get(i);
                    break;
                }
            }
//            order= this.orderService.findOrderProfile(order);
            //order= orderService.queryOrderById(order);
            updateUserAndOrder(order,super.getCurrentUser(),true);

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

    /***
     * 支付成功后 更新用户和订单信息
     * 订单
     * 用户
     * 支付成功状态
     */
    public void updateUserAndOrder(Order order,User user ,boolean payState){
        //查询订单


        //支付成功
        if(payState){
            //更新支付日期 到期日期 支付状态
            order.setPayTime(new Date());
            order.setPayStatus("1");//已支付
            //更新用户表 用户到期状态 和到期日期VIP状态 0：未过期 ，1：已过期
            //user.setVipstatus("0");//未到期
        }

        orderService.updateUserAndOrder(order,user );
    }

    /**
     *  根据订单号查询订单
     * @param orderCode
     * @return
     */

    @RequestMapping("order/getOrderByCode")
    @ResponseBody
    public ResponseBo getOrderByCode(String orderCode) {
        try {
            Order order = new Order();
//            order.setOrderCode(orderCode);
            List<Order> list = this.orderService.findAllOrder(order);
            for(int i=0;i<list.size();i++){
                if(list.get(i).getOrderCode().equals(orderCode)){
                    order=list.get(i);
                    break;
                }
            }
            return ResponseBo.ok(order);
        } catch (Exception e) {
            log.error("获取订单信息失败", e);
            return ResponseBo.error("获取订单信息失败，请联系网站管理员！");
        }
    }

}