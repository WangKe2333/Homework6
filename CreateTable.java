import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InterruptedIOException;

public class CreateTable {
	public static Configuration configuration; 
    static { 
        configuration = HBaseConfiguration.create();
        configuration.addResource("core-site.xml");
        configuration.addResource("hbase-site.xml");
        configuration.addResource("hdfs-site.xml");
        configuration.set("hbase.zookeeper.property.clientPort", "2181"); 
        configuration.set("hbase.zookeeper.quorum", "127.0.0.1"); 
        //configuration.set("hbase.master", "192.168.1.100:600000"); 3
    } 
	public static void main(String[] args) {
        CreateTable object = new CreateTable();
        object.createTable();
        object.insertData("mystudents");
    }

    public void createTable() {
//        Configuration config = HBaseConfiguration.create();
//        config.set("hbase.zookeeper.quorum", "127.0.0.1");
//        config.set("hbase.zookeeper.property.clientPort", "2181");

        Connection connection = null;
        Admin admin = null;

        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();

            String tableName = "mystudents";

            if (!admin.isTableAvailable(TableName.valueOf(tableName))) {
                HTableDescriptor hbaseTable = new HTableDescriptor(TableName.valueOf(tableName));
                hbaseTable.addFamily(new HColumnDescriptor("id"));
                hbaseTable.addFamily(new HColumnDescriptor("description"));
                hbaseTable.addFamily(new HColumnDescriptor("courses"));
                hbaseTable.addFamily(new HColumnDescriptor("home"));
                admin.createTable(hbaseTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (admin != null) {
                    admin.close();
                }

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
    }
    public static void insertData(String tableName) {  
        System.out.println("start insert data ......");
//        Connection conn= ConnectionFactory.createConnection(configuration);
//        Table table =conn.getTable(TableName.valueOf(tableName));
        HTablePool pool = new HTablePool(configuration, 10);  
        HTableInterface table = pool.getTable(tableName);   
        Put put = new Put("001".getBytes()); 
        put.addColumn(Bytes.toBytes("id"),Bytes.toBytes("idd"),Bytes.toBytes("001"));
        put.addColumn(Bytes.toBytes("description"),Bytes.toBytes("name"),Bytes.toBytes("li lei"));
        put.addColumn(Bytes.toBytes("description"),Bytes.toBytes("height"),Bytes.toBytes("176"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("chinese"),Bytes.toBytes("80"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("math"),Bytes.toBytes("90"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("physics"),Bytes.toBytes("95"));
        put.addColumn(Bytes.toBytes("home"),Bytes.toBytes("province"),Bytes.toBytes("zhejiang"));
        try {  
            table.put(put);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        put = new Put("002".getBytes()); 
        put.addColumn(Bytes.toBytes("id"),Bytes.toBytes("idd"),Bytes.toBytes("002"));
        put.addColumn(Bytes.toBytes("description"),Bytes.toBytes("name"),Bytes.toBytes("Han meimei"));
        put.addColumn(Bytes.toBytes("description"),Bytes.toBytes("height"),Bytes.toBytes("183"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("chinese"),Bytes.toBytes("88"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("math"),Bytes.toBytes("77"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("physics"),Bytes.toBytes("66"));
        put.addColumn(Bytes.toBytes("home"),Bytes.toBytes("province"),Bytes.toBytes("beijing"));
        try {  
            table.put(put);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        put = new Put("003".getBytes()); 
        put.addColumn(Bytes.toBytes("id"),Bytes.toBytes("idd"),Bytes.toBytes("003"));
        put.addColumn(Bytes.toBytes("description"),Bytes.toBytes("name"),Bytes.toBytes("Xiao ming"));
        put.addColumn(Bytes.toBytes("description"),Bytes.toBytes("height"),Bytes.toBytes("162"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("chinese"),Bytes.toBytes("90"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("math"),Bytes.toBytes("90"));
        put.addColumn(Bytes.toBytes("courses"),Bytes.toBytes("physics"),Bytes.toBytes("90"));
        put.addColumn(Bytes.toBytes("home"),Bytes.toBytes("province"),Bytes.toBytes("shanghai"));
        try {  
            table.put(put);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        System.out.println("end insert data ......");  
    }

}
