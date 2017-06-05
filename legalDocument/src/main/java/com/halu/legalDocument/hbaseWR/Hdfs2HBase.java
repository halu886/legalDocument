package com.halu.legalDocument.hbaseWR;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Hdfs2HBase {
    public static void main(String[] args) throws Exception {
    	System.setProperty("hadoop.home.dir", "F:/hadoop-2.6.4");
//    	System.setProperty("zookeeper.home.dir", "F:/zookeeper-3.4.6");
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum","192.168.26.129,192.168.26.130,192.168.26.133");
        conf.set("hbase.zookeeper.property.clientPort","2181"); 
//        conf.set("zookeeper.session.timeout.ms", "400000");
        
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if(otherArgs.length != 2) {
            System.err.println("Usage: wordcount <infile> <table>");
            System.exit(2);
        }

        Job job = new Job(conf, "hdfs2hbase");
        job.setJarByClass(Hdfs2HBase.class);
        job.setMapperClass(Hdfs2HBaseMapper.class);
        job.setReducerClass(Hdfs2HBaseReducer.class);

        job.setMapOutputKeyClass(Text.class);    // +
        job.setMapOutputValueClass(Text.class);  // +

        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Put.class);

        job.setOutputFormatClass(TableOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, otherArgs[1]);

        System.exit(job.waitForCompletion(true)?0:1);
    }
}
