package com.halu.legalDocument.hbaseWR;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.commons.logging.Log;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class Hdfs2HBaseReducer extends Reducer<Text, Text, ImmutableBytesWritable, Put> {
	private Log log = LogFactory.getLog(Hdfs2HBaseReducer.class);

	static {
//		private String family = "lawcase";
//		String title = "title";
		String body = "body";
	}

	private String family = "lawcase";
	
	private String titleQualifier = "title";
	
	private String bodyQualifier = "body";
	
	public void reduce(Text rowkey, Iterable<Text> value, Context context) throws IOException, InterruptedException {
		String k = rowkey.toString();
		for (Text val : value) {
			Put put = new Put(k.getBytes());
			String[] strs = val.toString().split("\\|");
			
			if (strs.length != 2) {
				log.error("rowkey:" + rowkey.toString() + strs.length + "切割失败");
//				for (int i = 0; i < strs.length; i++) {
//					System.out.println(strs[i]);
//				}
				return;
			}
			String title = strs[0];
			String body = strs[1];
//			put.addColumn((family.getBytes(), titleQualifier.getBytes(), title.getBytes());
			put.addColumn(family.getBytes(), titleQualifier.getBytes(), title.getBytes());
			put.addColumn(family.getBytes(), bodyQualifier.getBytes(), body.getBytes());
			context.write(new ImmutableBytesWritable(k.getBytes()), put);
		}
	}
}
