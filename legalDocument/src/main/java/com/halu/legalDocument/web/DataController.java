package com.halu.legalDocument.web;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.halu.legalDocument.util.HBaseUtil;


@Controller
@RequestMapping("/test")
public class DataController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final String ISDO = "isDo";// 操作结果布尔型

	private final String MSG = "msg";// 操作成功返回信息

	private final String ERRORMSG = "errorMsg";// 操作失败返回信息

	@RequestMapping("/index")
	public ModelAndView index() {
		// System.out.println("test");
		return new ModelAndView("index");
	}

	/**
	 * 
	 * @Title: search
	 * @Description:关键字查询
	 * @param key
	 *            关键字
	 * @return
	 */
	@RequestMapping("/search")
	public HashMap<String, Object> search(String key) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		try {
//			/* hashMap = */HBaseUtil.scan(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@RequestMapping(value="/get",method=RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> get(Integer id) {
//		System.out.println(id);
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		HashMap<String, Object> resultHashMap = new HashMap<String,Object>();
		if (id == null || id < 0) {
			hashMap.put(ISDO, false);
			hashMap.put(ERRORMSG, "参数错误");
			return hashMap;
		}
		try {
			hashMap = HBaseUtil.get(id.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			hashMap.put(ISDO, false);
			hashMap.put(ERRORMSG, "查询失败");
			return hashMap;
		}
//		hashMap.put("result", hashMap);
		
		hashMap.put(ISDO, true);
		hashMap.put(MSG, "查询成功");
		return hashMap;
	}
	
	@RequestMapping("/scan")
	public @ResponseBody HashMap<String, Object> scan(){
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		HashMap<String, Object> resultHashMap = new HashMap<String,Object>();
		try {
			hashMap = HBaseUtil.scan();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			hashMap.put(ISDO, false);
			hashMap.put(ERRORMSG, "扫描失败");
			return hashMap;
		}
//		hashMap.put("result", hashMap);
		
		hashMap.put(ISDO, true);
		hashMap.put(MSG, "扫描成功");
		return hashMap;
//		return null;
	}

}
