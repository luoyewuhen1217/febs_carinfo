package cc.mrbird.member.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_goods")
public class Goods implements Serializable {

	private static final long serialVersionUID = -4852732617765810959L;

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "GOODS_ID")
	private Long goodsId;

	@Column(name = "VIPTYPE")
	@ExportConfig(value = "VIP类型 1:个人用户，2:商户")
	private String vipType;

	@Column(name = "VIP_MONEY")
	@ExportConfig(value = "普通用户价格")
	private String vipMoney;

	@Column(name = "BUSINESS_MONEY")
	@ExportConfig(value = "商户价格")
	private String businessMoney;

	@Column(name = "GOODS_CYCLE")
	@ExportConfig(value = "套餐周期（1天/1个月/3个月/6个月/1年/3年/5年）")
	private String goodsCycle;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATE_TIME")
	@ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
	private Date createTime;



	// 用于搜索条件中的时间字段
	@Transient
	private String timeField;

	@Transient
	private String timeField1;

	@Transient
	private String timeField2;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getVipMoney() {
		return vipMoney;
	}

	public void setVipMoney(String vipMoney) {
		this.vipMoney = vipMoney;
	}

	public String getBusinessMoney() {
		return businessMoney;
	}

	public void setBusinessMoney(String businessMoney) {
		this.businessMoney = businessMoney;
	}

	public String getGoodsCycle() {
		return goodsCycle;
	}

	public void setGoodsCycle(String goodsCycle) {
		this.goodsCycle = goodsCycle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTimeField() {
		return timeField;
	}

	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}

	public String getTimeField1() {
		return timeField1;
	}

	public void setTimeField1(String timeField1) {
		this.timeField1 = timeField1;
	}

	public String getTimeField2() {
		return timeField2;
	}

	public void setTimeField2(String timeField2) {
		this.timeField2 = timeField2;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	@Override
	public String toString() {
		return "Goods{" + "goodsId=" + goodsId + ", vipType='" + vipType + '\'' + ", vipMoney='" + vipMoney + '\'' + ", businessMoney='" + businessMoney + '\'' + ", goodsCycle='" + goodsCycle + '\'' + ", remark='" + remark + '\'' + ", createTime=" + createTime + ", timeField='" + timeField + '\'' + ", timeField1='" + timeField1 + '\'' + ", timeField2='" + timeField2 + '\'' + '}';
	}
}