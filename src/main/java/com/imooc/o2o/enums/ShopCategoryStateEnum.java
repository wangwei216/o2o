package com.imooc.o2o.enums;


public enum ShopCategoryStateEnum {

	//这个是当用户对商品分类信息进行操作的时候，返回结果的时候容易出现的几种状态
	SUCCESS(0, "创建成功"),
	INNER_ERROR(-1001, "操作失败"),
	EMPTY(-1002, "区域信息为空");

	private int state;

	private String stateInfo;

	private ShopCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static ShopCategoryStateEnum stateOf(int index) {
		for (ShopCategoryStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
