package com.pro.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pro.dao.OrdersDao;
import com.pro.model.Orders;
import com.pro.model.Payment;
import com.pro.dao.PaymentDao;

public class PaymentService {

	private PaymentDao paymentDao;
	private OrdersDao ordersDao;
	private OrdersService ordersService;

	public PaymentService() {
		this.ordersDao = new OrdersDao();
		this.paymentDao = new PaymentDao();
		this.ordersService = new OrdersService();
	}
	
	public void savePayment(Payment payment) {
		payment.setCreateDate(new Date());
		payment.setPayNum(System.currentTimeMillis()+"");
		payment.setStatus(0);
		paymentDao.insert(payment);

		Orders orders = new Orders();
		orders.setId(payment.getOrderId());
		orders.setStatus(1);
		ordersDao.update(orders);

	}
	
	public void deletePayment(Integer id) {
		Integer orderId = paymentDao.queryById(id).getOrderId();
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setStatus(0);
		ordersDao.update(orders);

		paymentDao.delete(id);
	}
	
	public void updatePayment(Payment payment) {
		String payMethod = payment.getPayMethod();
		if(payMethod != null && payMethod.equals("cash")){
			payment.setCardNum("");
		}
		paymentDao.update(payment);
	}
	
	public List<Payment> getPaymentList(String payNum, Integer status, Integer customerId, String date){
		String sql = "select * from payment where 1=1";
		if(customerId != null){
			sql += " and customer_id = " + customerId;
		}
		if(payNum != null && !"".equals(payNum)){
			sql += " and pay_num = '" + payNum + "'";
		}
		if(status != null){
			sql += " and status = " + status;
		}
		if(date != null && !"".equals(date)){
			sql += " and date_format(create_date,'%Y-%m-%d') = '" + date + "'";
		}
		sql += " order by create_date desc";

		List<Payment> paymentList = paymentDao.queryList(sql);
		for (Payment payment : paymentList) {
			Orders orders = ordersService.getById(payment.getOrderId());
			payment.setOrders(orders);
		}
		return paymentList;
	}
}
