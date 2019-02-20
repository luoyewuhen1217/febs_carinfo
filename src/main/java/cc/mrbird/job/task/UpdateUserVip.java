package cc.mrbird.job.task;


import cc.mrbird.common.annotation.CronTag;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
//        Map map = new HashMap();
//        map.put("userName","18612345678");
        List<User> userNameList = userService.findUserWithUserName();
        StringBuilder userNameBuilder = new StringBuilder();
        if (userNameList.size() > 0) {
//            userNameBuilder.append("(");
//            for (int i = 0; i < userNameList.size(); i++) {
//                if (i == userNameList.size() - 1)//当循环到最后一个的时候 就不添加逗号,
//                {
//                    userNameBuilder.append("'").append(userNameList.get(i).getUsername()).append("'");
//                } else {
//                    userNameBuilder.append("'").append(userNameList.get(i).getUsername());
//                    userNameBuilder.append("',");
//                }
//            }
//            userNameBuilder.append(")");
            log.info("每天定时执行，更新VIP状态,从vip用户变成非VIP，正在被执行");
            userService.updateUserVip(userNameList);
        }


    }

}
