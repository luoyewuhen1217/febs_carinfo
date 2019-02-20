package cc.mrbird.member.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.MD5Utils;

import cc.mrbird.member.dao.GoodsMapper;
import cc.mrbird.member.domain.Goods;
import cc.mrbird.member.service.GoodsService;
import cc.mrbird.system.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("goodsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GoodsServiceImpl extends BaseService<Goods> implements GoodsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsMapper goodsMapper;

   // @Autowired
    //private GoodsRoleMapper goodsRoleMapper;

    //@Autowired
    //private GoodsRoleService goodsRoleService;//

    @Override
    public List<Goods> findAllGoods(Goods goods, User user) {

        try {
//            Example example = new Example(Goods.class);
////            if (StringUtils.isNotBlank(role.getRoleName())) {
////                example.createCriteria().andCondition("role_name=", role.getRoleName());
////            }
//            example.setOrderByClause("create_time");
//            return this.selectByExample(example);
            Example example = new Example(Goods.class);
            Example.Criteria criteria = example.createCriteria();
            goods.setVipType(user.getVipType());
            return this.goodsMapper.findAllGoods(goods);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Goods findByName(String goodsName) {
        Example example = new Example(Goods.class);
        example.createCriteria().andCondition("lower(goodsname)=", goodsName.toLowerCase());
        List<Goods> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Goods> findGoodsWithDept(Goods goods) {
        try {
            if (StringUtils.isNotBlank(goods.getTimeField())) {
                String[] timeArr = goods.getTimeField().split("~");
                String timeField1 = timeArr[0]+" 00:00:00";
                String timeField2 = timeArr[1]+" 23:59:59";
                goods.setTimeField1(timeField1);
                goods.setTimeField2(timeField2);
            }
            return this.goodsMapper.findGoodsWithDept(goods);
        } catch (Exception e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void registGoods(Goods goods) {
        goods.setCreateTime(new Date());
//        goods.setTheme(Goods.DEFAULT_THEME);
//        goods.setAvatar(Goods.DEFAULT_AVATAR);
//        goods.setSsex(Goods.SEX_UNKNOW);
//        goods.setPassword(MD5Utils.encrypt(goods.getGoodsname(), goods.getPassword()));
//        this.save(goods);
//        GoodsRole ur = new GoodsRole();
//        ur.setGoodsId(goods.getGoodsId());
//        ur.setRoleId(3L);
//        this.goodsRoleMapper.insert(ur);
    }

    @Override
    @Transactional
    public void updateTheme(String theme, String goodsName) {
        Example example = new Example(Goods.class);
        example.createCriteria().andCondition("goodsname=", goodsName);
        Goods goods = new Goods();
        //goods.setTheme(theme);
        this.goodsMapper.updateByExampleSelective(goods, example);
    }

    @Override
    @Transactional
    public void addGoods(Goods goods, Long[] roles) {
        goods.setCreateTime(new Date());
//        goods.setTheme(Goods.DEFAULT_THEME);
//        goods.setAvatar(Goods.DEFAULT_AVATAR);
//        goods.setPassword(MD5Utils.encrypt(goods.getGoodsname(), goods.getPassword()));
        this.save(goods);
        setGoodsRoles(goods, roles);
    }

    private void setGoodsRoles(Goods goods, Long[] roles) {
//        Arrays.stream(roles).forEach(roleId -> {
//            GoodsRole ur = new GoodsRole();
//            ur.setGoodsId(goods.getGoodsId());
//            ur.setRoleId(roleId);
//            this.goodsRoleMapper.insert(ur);
//        });
    }

    @Override
    @Transactional
    public void updateGoods(Goods goods) {
//        goods.setPassword(null);
//        goods.setGoodsname(null);
//        goods.setModifyTime(new Date());
        this.updateNotNull(goods);
        Example example = new Example(Goods.class);
        example.createCriteria().andCondition("goods_id=", goods.getGoodsId());
        this.goodsMapper.updateByExample(goods,example);
//        setGoodsRoles(goods, roles);
    }

    @Override
    @Transactional
    public void deleteGoodss(String goodsIds) {
        List<String> list = Arrays.asList(goodsIds.split(","));
        this.batchDelete(list, "goodsId", Goods.class);

        //this.goodsRoleService.deleteGoodsRolesByGoodsId(goodsIds);
    }

    @Override
    public void updateLoginTime(String goodsName) {

    }

    @Override
    public void updatePassword(String password) {

    }

//    @Override
//    @Transactional
//    public void updateLoginTime(String goodsName) {
//        Example example = new Example(Goods.class);
//        example.createCriteria().andCondition("lower(goodsname)=", goodsName.toLowerCase());
//        Goods goods = new Goods();
//        goods.setLastLoginTime(new Date());
//        this.goodsMapper.updateByExampleSelective(goods, example);
//    }
//
//    @Override
//    @Transactional
//    public void updatePassword(String password) {
//        Goods goods = (Goods) SecurityUtils.getSubject().getPrincipal();
//        Example example = new Example(Goods.class);
//        example.createCriteria().andCondition("goodsname=", goods.getGoodsname());
//        String newPassword = MD5Utils.encrypt(goods.getGoodsname().toLowerCase(), password);
//        goods.setPassword(newPassword);
//        this.goodsMapper.updateByExampleSelective(goods, example);
//    }

    @Override
    public Goods findById(Long goodsId) {
//        List<Goods> list = this.goodsMapper.find(goodsId);
        Example example = new Example(Goods.class);
        example.createCriteria().andCondition("goods_id=", goodsId);
        List<Goods> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Goods findGoodsProfile(Goods goods) {
        return this.goodsMapper.findGoodsProfile(goods);
    }

    @Override
    @Transactional
    public void updateGoodsProfile(Goods goods) {
//        goods.setGoodsname(null);
//        goods.setPassword(null);
//        if (goods.getDeptId() == null) goods.setDeptId(0L);
//        this.updateNotNull(goods);
    }

}
