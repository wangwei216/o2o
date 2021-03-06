package com.imooc.o2o.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;

import java.io.IOException;
import java.util.List;

public interface AreaService {
	/*
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	List<Area> getAreaList() throws JsonParseException, JsonMappingException,
			IOException;

	/*
	 * 
	 * @param area
	 * @return
	 */
	AreaExecution addArea(Area area);

	/*
	 * 
	 * @param area
	 * @return
	 */
	AreaExecution modifyArea(Area area);

	/*
	 * 
	 * @param areaId
	 * @return
	 */
	AreaExecution removeArea(long areaId);

	/*
	 * 
	 * @param areaIdList
	 * @return
	 */
	AreaExecution removeAreaList(List<Long> areaIdList);

}
