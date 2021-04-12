package com.java.topic.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadLocalConnectionUtil {
    private ThreadLocalConnectionUtil() {
    }

    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public static Connection getConnection() {
        Connection connection = connectionThreadLocal.get();
        if (null == connection) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("url", "userName", "password");
                connectionThreadLocal.set(connection);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
