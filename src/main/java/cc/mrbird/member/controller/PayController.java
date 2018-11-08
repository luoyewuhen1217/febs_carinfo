package cc.mrbird.member.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.FebsConstant;
import cc.mrbird.common.util.HttpUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("会员充值续费")
    @RequestMapping("pay")
    @RequiresPermissions("pay:list")
    public String weather() {
        return "member/pay/pay";
    }

    @RequestMapping("pay/query")
    @ResponseBody
    public ResponseBo queryWeather(String areaId) {
        try {
            String data = HttpUtils.sendPost(FebsConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return ResponseBo.ok(data);
        } catch (Exception e) {
            log.error("查询天气失败", e);
            return ResponseBo.error("查询天气失败，请联系网站管理员！");
        }
    }
}
