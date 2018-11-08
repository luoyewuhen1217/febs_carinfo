package cc.mrbird.member.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.member.domain.Goods;

import java.util.List;

//@CacheConfig(cacheNames = "GoodsService")
public interface GoodsService extends IService<Goods> {

    Goods findById(Long goodsId);

    public List<Goods> findAllGoods(Goods goods);

    Goods findByName(String goodsName);

//    @Cacheable(key = "#p0")
    List<Goods> findGoodsWithDept(Goods goods);
//    @CacheEvict(key = "#p0", allEntries = true)
    void registGoods(Goods goods);

    void updateTheme(String theme, String goodsName);

//    @CacheEvict(allEntries = true)
    void addGoods(Goods goods, Long[] roles);

//    @CacheEvict(key = "#p0", allEntries = true)
    void updateGoods(Goods goods, Long[] roles);

//    @CacheEvict(key = "#p0", allEntries = true)
    void deleteGoodss(String goodsIds);

    void updateLoginTime(String goodsName);

    void updatePassword(String password);

    Goods findGoodsProfile(Goods goods);

    void updateGoodsProfile(Goods goods);
}
