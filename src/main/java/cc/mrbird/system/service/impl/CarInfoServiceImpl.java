package cc.mrbird.system.service.impl;

import cc.mrbird.system.domain.User;
import org.apache.commons.lang3.StringUtils;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.domain.CarInfo;
import cc.mrbird.system.service.CarInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service("carInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CarInfoServiceImpl extends BaseService<CarInfo> implements CarInfoService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private CarInfoMapper carInfoMapper;

    @Override
    public List<CarInfo> findAllCarInfos(CarInfo carinfo, User user) {
        try {
            Example example = new Example(CarInfo.class);
            Example.Criteria criteria = example.createCriteria();

            if (StringUtils.isNotBlank(carinfo.getVehicleType())) {
                criteria.andCondition("vehicleType=", carinfo.getVehicleType());
            }
            if (StringUtils.isNotBlank(carinfo.getChassisTrademark())) {
                criteria.andCondition("chassisTrademark like","%" + carinfo.getChassisTrademark() + "%");
            }
            if (StringUtils.isNotBlank(carinfo.getEngineType())) {
                criteria.andCondition("engineType like","%" + carinfo.getEngineType() + "%");
            }
            // 方量
            if (carinfo.getSquareQuantity() != null) {
                if (StringUtils.isNotBlank(carinfo.getSquareQuantity().toString())) {
                    criteria.andCondition("squareQuantity=", carinfo.getSquareQuantity());
                }
            }

            if (StringUtils.isNotBlank(carinfo.getRemark())) {
                criteria.andCondition("remark like","%" + carinfo.getRemark() + "%");
            }
            if (StringUtils.isNotBlank(carinfo.getManufacturer())) {
                criteria.andCondition("manufacturer like","%" + carinfo.getManufacturer() + "%");
            }
//            if (StringUtils.isNotBlank(carinfo.getSquareQuantity().toString())) {
//                criteria.andCondition("squareQuantity=", Long.valueOf(carinfo.getSquareQuantity().toString()));
//            }
//            if (StringUtils.isNotBlank(carinfo.getEmissionStandard())) {
//                criteria.andCondition("emissionStandard=", Long.valueOf(carinfo.getEmissionStandard()));
//            }
            if ("2".equals(user.getVipType())) { // 商户
                if ("1".equals(user.getVipStatus())) {// VIP状态 0：未过期 ，1：已过期
                    criteria.andCondition("remark <>",user.getUsername());
                }
            }


            example.setOrderByClause("ISTOP desc,CAR_ID desc");
            //example.setOrderByClause("CAR_ID");
            System.out.println("example3:::"+example.getCountColumn());
            return this.selectByExample(example);
        } catch (NumberFormatException e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }
}
