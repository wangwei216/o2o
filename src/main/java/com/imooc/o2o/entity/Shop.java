package com.imooc.o2o.entity;

import java.util.Date;
import java.util.List;

public class Shop {

	private int shopId;
	private int ownerId;
	private int shopCategoryId;
	private String shopName;
	private String shopDesc;
	private String shopAddr;
	private String phone;
	private String shopImg;
	private Double longitude;
	private Double latitude;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;
	private String advice;
	/*因为你需要把其他几个实体对象都要可以通过shop对象去拿到其他实体的属性，需要把其他几个实体的
	* 的字段名给当成shop实体中属性，这样就可以通过shop实体中去拿到了别的实体的属性*/
	private Area area;
	private ShopCategory shopCategory;
	private PersonInfo personInfo;




	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	private List<ShopAuthMap> staffList;
	private ShopCategory parentCategory;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(int shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}

	public String getShopAddr() {
		return shopAddr;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	public List<ShopAuthMap> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<ShopAuthMap> staffList) {
		this.staffList = staffList;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String toString() {
		return "[shopId=" + shopId + ", shopName=" + shopName + "]";
	}

	public ShopCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ShopCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

}
