package com.pro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.pro.model.Device;
import com.pro.utils.ConnectionUtil;

import java.math.BigDecimal;

public class DeviceDao {

	public void insert(Device device) {

		Connection conn = ConnectionUtil.getConnection();
		String sql = "insert into device(device_name,device_type,unit_price,stock) values(?,?,?,?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, device.getDeviceName());
			ps.setString(2, device.getDeviceType());
			ps.setBigDecimal(3, device.getUnitPrice());
			ps.setInt(4, device.getStock());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}

	}

	public void update(Device device) {
		Connection conn = ConnectionUtil.getConnection();
		List<String> cols = new ArrayList<>();
		if (device.getDeviceName() != null) {
			cols.add("device_name=?");
		}
		if (device.getDeviceType() != null) {
			cols.add("device_type=?");
		}
		if (device.getUnitPrice() != null) {
			cols.add("unit_price=?");
		}
		if (device.getStock() != null) {
			cols.add("stock=?");
		}

		String sql = "update device set " + String.join(",", cols) + " where id=" + device.getId();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int idx = 1;
			if (device.getDeviceName() != null) {
				ps.setString(idx++, device.getDeviceName());
			}
			if (device.getDeviceType() != null) {
				ps.setString(idx++, device.getDeviceType());
			}
			if (device.getUnitPrice() != null) {
				ps.setBigDecimal(idx++, device.getUnitPrice());
			}
			if (device.getStock() != null) {
				ps.setInt(idx++, device.getStock());
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}

	}

	public void delete(Integer id) {

		Connection conn = ConnectionUtil.getConnection();
		String sql = "delete from device where id=?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}

	}

	public List<Device> queryList(String sql) {
		List<Device> list = new ArrayList<>();

		Connection conn = ConnectionUtil.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				Device obj = new Device();
				obj.setId(set.getInt("id"));
				obj.setDeviceName(set.getString("device_name"));
				obj.setDeviceType(set.getString("device_type"));
				obj.setUnitPrice(set.getBigDecimal("unit_price"));
				obj.setStock(set.getInt("stock"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return list;
	}

	public Device queryById(Integer id) {
		Connection conn = ConnectionUtil.getConnection();
		try {
			String sql = "select * from device where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				Device obj = new Device();
				obj.setId(set.getInt("id"));
				obj.setDeviceName(set.getString("device_name"));
				obj.setDeviceType(set.getString("device_type"));
				obj.setUnitPrice(set.getBigDecimal("unit_price"));
				obj.setStock(set.getInt("stock"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return null;
	}
}