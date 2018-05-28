package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;

import java.util.List;

//这个是要实现区域实体功能的DAO接口
public interface AreaDao {

    //这个是列出区域列表
    //TODO 通过测试
    List<Area> queryArea();

    /*
     * 插入区域信息
     * @param area
     * @return
     */
    //TODO 通过测试
    int insertArea(Area area);

    /*
     * 修改区域信息
     * @param area
     * @return
     */
    //TODO 通过测试
    int updateArea(Area area);

    /*
     * 根据区域id去删除区域信息
     * @param areaId
     * @return
     */
    //TODO 通过测试
    int deleteArea(long areaId);

    /*
     *  这个是批量删除区域信息
     * @param areaIdList
     * @return
     */
    //TODO 通过测试
    int batchDeleteArea(List<Long> areaIdList);

}
