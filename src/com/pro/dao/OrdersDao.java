package com.pro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.pro.model.Orders;
import com.pro.utils.ConnectionUtil;

import java.math.BigDecimal;


public class OrdersDao {

    public void insert(Orders orders) {

        Connection conn = ConnectionUtil.getConnection();
        String sql = "insert into orders(order_num,device_id,price,count,amount,customer_id,create_date,status) values(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orders.getOrderNum());
            ps.setInt(2, orders.getDeviceId());
            ps.setBigDecimal(3, orders.getPrice());
            ps.setInt(4, orders.getCount());
            ps.setBigDecimal(5, orders.getAmount());
            ps.setInt(6, orders.getCustomerId());
            ps.setTimestamp(7, new Timestamp(orders.getCreateDate().getTime()));
            ps.setInt(8, orders.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

    }

    public void update(Orders orders) {
        Connection conn = ConnectionUtil.getConnection();
        List<String> cols = new ArrayList<>();
        if (orders.getOrderNum() != null) {
            cols.add("order_num=?");
        }
        if (orders.getDeviceId() != null) {
            cols.add("device_id=?");
        }
        if (orders.getPrice() != null) {
            cols.add("price=?");
        }
        if (orders.getCount() != null) {
            cols.add("count=?");
        }
        if (orders.getAmount() != null) {
            cols.add("amount=?");
        }
        if (orders.getCustomerId() != null) {
            cols.add("customer_id=?");
        }
        if (orders.getCreateDate() != null) {
            cols.add("create_date=?");
        }
        if (orders.getStatus() != null) {
            cols.add("status=?");
        }

        String sql = "update orders set " + String.join(",", cols) + " where id=" + orders.getId();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int idx = 1;
            if (orders.getOrderNum() != null) {
                ps.setString(idx++, orders.getOrderNum());
            }
            if (orders.getDeviceId() != null) {
                ps.setInt(idx++, orders.getDeviceId());
            }
            if (orders.getPrice() != null) {
                ps.setBigDecimal(idx++, orders.getPrice());
            }
            if (orders.getCount() != null) {
                ps.setInt(idx++, orders.getCount());
            }
            if (orders.getAmount() != null) {
                ps.setBigDecimal(idx++, orders.getAmount());
            }
            if (orders.getCustomerId() != null) {
                ps.setInt(idx++, orders.getCustomerId());
            }
            if (orders.getCreateDate() != null) {
                ps.setTimestamp(idx++, new Timestamp(orders.getCreateDate().getTime()));
            }
            if (orders.getStatus() != null) {
                ps.setInt(idx++, orders.getStatus());
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
        String sql = "delete from orders where id=?";

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

    public void deleteByCustomerId(Integer customerId) {

        Connection conn = ConnectionUtil.getConnection();
        String sql = "delete from orders where customer_id=?";

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

    public List<Orders> queryList(String sql) {
        List<Orders> list = new ArrayList<>();

        Connection conn = ConnectionUtil.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                Orders obj = new Orders();
                obj.setId(set.getInt("id"));
                obj.setOrderNum(set.getString("order_num"));
                obj.setDeviceId(set.getInt("device_id"));
                obj.setPrice(set.getBigDecimal("price"));
                obj.setCount(set.getInt("count"));
                obj.setAmount(set.getBigDecimal("amount"));
                obj.setCustomerId(set.getInt("customer_id"));
                obj.setCreateDate(set.getTimestamp("create_date"));
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
    public Orders queryById(Integer id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "select * from orders where id = " + id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                Orders obj = new Orders();
                obj.setId(set.getInt("id"));
                obj.setOrderNum(set.getString("order_num"));
                obj.setDeviceId(set.getInt("device_id"));
                obj.setPrice(set.getBigDecimal("price"));
                obj.setCount(set.getInt("count"));
                obj.setAmount(set.getBigDecimal("amount"));
                obj.setCustomerId(set.getInt("customer_id"));
                obj.setCreateDate(set.getTimestamp("create_date"));
                obj.setStatus(set.getInt("status"));

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