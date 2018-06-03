package com.imooc.o2o.web.superadmin;

import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;
import com.imooc.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

	@Autowired
	private AreaService areaService;

	/*
	* 这个是查询区域信息的步骤
	* 	1，
	* */
	@RequestMapping(value = "/listareas", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listAreas() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("total", list);
			modelMap.put("rows", list.size());

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	/*
	* 这个是添加区域信息
	* */
	@RequestMapping(value = "/addarea",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> addArea(HttpServletRequest request){

		Map<String,Object> modelMap = new HashMap<String, Object>();
		Area area = null;
		/*area.setCreateTime(new Date());*/
		area.setAreaName("这个添加区域信息");
		area.setAreaDesc("区域信息描述");
		area.setPriority(30);
		if (area!=null){
			AreaExecution areaExecution = areaService.addArea(area);
			//这个是看执行service方法的时候返回的状态码是不是和这个相同
			if (areaExecution.getState()== AreaStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}
			else {
				modelMap.put("success",false);
				modelMap.put("errMsg",areaExecution.getStateInfo());
			}
		}
		else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请输入区域信息，且不能为空");
		}

			return modelMap;
	}

	/*
	* 这个是根据区域id去删除区域信息的
	* */
	/*@RequestMapping(value = "/removeareas",method = RequestMethod.POST)
	private Map<String,Object> removeArea(){
		Map<String,Object> modelMap = new HashMap<String, Object>();

	}*/

}
