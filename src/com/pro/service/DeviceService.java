package com.pro.service;

import java.util.List;

import com.pro.model.Device;
import com.pro.dao.DeviceDao;

public class DeviceService {

	private DeviceDao deviceDao;

	public DeviceService() {
		this.deviceDao = new DeviceDao();
	}
	
	public void saveDevice(Device device) {
		deviceDao.insert(device);
	}
	
	public void deleteDevice(Integer id) {
		deviceDao.delete(id);
	}
	
	public void updateDevice(Device device) {
		deviceDao.update(device);
	}
	
	public List<Device> getDeviceList(String name,String type){
		String sql = "select * from device where 1=1";
		if(name != null && !"".equals(name)){
			sql += " and device_name='" + name + "'";
		}

		if(type != null && !"".equals(type)){
			sql += " and device_type ='" + type + "'";
		}
		return deviceDao.queryList(sql);
	}
}
