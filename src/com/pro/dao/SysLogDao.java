package com.pro.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.pro.model.SysLog;
import com.pro.utils.ConnectionUtil;

public class SysLogDao {

	public void insert(SysLog sysLog) {

		Connection conn = ConnectionUtil.getConnection();
		String sql = "insert into sys_log(user_id,action,create_date) values(?,?,?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sysLog.getUserId());
			ps.setString(2, sysLog.getAction());
			ps.setTimestamp(3,new Timestamp(sysLog.getCreateDate().getTime()));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}

	}

	public void deleteByUserId(Integer customerId) {

		Connection conn = ConnectionUtil.getConnection();
		String sql = "delete from sys_log where user_id=?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}

	}

	public List<SysLog> queryList() {
		List<SysLog> list = new ArrayList<>();

		Connection conn = ConnectionUtil.getConnection();
		String sql = "select * from sysLog";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				SysLog obj = new SysLog();
				obj.setId(set.getInt("id"));
				obj.setUserId(set.getInt("user_id"));
				obj.setAction(set.getString("action"));
				obj.setCreateDate(set.getTimestamp("create_date"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return list;
	}

	public List<SysLog> queryList(String sql) {
		List<SysLog> list = new ArrayList<>();
		Connection conn = ConnectionUtil.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (set.next()) {
				SysLog obj = new SysLog();
				obj.setId(set.getInt("id"));
				obj.setUserId(set.getInt("user_id"));
				obj.setAction(set.getString("action"));
				obj.setCreateDate(new Date(set.getTimestamp("create_date").getTime()));
				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return list;
	}
}