package cc.mrbird.member.controller;


import cc.mrbird.common.controller.BaseController;
import cc.mrbird.member.config.WXPayClient;
import cc.mrbird.member.domain.Order;
import cc.mrbird.member.service.OrderService;
import cc.mrbird.member.util.PayUtil;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.UserService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信支付-扫码支付.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2018/6/18
 */
@Slf4j
@RestController
@RequestMapping("/wxpay/precreate")
public class WXPayPrecreateController extends BaseController {
    @Autowired
    private WXPay wxPay;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WXPayClient wxPayClient;

    /**
     * 扫码支付 - 统一下单
     * 相当于支付不的电脑网站支付
     * 微信支付二维码
     * <a href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1">扫码支付API</a>
     */
    @RequestMapping("/order")
    public void precreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> reqData = new HashMap<>();
        String xx=String.valueOf(System.nanoTime());

        String orderNo=request.getParameter("code");
        System.out.println("时间："+xx+"   订单号："+orderNo);

        // 查询订单
        Order order = new Order();
        //order.setOrderCode(params.get("out_trade_no"));
        List<Order> list = this.orderService.findAllOrder(order);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getOrderCode().equals(orderNo)){
                order=list.get(i);
                break;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderTime = sdf.format(order.getCreateTime());

        BigDecimal bdMoney=new BigDecimal(order.getRechargeMoney());
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bdMoney=bdMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
        reqData.put("out_trade_no", orderNo);
        reqData.put("trade_type", "NATIVE");
        reqData.put("product_id", "1");
        reqData.put("time_start", orderTime);//生成日期
        //reqData.put("time_expire", "20181216145210"); 失效日期
        //reqData.put("product_id", "1");
        //reqData.put("product_id", "1");

        reqData.put("nonce_str", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS"); //随机字符串，不长于32位。推荐随机数生成算法
        reqData.put("body", "购买会员"+order.getRechargeCycle());
        // 订单总金额，单位为分
        reqData.put("total_fee", bdMoney.toString());//101 金额 单位是分 所以要把金额*100
        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        reqData.put("spbill_create_ip", "223.167.169.162");
        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        reqData.put("notify_url", "http://qiwebdd.shangyixx.com/wxpay/precreate/notify");
        // 自定义参数, 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        reqData.put("device_info", "");
        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
        reqData.put("attach", orderNo);

        /**
         * {
         * code_url=weixin://wxpay/bizpayurl?pr=vvz4xwC,
         * trade_type=NATIVE,
         * return_msg=OK,
         * result_code=SUCCESS,
         * return_code=SUCCESS,
         * prepay_id=wx18111952823301d9fa53ab8e1414642725
         * }
         */
        System.out.println("微信下单请求报文参数："+reqData.toString());
        Map<String, String> responseMap = wxPay.unifiedOrder(reqData);

        System.out.println("微信下单返回报文："+responseMap.toString());
        //logo(responseMap.toString());
        String returnCode = responseMap.get("return_code");
        String resultCode = responseMap.get("result_code");
        if (WXPayConstants.SUCCESS.equals(returnCode) && WXPayConstants.SUCCESS.equals(resultCode)) {
            String prepayId = responseMap.get("prepay_id");
            String codeUrl = responseMap.get("code_url");

            BufferedImage image = PayUtil.getQRCodeImge(codeUrl);

            response.setContentType("image/jpeg");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setIntHeader("Expires",-1);
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }

    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/notify")
    public void precreateNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> reqData = wxPayClient.getNotifyParameter(request);
            System.out.println("微信通知支付成功回调了。。。"+reqData.toString());
        /**
         * {
         * transaction_id=4200000138201806180751222945,
         * nonce_str=aaaf3fe4d3aa44d8b245bc6c97bda7a8,
         * bank_type=CFT,
         * openid=xxx,
         * sign=821A5F42F5E180ED9EF3743499FBCF13,
         * fee_type=CNY,
         * mch_id=xxx,
         * cash_fee=1,
         * out_trade_no=186873223426017,
         * appid=xxx,
         * total_fee=1,
         * trade_type=NATIVE,
         * result_code=SUCCESS,
         * time_end=20180618131247,
         * is_subscribe=N,
         * return_code=SUCCESS
         * }
         *
         *
         *
         *  s
         */
        //log.info(reqData.toString());
        System.out.println(reqData.toString());
        // 支付成功后操作
        // 查询订单
        Order order = new Order();
        //order.setOrderCode(params.get("out_trade_no"));
        List<Order> list = this.orderService.findAllOrder(order);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getOrderCode().equals(reqData.get("out_trade_no"))){
                order=list.get(i);
                break;
            }
        }
//            order= this.orderService.findOrderProfile(order);
        //order= orderService.queryOrderById(order);
        //updateUserAndOrder(order,super.getCurrentUser(),true);
        //更新支付日期 到期日期 支付状态
        order.setPayTime(new Date());
        order.setPayStatus("1");//已支付
        //更新用户表 用户到期状态 和到期日期VIP状态 0：未过期 ，1：已过期
        //user.setVipstatus("0");//未到期


        User user = new User();
                    user.setUserId(order.getUserId());
        user=    userService.findUserProfile(user);

        orderService.updateUserAndOrder(order,user );

        // 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
        boolean signatureValid = wxPay.isPayResultNotifySignatureValid(reqData);
        if (signatureValid) {
            /**
             * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
             * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
             * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
             * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
             */

            Map<String, String> responseMap = new HashMap<>(2);
            responseMap.put("return_code", "SUCCESS");
            responseMap.put("return_msg", "OK");
            String responseXml = WXPayUtil.mapToXml(responseMap);

            response.setContentType("text/xml");
            response.getWriter().write(responseXml);
            response.flushBuffer();
        }
    }
}
