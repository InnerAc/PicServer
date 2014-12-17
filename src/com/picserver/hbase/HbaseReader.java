package com.picserver.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import com.picserver.bean.HdBean;
import com.picserver.bean.LogBean;
import com.picserver.bean.MapfileBean;
import com.picserver.bean.PanoBean;
import com.picserver.bean.Pic3DBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;

/**
 * 读取hbase内容封装到Bean中
 * @author hadoop
 *
 */
public class HbaseReader {

	HbaseOperation ho = new HbaseOperation();
	BeanMapping bm = new BeanMapping();
	ListMapping lm = new ListMapping();

	/**
	 * 根据rowkey 图片名 读取数据保存在PictureBean
	 * @param rowkey图片名
	 * @return 存在则返回Bean，不存在返回null
	 */
	public PictureBean getPictureBean(String rowkey) {
		if (rowkey == null)
			return null;
		Result rs = ho.QueryByRowKey("cloud_picture", rowkey);
		return bm.pictureBeanMapping(rs, rowkey);
	}

	/**
	 * 根据图片某一个列值检索表cloud_picture
	 * @param family 列族
	 * @param column  列
	 * @param value   值
	 * @return 如果检索到，则返回PictureBean的list，没有则返回null
	 * @throws IOException
	 */
	public List<PictureBean> getPictureBean(String family, String column,
			String value) throws IOException {
		ResultScanner rs = ho.QueryPicByColumn( family, column,value);
		return lm.pictureListMapping(rs);
	}

	/**
	 * 检索某用户某时间段上传的图片
	 * @param uid   用户
	 * @param sTime  起始时间
	 * @param eTime   结束时间
	 * @return
	 * @throws IOException
	 */
	public List<PictureBean> getLimitPicture(String uid, String sTime,
			String eTime, int num) throws IOException {
		ResultScanner rs = ho.QueryLimitPic(uid, sTime, eTime,num);
		return lm.pictureListMapping(rs);
	}

	/**
	 * 根据space和usr检索表
	 * @param usr
	 * @param space
	 * @return
	 * @throws IOException
	 */
	public List<PictureBean> getPictureBean(String usr, String space)
			throws IOException {
		ResultScanner rs = ho.QueryPic(usr, space);
		return lm.pictureListMapping(rs);
	
	}

	/**
	 * 根据rowkey 空间名 读取数据保存在SpaceBean
	 * @param rowkey   空间名
	 * @return 存在则返回SpaceBean，不存在返回null
	 */
	public SpaceBean getSpaceBean(String rowkey) {
		Result rs = ho.QueryByRowKey("cloud_space", rowkey);
		return bm.spaceBeanMapping(rs, rowkey);
	}

	/**
	 * 根据空间列值检索表cloud_space
	 * @param family  列族
	 * @param column  列
	 * @param value    值
	 * @return 如果检索到，则返回SpaceBean的list，没有则返回null
	 * @throws IOException
	 */
	public List<SpaceBean> getSpaceBean(String family, String column,
			String value) throws IOException {
		ResultScanner rs = ho.QueryByColumn("cloud_space", family, column,value);
		return lm.spaceListMapping(rs);
	}

	/**
	 * 根据rowkey 用户名 读取数据保存在UserBean
	 * @param rowkey   用户名uid
	 * @return 如果存在则返回UserBean，不存在返回null
	 */
	public UserBean getUserBean(String rowkey) {
		Result rs = ho.QueryByRowKey("cloud_user", rowkey);
		return bm.userBeanMapping(rs, rowkey);
	}

	/**
	 * 根据用户检索日志
	 * @param user   用户id
	 * @return 存在返回LogBean，不存在返回null
	 * @throws IOException
	 */
	public List<LogBean> getLogBean(String user) throws IOException {
		ResultScanner rs = ho.QueryByColumn("cloud_log", "attr", "user", user);
		return lm.logListMapping(rs);
	}

	/**
	 * 根据用户和时间范围检索日志
	 * @param user   用户
	 * @param min  起始时间
	 * @param max  结束时间
	 * @return 如果有返回List，没有返回null
	 * @throws IOException
	 */
	public List<LogBean> getLogBean(String user, String min, String max)
			throws IOException {
		ResultScanner rs = ho.QueryLog(user, min, max);
		return lm.logListMapping(rs);
	}

	/**
	 * 检索某用户的所有高清图片
	 * @param uid 用户id
	 * @return 有返回list，没有返回null
	 * @throws IOException
	 */
	public List<HdBean> getHdList(String uid) throws IOException {
		ResultScanner rs = ho.QueryByColumn("cloud_hd", "attr", "uid", uid);
		return lm.hdListMapping(rs);
	}
	
	/**
	 * 检索某用户的所有3D图片
	 * @param uid
	 * @return
	 * @throws IOException
	 */
	public List<Pic3DBean> get3DList(String uid) throws IOException {
		ResultScanner rs = ho.QueryByColumn("cloud_3d", "attr", "uid", uid);
		return lm.pic3DListMapping(rs);
	}
	
	/**
	 * 检索某用户的pano
	 * @param uid
	 * @return
	 * @throws IOException
	 */
	public List<PanoBean> getPanoList(String uid) throws IOException {
		ResultScanner rs = ho.QueryByColumn("cloud_pano", "attr", "uid", uid);
		return lm.panoListMapping(rs);
	}
	
	public PanoBean getPanoBean(String key) throws IOException {
		if (key == null)
			return null;
		Result rs = ho.QueryByRowKey("cloud_pano", key);
		return bm.panoBeanMapping(rs, key);
	}


	/**
	 * 根据主键获取mapfile
	 * @param rowkey 主键
	 * @return 有返回list，没有返回null
	 */
	public MapfileBean getMapfileBean(String rowkey) {
		Result rs = ho.QueryByRowKey("cloud_mapfile", rowkey);
		return bm.mapfileMapping(rs, rowkey);
	}
	
	public Pic3DBean get3D(String uid, String name){
		Result rs = ho.QueryByRowKey("cloud_3d", name+uid);
		return bm.Pic3DMapping(rs, name+uid);
	}

}