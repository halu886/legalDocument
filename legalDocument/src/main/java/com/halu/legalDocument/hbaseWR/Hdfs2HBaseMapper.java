package com.halu.legalDocument.hbaseWR;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Hdfs2HBaseMapper extends Mapper<LongWritable, Text, Text, Text> {
        public void map(LongWritable key, Text line, Context context) throws IOException,InterruptedException {
                String lineStr = line.toString();
                int index;
//                index = "asdfadf|asf".indexOf("|", 0);
                index = lineStr.indexOf("|");
//                line.
                String rowkey = lineStr.substring(0, index);
                String left = lineStr.substring(index+1);
                
                context.write(new Text(rowkey), new Text(left));
        }
}