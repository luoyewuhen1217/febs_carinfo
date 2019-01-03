package cc.mrbird.system.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.system.domain.CarInfo;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.CarInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CarInfoController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarInfoService carInfoService;

    @RequestMapping("carInfo")
    @RequiresPermissions("carInfo:list")
    public String index(Model model) {
        //User user = super.getCurrentUser();
        //CarInfo  carInfo=
        //model.addAttribute("carInfo", user);
        return "system/carInfo/carInfo";
    }



    @Log("获取车辆信息")
    @RequestMapping("carInfo/list")
    @ResponseBody
    public Map<String, Object> carInfoList(QueryRequest request, CarInfo carInfo) {
        PageInfo<CarInfo> pageInfo = null;
        try {
            PageHelper.startPage(request.getPageNum(), request.getPageSize());
            User user = super.getCurrentUser();
            String vipTime = user.getVipTime();// 到期时间
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
            String nowTimeString = sdf.format(new Date());//获取当前时间
            if (sdf.parse(nowTimeString).getTime() > sdf.parse(vipTime).getTime()) {
                System.out.println("当前时间大于到期时间");
                // 按照当期时间累加
                Calendar calendar =Calendar.getInstance();
                calendar.setTime(sdf.parse(nowTimeString));

                // 判断是选择的1天/1个月/3个月/6个月/1年/3年/5年  ------怎么获取？？？？？？？？？？？ ,获取到了以后根据当前时间来累加


                //calendar.add(calendar.DAY_OF_YEAR, 1);//增加一天,负数为减少一天
                //calendar.add(calendar.DAY_OF_MONTH, 1);//增加一天
                //calendar.add(calendar.DATE,1);//增加一天
                //calendar.add(calendar.WEEK_OF_MONTH, 1);//增加一个礼拜
                //calendar.add(calendar.WEEK_OF_YEAR,1);//增加一个礼拜
                calendar.add(calendar.MONTH,1);//增加一个月
                //calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
                nowTimeString = calendar.getTime().toString();

                System.out.println(nowTimeString);


            } else {
                System.out.println("当前时间小于到期时间");
                // 按照到期日期累加


            }

            List<CarInfo> list = this.carInfoService.findAllCarInfos(carInfo);
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDataTable(pageInfo);
    }
//    @Log("获取车辆信息")
//    @RequestMapping("carInfo/list")
//    @ResponseBody
//    public List<CarInfo> carInfoList(QueryRequest request, CarInfo carInfo) {
//        try {
//            return this.carInfoService.findAllCarInfos(carInfo);
//        } catch (Exception e) {
//            log.error("获取车辆信息集合失败", e);
//            return new ArrayList<>();
//        }
//    }


}
