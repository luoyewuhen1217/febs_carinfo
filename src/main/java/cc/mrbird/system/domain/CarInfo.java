package cc.mrbird.system.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
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
    @ExportConfig(value = "方量/箱体长度/臂长")
    private String squareQuantity;

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

//    @Column(name = "CRATE_TIME")
//    @ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
//    private Date crateTime;
//
//    @Column(name = "MODIFY_TIME")
//    @ExportConfig(value = "修改时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
//    private Date modifyTime;

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
    private String price;

    // 清洗吸污车  污水罐体容积（m³）
    @Column(name = "VOLUME1")
    @ExportConfig(value = "污水罐体容积（m³）")
    private String volume1;

    // 清洗吸污车  清水罐体容积（m³）
    @Column(name = "VOLUME2")
    @ExportConfig(value = "清水罐体容积（m³）")
    private String volume2;

    // 易燃液体厢式车 整车尺寸/吨位
    @Column(name = "VEHICLESIZE")
    @ExportConfig(value = "整车尺寸/吨位")
    private String vehicleSize;


    //  易燃液体厢式车 箱体尺寸
    @Column(name = "DIMENSION")
    @ExportConfig(value = "箱体尺寸（m³）")
    private String dimension;

    // 扫路车 水方
    @Column(name = "WATERSIDE")
    @ExportConfig(value = "水方")
    private String waterSide;

    // 扫路车 尘方 Dusts
    @Column(name = "DUSTS")
    @ExportConfig(value = "尘方")
    private String dusts;

    // 扫路车 作业宽度
    @Column(name = "JOBWIDTH")
    @ExportConfig(value = "作业宽度")
    private String jobWidth;

    // 泵长
    @Column(name = "PUMPLENGTH")
    @ExportConfig(value = "泵长")
    private String pumpLength;

    // 是否置顶
    @Column(name = "ISTOP")
    @ExportConfig(value = "是否置顶")
    private String isTop;

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

    public String getSquareQuantity() {
        return squareQuantity;
    }

    public void setSquareQuantity(String squareQuantity) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume1() {
        return volume1;
    }

    public void setVolume1(String volume1) {
        this.volume1 = volume1;
    }

    public String getVolume2() {
        return volume2;
    }

    public void setVolume2(String volume2) {
        this.volume2 = volume2;
    }

    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getWaterSide() {
        return waterSide;
    }

    public void setWaterSide(String waterSide) {
        this.waterSide = waterSide;
    }

    public String getDusts() {
        return dusts;
    }

    public void setDusts(String dusts) {
        this.dusts = dusts;
    }

    public String getJobWidth() {
        return jobWidth;
    }

    public void setJobWidth(String jobWidth) {
        this.jobWidth = jobWidth;
    }

    public String getPumpLength() {
        return pumpLength;
    }

    public void setPumpLength(String pumpLength) {
        this.pumpLength = pumpLength;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        return "CarInfo{" + "carId=" + carId + ", vehicleType='" + vehicleType + '\'' + ", chassisTrademark='" + chassisTrademark + '\'' + ", engineType='" + engineType + '\'' + ", squareQuantity='" + squareQuantity + '\'' + ", number=" + number + ", emissionStandard='" + emissionStandard + '\'' + ", remark='" + remark + '\'' + ", manufacturer='" + manufacturer + '\'' + ", contacts='" + contacts + '\'' + ", tel='" + tel + '\'' + ", address='" + address + '\'' + ", price='" + price + '\'' + ", volume1='" + volume1 + '\'' + ", volume2='" + volume2 + '\'' + ", vehicleSize='" + vehicleSize + '\'' + ", dimension='" + dimension + '\'' + ", waterSide='" + waterSide + '\'' + ", dusts='" + dusts + '\'' + ", jobWidth='" + jobWidth + '\'' + ", pumpLength='" + pumpLength + '\'' + ", isTop='" + isTop + '\'' + '}';
    }
}
