package cc.mrbird.job.task;


import cc.mrbird.common.annotation.CronTag;
import cc.mrbird.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@CronTag("updateUserVip")
public class UpdateUserVip {
    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }

    public void vipUser() {
        Map map = new HashMap();
        map.put("name","李四");
        log.info("每天定时执行，更新到期时间，正在被执行");
                userService.updateUserVip(map);
    }

}
