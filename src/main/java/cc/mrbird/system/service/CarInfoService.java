package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.CarInfo;
import cc.mrbird.system.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "CarInfoService")
public interface CarInfoService extends IService<CarInfo> {
    List<CarInfo> findAllCarInfos(CarInfo carinfo);

}
