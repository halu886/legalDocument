package com.halu.legalDocument.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.halu.legalDocument.entity.LegalCell;

public class HBaseUtil {

	private static Connection connection;

	static TableName tableName = TableName.valueOf("legalDocument".getBytes());

	private static Admin admin;// 客户端

	private static void init() {
		System.setProperty("hadoop.home.dir", "F:/hadoop-2.6.4");
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "192.168.26.129,192.168.26.130,192.168.26.133");
		conf.set("hbase.zookeeper.property.clientPort", "2181");

		try {
			connection = ConnectionFactory.createConnection(conf);
			admin = connection.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (connection != null && !connection.isClosed()) {
			return connection;
		} else {
			init();
			return connection;
		}
	}

	/**
	 * 根据RowKey获取数据
	 * 
	 * @param tableName
	 *            表名称
	 * @param rowKey
	 *            RowKey名称
	 * @param colFamily
	 *            列族名称
	 * @param col
	 *            列名称
	 * @throws IOException
	 */
	public static HashMap<String, Object> getData(String tableName, String rowKey, String colFamily, String col)
			throws IOException {
		Table table = getConnection().getTable(TableName.valueOf(tableName));
		Get get = new Get(Bytes.toBytes(rowKey));
		if (colFamily != null) {
			get.addFamily(Bytes.toBytes(colFamily));
		}
		if (colFamily != null && col != null) {
			get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col));

		}
		Result result = table.get(get);
		showCell(result);
		HashMap<String, Object> hashMap = convertCell(result);

		table.close();
		close();
		return hashMap;
	}

	private static HashMap<String, Object> convertCell(Result result) throws UnsupportedEncodingException {
		Cell[] cells = result.rawCells();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		// cells.
		int i = 1;
		for (Cell cell : cells) {
			hashMap.put("id", new String(CellUtil.cloneRow(cell), "UTF-8"));
			hashMap.put("timeTamp", cell.getTimestamp());
			hashMap.put("columnFamily", new String(CellUtil.cloneFamily(cell), "UTF-8"));
			hashMap.put(new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell), "UTF-8"));
		}
		return hashMap;
	}

	private static Admin getAdmin() {
		if (admin != null) {
			return admin;
		} else {
			init();
			return admin;
		}
	}

	/**
	 * 关闭连接
	 */
	public static void close() {
		try {
			if (null != admin) {
				admin.close();
			}
			if (null != connection) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: scan
	 * @Description:扫描Hbase
	 * @param key
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static HashMap<String, Object> scan() throws IllegalArgumentException, IOException {
		ResultScanner resultScanner = getConnection().getTable(tableName).getScanner("lawcase".getBytes());
//		showCell(resultScanner);
		return convertCell(resultScanner);
	}

	/**
	 * 
	 * @Title: convertCell
	 * @Description:将数据转存为HashMap
	 * @param resultScanner
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static HashMap<String, Object> convertCell(ResultScanner resultScanner) throws UnsupportedEncodingException {
		Iterator<Result> iterator = resultScanner.iterator();
		ArrayList<LegalCell> list = new ArrayList<LegalCell>();
		for (Result result : resultScanner) {
			Cell[] cells = result.rawCells();
			LegalCell legalCell = new LegalCell();
			for (Cell cell : cells) {
				 legalCell.setId(new String(CellUtil.cloneRow(cell), "UTF-8"));
				 legalCell.setTimeTamp(cell.getTimestamp());
				 legalCell.setColumnFamily(new String(CellUtil.cloneFamily(cell), "UTF-8"));
//				Object object = new Object{};
				if (new String(CellUtil.cloneQualifier(cell)).equals("title")) {
					legalCell.setTitle(new String(CellUtil.cloneValue(cell), "UTF-8"));
				}else if (new String(CellUtil.cloneQualifier(cell)).equals("body")) {
					legalCell.setBody(new String(CellUtil.cloneValue(cell), "UTF-8"));
				}
			}	
			list.add(legalCell);
		}
		HashMap<String, Object> cellMap = new HashMap<String,Object>();
		cellMap.put("cells", list);
		return cellMap;
	}

	/**
	 * 
	 * @Title: showCell
	 * @Description:显示cell
	 * @param resultScanner
	 */
	private static void showCell(ResultScanner resultScanner) {
		Iterator<Result> iterator = resultScanner.iterator();
		for (Result result : resultScanner) {
			showCell(result);
		}
	}

	public static HashMap<String, Object> get(String rowId) throws IOException {
		return getData("legalDocument", rowId);
	}

	public static void showCell(Result result) {
		Cell[] cells = result.rawCells();
		for (Cell cell : cells) {
			System.out.println("RowName: " + new String(CellUtil.cloneRow(cell)) + " ");
			System.out.println("Timetamp: " + cell.getTimestamp() + " ");
			System.out.println("column Family: " + new String(CellUtil.cloneFamily(cell)) + " ");
			System.out.println("row Name: " + new String(CellUtil.cloneQualifier(cell)) + " ");
			System.out.println("value: " + new String(CellUtil.cloneValue(cell)) + " ");
		}
	}

	public static HashMap<String, Object> getData(String tableName, String rowKey) throws IOException {
		return getData(tableName, rowKey, null, null);
	}

	public static void main(String[] args) {
		try {
			// System.out.println();
			scan();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
