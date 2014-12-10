package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import com.picserver.bean.PictureBean;

/**
 * 匹配查询
 * 
 * @author hadoop
 *
 */
public class HbaseMatching {
	HbaseConf hbaseConf = new HbaseConf();
	Configuration configuration = hbaseConf.hbaseConf();

	public List<PictureBean> picNameMatching(String subStr, String uid) {
		try {
			HTablePool pool = new HTablePool(configuration, 1000);
			List<Filter> filters = new ArrayList<Filter>();
			SubstringComparator comp = new SubstringComparator(subStr);
			// 当列colunm的值为val时进行查询
			Filter filter = new SingleColumnValueFilter(Bytes.toBytes("attr"),
					Bytes.toBytes("name"), CompareOp.EQUAL, comp);
			filters.add(filter);

			Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("attr"),
					Bytes.toBytes("usr"), CompareOp.EQUAL, Bytes.toBytes(uid));
			filters.add(filter1);

			Scan s = new Scan();
			FilterList filterList = new FilterList(filters);
			s.setFilter(filterList);
			ResultScanner rs = pool.getTable("cloud_picture").getScanner(s);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
