package com.kym.ui.selectcity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * 
 * 城市代码
 * 
 * @author zd
 * 
 */
public class CitycodeUtil {

	private ArrayList<String> province_list = new ArrayList<String>();
	private ArrayList<String> city_list = new ArrayList<String>();
	private ArrayList<String> couny_list = new ArrayList<String>();
	public ArrayList<String> province_list_code = new ArrayList<String>();
	public ArrayList<String> city_list_code = new ArrayList<String>();
	public ArrayList<String> couny_list_code = new ArrayList<String>();
	/** 单例 */
	public static CitycodeUtil model;
	private Context context;

	private CitycodeUtil() {
	}

	public ArrayList<String> getProvince_list_code() {
		return province_list_code;
	}

	public ArrayList<String> getCity_list_code() {
		return city_list_code;
	}

	public void setCity_list_code(ArrayList<String> city_list_code) {
		this.city_list_code = city_list_code;
	}

	public ArrayList<String> getCouny_list_code() {
		return couny_list_code;
	}

	public void setCouny_list_code(ArrayList<String> couny_list_code) {
		this.couny_list_code = couny_list_code;
	}

	public void setProvince_list_code(ArrayList<String> province_list_code) {

		this.province_list_code = province_list_code;
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static CitycodeUtil getSingleton() {
		if (null == model) {
			model = new CitycodeUtil();
		}
		return model;
	}

	public ArrayList<String> getProvince(List<Province> provice) {
		if (province_list_code.size() > 0) {
			province_list_code.clear();
		}
		if (province_list.size() > 0) {
			province_list.clear();
		}
		for (int i = 0; i < provice.size(); i++) {
			province_list.add(provice.get(i).getName());
			province_list_code.add(provice.get(i).getPid());
		}
		return province_list;

	}

	public ArrayList<String> getCity(
			List<City> citylist, String provicecode) {
		if (city_list_code.size() > 0) {
			city_list_code.clear();
		}
		if (city_list.size() > 0) {
			city_list.clear();
		}
//		List<Cityinfo> city = news_img ArrayList<Cityinfo>();
//		city = cityHashMap.get(provicecode);
//		System.out.println("city--->" + city.toString());
		for (int i = 0; i < citylist.size(); i++) {
			City city=citylist.get(i);
			if(city.getPid().equals(provicecode)){
				city_list.add(city.getName());
				city_list_code.add(city.getCid());
			}
			
		}
		return city_list;

	}

	public ArrayList<String> getCouny(
			List<Area> arealist, String citycode) {
		System.out.println("citycode" + citycode);
//		List<Cityinfo> couny = null;
		if (couny_list_code.size() > 0) {
			couny_list_code.clear();

		}
		if (couny_list.size() > 0) {
			couny_list.clear();
		}
//		if (couny == null) {
//			couny = news_img ArrayList<Cityinfo>();
//		} else {
//			couny.clear();
//		}
//
//		couny = cityHashMap.get(citycode);
//		System.out.println("couny--->" + couny.toString());
		for (int i = 0; i < arealist.size(); i++) {
			Area area=arealist.get(i);
			if(area.getCid().equals(citycode)){
				couny_list.add(area.getName());
				couny_list_code.add(area.getAid());
			}
			
		}
		return couny_list;

	}
}
