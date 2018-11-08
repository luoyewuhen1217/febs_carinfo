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
	private Long goodsid;

	@Column(name = "GOODS_MONEY")
	@ExportConfig(value = "商品价格")
	private String goodsmoney;

	@Column(name = "GOODS_CYCLE")
	private String goodscycle;

	@Column(name = "REMARK")
	private String remark;



	@Column(name = "CREATE_TIME")
	@ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
	private Date createTime;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsmoney() {
		return goodsmoney;
	}

	public void setGoodsmoney(String goodsmoney) {
		this.goodsmoney = goodsmoney;
	}

	public String getGoodscycle() {
		return goodscycle;
	}

	public void setGoodscycle(String goodscycle) {
		this.goodscycle = goodscycle;
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

	// 用于搜索条件中的时间字段
	@Transient
	private String timeField;

	@Transient
	private String timeField1;

	@Transient
	private String timeField2;




}