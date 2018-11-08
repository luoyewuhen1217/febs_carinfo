package cc.mrbird.member.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_order")
public class Order implements Serializable {

	private static final long serialVersionUID = -4852732617765810959L;

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "ORDER_ID")
	private Long orderid;

	@Column(name = "ORDER_CODE")
	@ExportConfig(value = "订单号")
	private String ordercode;

	@Column(name = "USER_ID")
	private Long userid;

	@ExportConfig(value = "支付状态（1已支付/2未支付/3支付失败）")
	@Column(name = "PAYSTATUS")
	private String paystatus;

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	@ExportConfig(value = "用户名")
	@Column(name = "USERNAME")
	private String username;

	@ExportConfig(value = "支付方式（1支付宝/2微信）")
	@Column(name = "PAYMENT")
	private String payment;

	@Column(name = "GOODS_ID")
	private Long goodsid;

	@Column(name = "RECHARGE_MONEY")
	@ExportConfig(value = "商品价格")
	private String rechargemoney;

	@Column(name = "RECHARGE_CYCLE")
	@ExportConfig(value = "充值周期（1天/1个月/3个月/6个月/1年/3年/5年）")
	private String rechargecycle;


	@Column(name = "CREATE_TIME")
	@ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
	private Date createTime;

	@Column(name = "PAY_TIME")
	@ExportConfig(value = "支付时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
	private Date paytime;

	@Column(name = "EXPIRY_TIME")
	@ExportConfig(value = "到期时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
	private Date expirytime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}

	public String getRechargemoney() {
		return rechargemoney;
	}

	public void setRechargemoney(String rechargemoney) {
		this.rechargemoney = rechargemoney;
	}

	public String getRechargecycle() {
		return rechargecycle;
	}

	public void setRechargecycle(String rechargecycle) {
		this.rechargecycle = rechargecycle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public Date getExpirytime() {
		return expirytime;
	}

	public void setExpirytime(Date expirytime) {
		this.expirytime = expirytime;
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