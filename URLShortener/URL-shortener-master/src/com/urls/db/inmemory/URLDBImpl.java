package com.urls.db.inmemory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.urls.db.DB;
import com.urls.db.URLBean;
import com.urls.enums.URLStatus;

import ch.qos.logback.classic.Logger;

public class URLDBImpl extends DB {

	private final static Logger log = (Logger) LoggerFactory.getLogger(URLDBImpl.class);

	@Override
	public void createTable() throws Exception {

		String createTableQry = "create table IF NOT EXISTS urlTable (id INT NOT NULL AUTO_INCREMENT, urlKey varchar(100) not null unique, url varchar(200) not null, status INT, creationTime BIGINT, expireTime BIGINT, PRIMARY KEY (id))";
		Connection conn = null;
		Statement statement = null;
		try {

			conn = getDBConnection();
			statement = conn.createStatement();
			statement.executeUpdate(createTableQry);

			log.info("Table URL-Table successfully created");

		} catch (Exception e) {
			log.error("Error while creating table. ErrorMsg: {}", e.getMessage(), e);
		} finally {

			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		}

	}

	public URLBean insertUrl(URLBean url) throws Exception {

		String insertQry = "INSERT into urlTable(urlKey,url,status,creationTime,expireTime) values('" + url.getUrlKey()
				+ "', '" + url.getUrl() + "'," + url.getStatus().getValue() + "," + url.getCreationDate() + ","
				+ url.getExpireDate() + " )";

		Connection conn = null;
		Statement statement = null;
		try {
			createTable();
			conn = getDBConnection();
			statement = conn.createStatement();
			statement.executeUpdate(insertQry);

			url.setId(loadUrlIdByUrlKey(url.getUrlKey()));
			url.setUrlKey(url.getId()+"");
			updateUrl(url);

		} catch (Exception e) {
			log.error("Error while inserting Url. ErrorMsg :{}", e.getMessage(), e);
			throw e;
		} finally {
			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		}
		return url;

	}

	public int loadUrlIdByUrlKey(String urlKey) throws Exception {

		String qry = "select id from urlTable where urlKey = '" + urlKey + "'";

		Connection conn = null;
		Statement statement = null;
		int id;
		try {
			createTable();
			conn = getDBConnection();
			statement = conn.createStatement();
			ResultSet set = statement.executeQuery(qry);

			set.next();
			id = set.getInt("id");

			log.debug("ID loaded successfully. ID: {}, Url-Key: {}", id, urlKey);

		} catch (Exception e) {
			log.error("Error while inserting Url. ErrorMsg :{}", e.getMessage(), e);
			throw e;
		} finally {
			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		}
		return id;
	}

	public Integer updateUrl(URLBean url) throws Exception {

		String updateQry = "UPDATE urlTable SET urlKey ='"+url.getUrlKey()+"', status="+url.getStatus().getValue()+", expireTime ="+url.getExpireDate()+" where id ="+url.getId();

		Connection conn = null;
		Statement statement = null;
		int result;

		try {
			conn = getDBConnection();
			statement = conn.createStatement(); //prepareStatement(updateQry);
			
			//statement.setString(1, url.getUrlKey());
			//statement.setInt(1, url.getStatus().getValue());
			//statement.setInt(2, url.getId());
			
			result = statement.executeUpdate(updateQry);

		} catch (Exception e) {
			log.error("Error while inserting Url. ErrorMsg :{}", e.getMessage(), e);
			throw e;
		} finally {
			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		}
		return result;

	}

	public URLBean loadUrl(String urlKey) throws Exception {

		String loadQry = "select * from urlTable where urlKey ='" + urlKey + "'";

		Connection conn = null;
		Statement statement = null;
		URLBean result;

		try {
			conn = getDBConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(loadQry);
			rs.next();
			result = parseResultIntoUrl(rs);
			rs.close();

		} catch (Exception e) {
			log.error("Error while inserting Url. ErrorMsg :{}", e.getMessage(), e);
			throw e;
		} finally {
			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		}
		return result;

	}

	public List<URLBean> loadAllURl() throws Exception {

		String loadQry = "select * from urlTable where status != " + URLStatus.DELETED.getValue();

		Connection conn = null;
		Statement statement = null;
		List<URLBean> resultList = new ArrayList<URLBean>();

		try {
			conn = getDBConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(loadQry);

			while (rs.next())
				resultList.add(parseResultIntoUrl(rs));

			rs.close();

		} catch (Exception e) {
			log.error("Error while inserting Url. ErrorMsg :{}", e.getMessage(), e);
			throw e;
		} finally {
			if (statement != null)
				statement.close();
			if (conn != null)
				conn.close();
		}
		return resultList;

	}

	private URLBean parseResultIntoUrl(ResultSet rs) throws Exception {

		URLBean bean = new URLBean();

		bean.setId(rs.getInt("id"));
		bean.setStatus(URLStatus.valueOf(rs.getInt("status")));
		bean.setUrl(rs.getString("url"));
		bean.setUrlKey(rs.getString("urlKey"));
		bean.setExpireDate(rs.getLong("expireTime"));
		bean.setCreationDate(rs.getLong("creationTime"));

		return bean;

	}

	public int deleteUrl(URLBean bean) throws Exception {

		bean.setStatus(URLStatus.DELETED);
		return updateUrl(bean);

	}

	public static void main(String[] args) {

		URLBean bean = new URLBean();
		bean.setCreationDate(Calendar.getInstance().getTimeInMillis());
		bean.setExpireDate(Calendar.getInstance().getTimeInMillis());
		bean.setStatus(URLStatus.ACTIVE);
		bean.setUrl("www.fb.com");
		bean.setUrlKey("key1");

		URLDBImpl db = new URLDBImpl();
		try {
			// db.createTable();
			db.insertUrl(bean);
			db.deleteUrl(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
