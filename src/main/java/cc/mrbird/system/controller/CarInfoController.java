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

            List<CarInfo> list = this.carInfoService.findAllCarInfos(carInfo,user);
            if ("1".equals(user.getVipType()) || "2".equals(user.getVipType())) { //用户类型 1:普通用户，2:商户
                if ("1".equals(user.getVipStatus())) {// VIP状态 0：未过期 ，1：已过期
                    for (int i = 0; i < list.size(); i++) {
                        CarInfo carInfo1 = list.get(i);
                        carInfo1.setContacts("*");
                        carInfo1.setTel("*");
                        carInfo1.setAddress("*");
                        carInfo1.setPrice("*");
                    }

                }
            }

//            if(1){
//                fort lit
//                        cartinf get(i)
//                        cating .setcnme (cat.get.sf.sub(0.6+*****))
//            }

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
