package com.org.moglix.service.impl;

import java.util.List;

import com.org.moglix.dao.OrderDao;
import com.org.moglix.dao.impl.OrderDaoImpl;
import com.org.moglix.domain.Orders;

public class OrderServiceImpl implements com.org.moglix.service.OrderService {
OrderDao orderDao =new OrderDaoImpl();
	@Override
	public Orders saveOrUpdate(Orders order) {
		return orderDao.saveOrUpdate(order);
	}

	@Override
	public Orders getById(Long orderId) {
		return orderDao.getById(orderId);
	}

	@Override
	public List<Orders> getList() {
		return orderDao.getList();
	}

	@Override
	public void deleteById(Long orderId) {
		orderDao.deleteById(orderId);
	}

}