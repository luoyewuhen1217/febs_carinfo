package cc.mrbird.member.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.member.domain.Goods;

import java.util.List;

public interface GoodsMapper extends MyMapper<Goods> {
    List<Goods> findAllGoods(Goods goods);
	List<Goods> findGoodsWithDept(Goods goods);
	
	//List<GoodsWithRole> findGoodsWithRole(Long goodsId);
	
	Goods findGoodsProfile(Goods goods);
}