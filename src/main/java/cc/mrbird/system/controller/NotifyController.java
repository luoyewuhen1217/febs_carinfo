import org.springframework.stereotype.Controller;

@Controller
public class NotifyController {
//    @RequestMapping("notify")
//    public void notifyUrl(HttpServletRequest request) throws Exception {
//        // 获取支付宝POST过来反馈信息
//        Map<String, String> params = new HashMap<>();
//        Map<String, String[]> requestParams = request.getParameterMap();
//        // 签名验证
//        boolean sign = aliPayService.verifySign(params, requestParams);
//        request.setContentType("text/html;charset=" + AlipayConfig.charset);
//        // 验签成功，执行商户操作
//        if (sign) {
//            /**
//             * 实际验证过程建议商户务必添加以下校验：
//             * 1、验证app_id是否为该商户本身。
//             * 2、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
//             * 3、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）
//             * 4、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方
//             * （有的时候，一个商户可能有多个seller_id/seller_email）
//             */
//
//            /**
//             * <pre>
//             * 交易状态 {
//             *    TRADE_FINISHED:通知触发条件是商户签约的产品不支持退款功能的前提下，买家付款成功；
//             *    或者，商户签约的产品支持退款功能的前提下，交易已经成功并且已经超过可退款期限。
//             *
//             *    TRADE_SUCCESS:通知触发条件是商户签约的产品支持退款功能的前提下，买家付款成功；
//             * }
//             * </pre>
//             */
//            if ("trade_finished".equals(trade_status) || "trade_success".equals(trade_status)) {
//                // 执行商户操作
//                orderService.signSuccess(params, false, ClientUtil.getClientIP(request), user);
//            } else {
//                // 如果返回不是支付成功，将进行订单查询支付结果查询，当结果为支付成功时，重新执行商户操作
//                orderService.tradeQuery(out_trade_no, ClientUtil.getClientIP(request), user);
//            }
//            response.getWriter().write("success");
//
//        } else {
//            response.getWriter().write("fail");
//            // 调试用，写文本函数记录程序运行情况是否正常
//            String sWord = AlipaySignature.getSignCheckContentV1(params);
//            AlipayFunction.logResult(sWord);
//        }
//
//        response.getWriter().flush();
//        response.getWriter().close();
//    }
}
