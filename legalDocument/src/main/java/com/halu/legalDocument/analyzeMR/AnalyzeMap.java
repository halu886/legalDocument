package com.halu.legalDocument.analyzeMR;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.halu.legalDocument.util.StringUtil;


public class AnalyzeMap extends Mapper<ImmutableBytesWritable, Put, Text, Text>{
	
	private final String id = "id";//主键
	
	private final String body = "body";
	
	private final String title = "title";
	
	private final String family = "family";

	@Override
	protected void map(ImmutableBytesWritable key, Put value,
			Mapper<ImmutableBytesWritable, Put, Text, Text>.Context context) throws IOException, InterruptedException {
		List<Cell> valueTitle = value.get(family.getBytes(), title.getBytes());
		for (Iterator iterator = valueTitle.iterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();
			StringUtil.extractKey(cell.getValueArray().toString(), "原告");
		}
		super.map(key, value, context);
	}
}
