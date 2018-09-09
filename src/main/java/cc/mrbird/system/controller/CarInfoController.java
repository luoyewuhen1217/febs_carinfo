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

import java.util.ArrayList;
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
    public Map<String, Object> userList(QueryRequest request, CarInfo carInfo) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<CarInfo> list = this.carInfoService.findAllCarInfos(carInfo);
        PageInfo<CarInfo> pageInfo = new PageInfo<>(list);
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
