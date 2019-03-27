package com.example.demo.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class Dao {
    static Configuration cfg = null;
    static {
        cfg = HBaseConfiguration.create();
        cfg.set("hbase.zookeeper.quorum", "master,slave1,slave2");
    }


    public static boolean isExists(String tableName) throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
        //老API
//        HBaseAdmin admin = new HBaseAdmin(conf);
//
//        return admin.tableExists(Bytes.toBytes(tableName));

        // 新API
        Connection connection = ConnectionFactory.createConnection(cfg);
        Admin admin = connection.getAdmin();

        return admin.tableExists(TableName.valueOf(tableName));
    }


    public static void create(String tableName, String[] columnFamily) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(cfg);
        if (admin.tableExists(tableName)) {
            System.out.println(tableName + " exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for(String str:columnFamily){
                tableDesc.addFamily(new HColumnDescriptor(str));
            }
            admin.createTable(tableDesc);
            System.out.println(tableName + " create successfully!");
        }
    }

    public static void put(String tablename, String row, String columnFamily, String column, String data)
            throws Exception {
        HTable table = new HTable(cfg, tablename);
        Put put = new Put(Bytes.toBytes(row));
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
        table.put(put);
    }

    public static Result getResult(String tableName, String rowKey)
            throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        HTable table = new HTable(cfg, Bytes.toBytes(tableName));// 获取表
        Result result = table.get(get);
        return result;
    }

    public static ResultScanner scan(String tableName) throws IOException  {
        String str = "";
        HTable table = new HTable(cfg, tableName);
        Scan s = new Scan();
        ResultScanner rs = table.getScanner(s);
        return rs;
    }



}
