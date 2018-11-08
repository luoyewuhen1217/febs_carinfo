package cc.mrbird.member.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.member.domain.Pay;
import cc.mrbird.system.domain.CarInfo;
import cc.mrbird.system.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "PayService")
public interface PayService extends IService<Pay> {
    List<Pay> findAllCarInfos(Pay pay);

}
