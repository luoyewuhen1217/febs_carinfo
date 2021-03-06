package cc.mrbird.member.service.impl;

import cc.mrbird.member.domain.Pay;
import cc.mrbird.member.service.PayService;
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

@Service("PayService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PayServiceImpl extends BaseService<Pay> implements PayService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private CarInfoMapper carInfoMapper;

    @Override
    public List<Pay> findAllCarInfos(Pay carinfo) {
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
            example.setOrderByClause("ISTOP desc,CAR_ID desc");
            //example.setOrderByClause("CAR_ID");
            System.out.println("example3:::"+example.getCountColumn());
            return null;//this.selectByExample(example);
        } catch (NumberFormatException e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }
}
