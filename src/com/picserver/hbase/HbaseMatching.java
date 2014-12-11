package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
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
 * @author hadoop
 *
 */
public class HbaseMatching {
	HbaseConf hbaseConf = new HbaseConf();
	Configuration configuration = hbaseConf.hbaseConf();


	/**
	 * 根据名字的子串检索某用户的图片
	 * @param subStr
	 * @param uid
	 * @return
	 */

	public List<PictureBean> picNameMatching(String subStr, String uid) {
		try {
	        HTable table=new HTable(configuration, "cloud_picture"); 
			List<Filter> filters = new ArrayList<Filter>();
			
			SubstringComparator comp = new SubstringComparator(subStr);
			Filter filter = new SingleColumnValueFilter(Bytes.toBytes("attr"),
					Bytes.toBytes("name"), CompareOp.EQUAL, comp);
			filters.add(filter);

			Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("attr"),
					Bytes.toBytes("usr"), CompareOp.EQUAL, Bytes.toBytes(uid));
			filters.add(filter1);

			Scan s = new Scan();
			FilterList filterList = new FilterList(filters);
			s.setFilter(filterList);
			ResultScanner rs =table.getScanner(s);
			table.close();
			ListMapping lm = new ListMapping();
			List<PictureBean> list = lm.pictureListMapping(rs);
			rs.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
