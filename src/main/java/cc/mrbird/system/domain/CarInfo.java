package cc.mrbird.system.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_carInfo")
public class CarInfo implements Serializable {

    private static final long serialVersionUID = -4852732617765810959L;
    /**
     * 性别
     */
    public static final String SEX_MALE = "0";

    public static final String SEX_FEMALE = "1";

    public static final String SEX_UNKNOW = "2";

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "CAR_ID")
    private Long carId;

    @Column(name = "VEHICLETYPE")
    @ExportConfig(value = "车辆类型")
    private String vehicleType;

    @Column(name = "CHASSISTRADEMARK")
    @ExportConfig(value = "底盘品牌")
    private String chassisTrademark;

    @Column(name = "ENGINETYPE")
    @ExportConfig(value = "发动机型号")
    private String engineType;

    @Column(name = "SQUAREQUANTITY")
    @ExportConfig(value = "方量")
    private BigDecimal squareQuantity;

    @Column(name = "NUMBER")
    @ExportConfig(value = "数量")
    private Integer number;

//    @Column(name = "VEHICLECLASSIFICATION")
//    @ExportConfig(value = "车辆分类")
//    private String vehicleClassification;
//
//    @Column(name = "WHEELBASE")
//    @ExportConfig(value = "轴距")
//    private Long wheelbase;

    @Column(name = "EMISSIONSTANDARD")
    @ExportConfig(value = "排放标准")
    private String emissionStandard;

    @Column(name = "REMARK")
    @ExportConfig(value = "备注（轴距，轮胎，等）")
    private String remark;

    @Column(name = "CRATE_TIME")
    @ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
    private Date crateTime;

    @Column(name = "MODIFY_TIME")
    @ExportConfig(value = "修改时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
    private Date modifyTime;

    @Column(name = "MANUFACTURER")
    @ExportConfig(value = "生产厂家")
    private String manufacturer;

    @Column(name = "CONTACTS")
    @ExportConfig(value = "联系人")
    private String contacts;

    @Column(name = "TEL")
    @ExportConfig(value = "联系电话")
    private String tel;

    @Column(name = "ADDRESS")
    @ExportConfig(value = "联系地址")
    private String address;

    @Column(name = "PRICE")
    @ExportConfig(value = "售价")
    private BigDecimal price;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getChassisTrademark() {
        return chassisTrademark;
    }

    public void setChassisTrademark(String chassisTrademark) {
        this.chassisTrademark = chassisTrademark;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public BigDecimal getSquareQuantity() {
        return squareQuantity;
    }

    public void setSquareQuantity(BigDecimal squareQuantity) {
        this.squareQuantity = squareQuantity;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getEmissionStandard() {
        return emissionStandard;
    }

    public void setEmissionStandard(String emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "carId=" + carId +
                ", vehicleType='" + vehicleType + '\'' +
                ", chassisTrademark='" + chassisTrademark + '\'' +
                ", engineType='" + engineType + '\'' +
                ", squareQuantity=" + squareQuantity +
                ", number=" + number +
                ", emissionStandard='" + emissionStandard + '\'' +
                ", remark='" + remark + '\'' +
                ", crateTime=" + crateTime +
                ", modifyTime=" + modifyTime +
                ", manufacturer='" + manufacturer + '\'' +
                ", contacts='" + contacts + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }
}