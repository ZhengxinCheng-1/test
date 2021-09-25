package com.pro.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.pro.model.Payment;
import com.pro.utils.ConnectionUtil;


public class PaymentDao {

    public void insert(Payment payment) {

        Connection conn = ConnectionUtil.getConnection();
        String sql = "insert into payment(pay_num,customer_id,order_id,pay_tot,pay_method,card_num,create_date,status) values(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, payment.getPayNum());
            ps.setInt(2, payment.getCustomerId());
            ps.setInt(3, payment.getOrderId());
            ps.setBigDecimal(4, payment.getPayTot());
            ps.setString(5, payment.getPayMethod());
            ps.setString(6, payment.getCardNum());
            ps.setTimestamp(7, new Timestamp(payment.getCreateDate().getTime()));
            ps.setInt(8, payment.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

    }

    public void update(Payment payment) {
        Connection conn = ConnectionUtil.getConnection();
        List<String> cols = new ArrayList<>();
        if (payment.getPayNum() != null) {
            cols.add("pay_num=?");
        }
        if (payment.getCustomerId() != null) {
            cols.add("customer_id=?");
        }
        if (payment.getOrderId() != null) {
            cols.add("order_id=?");
        }
        if (payment.getPayTot() != null) {
            cols.add("pay_tot=?");
        }
        if (payment.getPayMethod() != null) {
            cols.add("pay_method=?");
        }
        if (payment.getCardNum() != null) {
            cols.add("card_num=?");
        }
        if (payment.getCreateDate() != null) {
            cols.add("create_date=?");
        }
        if (payment.getStatus() != null) {
            cols.add("status=?");
        }

        String sql = "update payment set " + String.join(",", cols) + " where id=" + payment.getId();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int idx = 1;
            if (payment.getPayNum() != null) {
                ps.setString(idx++, payment.getPayNum());
            }
            if (payment.getCustomerId() != null) {
                ps.setInt(idx++, payment.getCustomerId());
            }
            if (payment.getOrderId() != null) {
                ps.setInt(idx++, payment.getOrderId());
            }
            if (payment.getPayTot() != null) {
                ps.setBigDecimal(idx++, payment.getPayTot());
            }
            if (payment.getPayMethod() != null) {
                ps.setString(idx++, payment.getPayMethod());
            }
            if (payment.getCardNum() != null) {
                ps.setString(idx++, payment.getCardNum());
            }
            if (payment.getCreateDate() != null) {
                ps.setTimestamp(idx++, new Timestamp(payment.getCreateDate().getTime()));
            }
            if (payment.getStatus() != null) {
                ps.setInt(idx++, payment.getStatus());
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
        String sql = "delete from payment where id=?";

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
        String sql = "delete from payment where customer_id=?";

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

    public List<Payment> queryList(String sql) {
        List<Payment> list = new ArrayList<>();

        Connection conn = ConnectionUtil.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                Payment obj = new Payment();
                obj.setId(set.getInt("id"));
                obj.setPayNum(set.getString("pay_num"));
                obj.setCustomerId(set.getInt("customer_id"));
                obj.setOrderId(set.getInt("order_id"));
                obj.setPayTot(set.getBigDecimal("pay_tot"));
                obj.setPayMethod(set.getString("pay_method"));
                obj.setCardNum(set.getString("card_num"));
                obj.setCreateDate(set.getDate("create_date"));
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

    public Payment queryById(Integer id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "select * from payment where id= " + id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                Payment obj = new Payment();
                obj.setId(set.getInt("id"));
                obj.setPayNum(set.getString("pay_num"));
                obj.setCustomerId(set.getInt("customer_id"));
                obj.setOrderId(set.getInt("order_id"));
                obj.setPayTot(set.getBigDecimal("pay_tot"));
                obj.setPayMethod(set.getString("pay_method"));
                obj.setCardNum(set.getString("card_num"));
                obj.setCreateDate(set.getDate("create_date"));
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