package cc.mrbird.member.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.FebsConstant;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.common.util.HttpUtils;
import cc.mrbird.common.util.MD5Utils;
import cc.mrbird.member.domain.Goods;
import cc.mrbird.member.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class GoodsController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;

    private static final String ON = "on";

    @RequestMapping("goods")
    @RequiresPermissions("goods:list")
    public String index(Model model) {
       // Goods goods = goodsService.findAllGoods(new Goods());//super.get();
        //model.addAttribute("goods", goods);
        return "member/pay/goods";
    }

    @Log("获取商品信息")
    @RequestMapping("goods/list")
    @ResponseBody
    public Map<String, Object> goodsList(QueryRequest request, Goods goods) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Goods> list = this.goodsService.findAllGoods(goods);
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }

    @Log("获取全部商品信息")
    @RequestMapping("goods/listall")
    @ResponseBody
    public List<Goods> goodsListAll(QueryRequest request, Goods goods) {
        List<Goods> list = this.goodsService.findAllGoods(goods);
        return list;
    }

    @RequestMapping("goods/checkGoodsName")
    @ResponseBody
    public boolean checkGoodsName(String goodsname, String oldgoodsname) {
        if (StringUtils.isNotBlank(oldgoodsname) && goodsname.equalsIgnoreCase(oldgoodsname)) {
            return true;
        }
        Goods result = this.goodsService.findByName(goodsname);
        return result == null;
    }

    @RequestMapping("goods/getGoods")
    @ResponseBody
    public ResponseBo getGoods(Long goodsId) {
        try {
            Goods goods = this.goodsService.findById(goodsId);

            return ResponseBo.ok(goods);
        } catch (Exception e) {
            log.error("获取商品失败", e);
            return ResponseBo.error("获取商品失败，请联系网站管理员！");
        }
    }

//    @Log("获取商品信息")
//    @RequestMapping("goods/list")
//    @ResponseBody
//    public Map<String, Object> goodsList(QueryRequest request, Goods goods) {
//        PageHelper.startPage(request.getPageNum(), request.getPageSize());
//        List<Goods> list = this.goodsService.findGoodsWithDept(goods);
//        PageInfo<Goods> pageInfo = new PageInfo<>(list);
//        return getDataTable(pageInfo);
//    }

    @RequestMapping("goods/excel")
    @ResponseBody
    public ResponseBo goodsExcel(Goods goods) {
        try {
            List<Goods> list = this.goodsService.findGoodsWithDept(goods);
            return FileUtils.createExcelByPOIKit("商品表", list, Goods.class);
        } catch (Exception e) {
            log.error("导出商品信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/csv")
    @ResponseBody
    public ResponseBo goodsCsv(Goods goods) {
        try {
            List<Goods> list = this.goodsService.findGoodsWithDept(goods);
            return FileUtils.createCsv("商品表", list, Goods.class);
        } catch (Exception e) {
            log.error("导出商品信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/regist")
    @ResponseBody
    public ResponseBo regist(Goods goods) {
        try {
            Goods result = this.goodsService.findByName(goods.getGoodscycle());
            if (result != null) {
                return ResponseBo.warn("该商品名已被使用！");
            }
            this.goodsService.registGoods(goods);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("注册失败", e);
            return ResponseBo.error("注册失败，请联系网站管理员！");
        }
    }

    @Log("更换主题")
    @RequestMapping("goods/theme")
    @ResponseBody
    public ResponseBo updateTheme(Goods goods) {
        try {
            //this.goodsService.updateTheme(goods.getTheme(), goods.getGoodsname());
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("修改主题失败", e);
            return ResponseBo.error();
        }
    }

    @Log("新增商品")
    @RequiresPermissions("goods:add")
    @RequestMapping("goods/add")
    @ResponseBody
    public ResponseBo addGoods(Goods goods, Long[] roles) {
        try {
//            if (ON.equalsIgnoreCase(goods.getStatus()))
//                goods.setStatus(Goods.STATUS_VALID);
//            else
//                goods.setStatus(Goods.STATUS_LOCK);
            this.goodsService.addGoods(goods, roles);
            return ResponseBo.ok("新增商品成功！");
        } catch (Exception e) {
            log.error("新增商品失败", e);
            return ResponseBo.error("新增商品失败，请联系网站管理员！");
        }
    }

    @Log("修改商品")
    @RequiresPermissions("goods:update")
    @RequestMapping("goods/update")
    @ResponseBody
    public ResponseBo updateGoods(Goods goods, Long[] rolesSelect) {
        try {
//            if (ON.equalsIgnoreCase(goods.getStatus()))
//                goods.setStatus(Goods.STATUS_VALID);
//            else
//                goods.setStatus(Goods.STATUS_LOCK);
            this.goodsService.updateGoods(goods, rolesSelect);
            return ResponseBo.ok("修改商品成功！");
        } catch (Exception e) {
            log.error("修改商品失败", e);
            return ResponseBo.error("修改商品失败，请联系网站管理员！");
        }
    }

    @Log("删除商品")
    @RequiresPermissions("goods:delete")
    @RequestMapping("goods/delete")
    @ResponseBody
    public ResponseBo deleteGoodss(String ids) {
        try {
            this.goodsService.deleteGoodss(ids);
            return ResponseBo.ok("删除商品成功！");
        } catch (Exception e) {
            log.error("删除商品失败", e);
            return ResponseBo.error("删除商品失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
//        Goods goods = getCurrentGoods();
//        String encrypt = MD5Utils.encrypt(goods.getGoodsname().toLowerCase(), password);
//        return goods.getPassword().equals(encrypt);
        return false;
    }

    @RequestMapping("goods/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String newPassword) {
        try {
            this.goodsService.updatePassword(newPassword);
            return ResponseBo.ok("更改密码成功！");
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return ResponseBo.error("更改密码失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/profile")
    public String profileIndex(Model model) {
//        Goods goods = super.getCurrentGoods();
//        goods = this.goodsService.findGoodsProfile(goods);
//        String ssex = goods.getSsex();
//        if (Goods.SEX_MALE.equals(ssex)) {
//            goods.setSsex("性别：男");
//        } else if (Goods.SEX_FEMALE.equals(ssex)) {
//            goods.setSsex("性别：女");
//        } else {
//            goods.setSsex("性别：保密");
//        }
//        model.addAttribute("goods", goods);
        return "system/goods/profile";
    }

    @RequestMapping("goods/getGoodsProfile")
    @ResponseBody
    public ResponseBo getGoodsProfile(Long goodsId) {
        try {
            Goods goods = new Goods();
//            goods.setGoodsId(goodsId);
            return ResponseBo.ok(this.goodsService.findGoodsProfile(goods));
        } catch (Exception e) {
            log.error("获取商品信息失败", e);
            return ResponseBo.error("获取商品信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/updateGoodsProfile")
    @ResponseBody
    public ResponseBo updateGoodsProfile(Goods goods) {
        try {
            this.goodsService.updateGoodsProfile(goods);
            return ResponseBo.ok("更新个人信息成功！");
        } catch (Exception e) {
            log.error("更新商品信息失败", e);
            return ResponseBo.error("更新商品信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/changeAvatar")
    @ResponseBody
    public ResponseBo changeAvatar(String imgName) {
        try {
            String[] img = imgName.split("/");
            String realImgName = img[img.length - 1];
//            Goods goods = getCurrentGoods();
//            goods.setAvatar(realImgName);
//            this.goodsService.updateNotNull(goods);
            return ResponseBo.ok("更新头像成功！");
        } catch (Exception e) {
            log.error("更换头像失败", e);
            return ResponseBo.error("更新头像失败，请联系网站管理员！");
        }
    }
}