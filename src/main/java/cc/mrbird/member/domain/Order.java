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
    private Long orderId;

    @Column(name = "ORDER_CODE")
    @ExportConfig(value = "订单号")
    private String orderCode;

    @Column(name = "USER_ID")
    private Long userId;

    @ExportConfig(value = "支付状态（1已支付/2未支付/3支付失败）")
    @Column(name = "PAYSTATUS")
    private String payStatus;

    @ExportConfig(value = "用户名")
    @Column(name = "USERNAME")
    private String userName;

    @ExportConfig(value = "支付方式（1支付宝/2微信）")
    @Column(name = "PAYMENT")
    private String payMent;

    @Column(name = "GOODS_ID")
    private Long goodsId;

    @Column(name = "RECHARGE_MONEY")
    @ExportConfig(value = "商品价格")
    private String rechargeMoney;

    @Column(name = "RECHARGE_CYCLE")
    @ExportConfig(value = "充值周期（1天/1个月/3个月/6个月/1年/3年/5年）")
    private String rechargeCycle;


    @Column(name = "CREATE_TIME")
    @ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
    private Date createTime;

    @Column(name = "PAY_TIME")
    @ExportConfig(value = "支付时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
    private Date payTime;

    @Column(name = "EXPIRY_TIME")
    @ExportConfig(value = "到期时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
    private Date expiryTime;

    // 用于搜索条件中的时间字段
    @Transient
    private String timeField;

    @Transient
    private String timeField1;

    @Transient
    private String timeField2;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayMent() {
        return payMent;
    }

    public void setPayMent(String payMent) {
        this.payMent = payMent;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getRechargeCycle() {
        return rechargeCycle;
    }

    public void setRechargeCycle(String rechargeCycle) {
        this.rechargeCycle = rechargeCycle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
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
}