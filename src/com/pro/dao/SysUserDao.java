package com.pro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.pro.model.SysUser;
import com.pro.utils.ConnectionUtil;

public class SysUserDao {

	public void insert(SysUser sysUser) {

		Connection conn = ConnectionUtil.getConnection();
		String sql = "insert into sys_user(username,password,phone,role_type,status) values(?,?,?,?,?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, sysUser.getUsername());
			ps.setString(2, sysUser.getPassword());
			ps.setString(3, sysUser.getPhone());
			ps.setInt(4, sysUser.getRoleType());
			ps.setInt(5, sysUser.getStatus());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}

	}

	public void update(SysUser sysUser) {
		Connection conn = ConnectionUtil.getConnection();
		List<String> cols = new ArrayList<>();
		if (sysUser.getUsername() != null) {
			cols.add("username=?");
		}
		if (sysUser.getPassword() != null) {
			cols.add("password=?");
		}
		if (sysUser.getPhone() != null) {
			cols.add("phone=?");
		}
		if (sysUser.getRoleType() != null) {
			cols.add("role_type=?");
		}
		if (sysUser.getStatus() != null) {
			cols.add("status=?");
		}

		String sql = "update sys_user set " + String.join(",", cols) + " where id=" + sysUser.getId();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int idx = 1;
			if (sysUser.getUsername() != null) {
				ps.setString(idx++, sysUser.getUsername());
			}
			if (sysUser.getPassword() != null) {
				ps.setString(idx++, sysUser.getPassword());
			}
			if (sysUser.getPhone() != null) {
				ps.setString(idx++, sysUser.getPhone());
			}
			if (sysUser.getRoleType() != null) {
				ps.setInt(idx++, sysUser.getRoleType());
			}
			if (sysUser.getStatus() != null) {
				ps.setInt(idx++, sysUser.getStatus());
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
		String sql = "delete from sys_user where id=?";

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

	public SysUser queryById(Integer id){
		Connection conn = ConnectionUtil.getConnection();
		String sql = "select * from sys_user where id=" + id;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			SysUser obj = new SysUser();
			while (set.next()) {
				obj.setId(set.getInt("id"));
				obj.setUsername(set.getString("username"));
				obj.setPassword(set.getString("password"));
				obj.setPhone(set.getString("phone"));
				obj.setRoleType(set.getInt("role_type"));
				obj.setStatus(set.getInt("status"));
			}
			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return null;
	}

	public int checkPhone(String phone,Integer userId){
		Connection conn = ConnectionUtil.getConnection();
		String sql = "select count(1) as count from sys_user where phone = ?";
		if(userId != null){
			sql += " and id !=?";
		}

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,phone);
			if(userId != null){
				ps.setInt(2,userId);
			}
			ResultSet set = ps.executeQuery();
			SysUser obj = new SysUser();
			if (set.next()) {
				return set.getInt("count");
			}

			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return 0;
	}

	public SysUser selectByUsername(String username, Integer userId){
		Connection conn = ConnectionUtil.getConnection();
		String sql = "select * from sys_user where username=?";

		if(userId != null){
			sql += " and id !=?";
		}

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			if(userId != null){
				ps.setInt(2,userId);
			}

			ResultSet set = ps.executeQuery();
			SysUser obj = null;
			if (set.next()) {
				obj = new SysUser();
				obj.setId(set.getInt("id"));
				obj.setUsername(set.getString("username"));
				obj.setPassword(set.getString("password"));
				obj.setPhone(set.getString("phone"));
				obj.setRoleType(set.getInt("role_type"));
				obj.setStatus(set.getInt("status"));
			}
			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return null;
	}

	public List<SysUser> queryList(SysUser sysUser) {
		List<SysUser> list = new ArrayList<>();

		Connection conn = ConnectionUtil.getConnection();
		String sql = "select * from sys_user where 1=1";

		List<String> cols = new ArrayList<>();

		if (sysUser.getUsername() != null) {
			cols.add("username=?");
		}
		if (sysUser.getPassword() != null) {
			cols.add("password=?");
		}
		if (sysUser.getPhone() != null) {
			cols.add("phone=?");
		}
		if (sysUser.getRoleType() != null) {
			cols.add("role_type=?");
		}
		if (sysUser.getStatus() != null) {
			cols.add("status=?");
		}

//		String sql = "update sys_user set " + String.join(",", cols) + " where id=" + sysUser.getId();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int idx = 1;
			if (sysUser.getUsername() != null) {
				ps.setString(idx++, sysUser.getUsername());
			}
			if (sysUser.getPassword() != null) {
				ps.setString(idx++, sysUser.getPassword());
			}
			if (sysUser.getPhone() != null) {
				ps.setString(idx++, sysUser.getPhone());
			}
			if (sysUser.getRoleType() != null) {
				ps.setInt(idx++, sysUser.getRoleType());
			}
			if (sysUser.getStatus() != null) {
				ps.setInt(idx++, sysUser.getStatus());
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}


		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				SysUser obj = new SysUser();
				obj.setId(set.getInt("id"));
				obj.setUsername(set.getString("username"));
				obj.setPassword(set.getString("password"));
				obj.setPhone(set.getString("phone"));
				obj.setRoleType(set.getInt("role_type"));
				obj.setStatus(set.getInt("status"));

				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.closeConnection(conn);
		}
		return list;
	}

	public List<SysUser> queryList(String sql) {
		List<SysUser> list = new ArrayList<>();

		Connection conn = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				SysUser obj = new SysUser();
				obj.setId(set.getInt("id"));
				obj.setUsername(set.getString("username"));
				obj.setPassword(set.getString("password"));
				obj.setPhone(set.getString("phone"));
				obj.setRoleType(set.getInt("role_type"));
				obj.setStatus(set.getInt("status"));

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