package com.pro.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pro.dao.DeviceDao;
import com.pro.model.Device;
import com.pro.model.Orders;
import com.pro.dao.OrdersDao;
import com.pro.utils.Result;

public class OrdersService {

	private OrdersDao ordersDao;
	private DeviceDao deviceDao;

	public OrdersService() {
		this.ordersDao = new OrdersDao();
		this.deviceDao = new DeviceDao();
	}
	
	public void saveOrders(Orders orders) {
		Device device = deviceDao.queryById(orders.getDeviceId());
		device.setStock(device.getStock() - orders.getCount());
		deviceDao.update(device);

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		orders.setCreateDate(date);
		orders.setStatus(0);
		orders.setOrderNum(sf.format(date));
		ordersDao.insert(orders);
	}
	
	public void deleteOrders(Integer id) {
        Orders o = ordersDao.queryById(id);
        Device device = deviceDao.queryById(o.getDeviceId());
        device.setStock(device.getStock() + o.getCount());
        deviceDao.update(device);
	    ordersDao.delete(id);
	}
	
	public Result updateOrders(Orders orders) {
        Orders o = ordersDao.queryById(orders.getId());
        Device device = deviceDao.queryById(o.getDeviceId());
        if(orders.getCount() > device.getStock()){
            return Result.failed("The quantity purchased is greater than the quantity in stock",500);
        }

        device.setStock(device.getStock() - orders.getCount());
        deviceDao.update(device);
        ordersDao.update(orders);
        return Result.success();
	}
	
	public List<Orders> getOrdersList(String orderNum, Integer status, Integer customerId, String date){

		String sql = "select * from orders where 1=1";
		if(customerId != null){
			sql += " and customer_id = " + customerId;
		}
		if(orderNum != null && !"".equals(orderNum)){
			sql += " and order_num = '" + orderNum + "'";
		}
		if(status != null){
			sql += " and status = " + status;
		}
        if(date != null && !"".equals(date)){
            sql += " and date_format(create_date,'%Y-%m-%d') = '" + date + "'";
        }
        sql += " order by create_date desc";

        List<Orders> ordersList = ordersDao.queryList(sql);
        for (Orders orders : ordersList) {
            Device device = deviceDao.queryById(orders.getDeviceId());
            orders.setDevice(device);
        }
        return ordersList;
	}

	public Orders getById(Integer id){
        Orders orders = ordersDao.queryById(id);
        Device device = deviceDao.queryById(orders.getDeviceId());
        orders.setDevice(device);
        return orders;
    }
}
