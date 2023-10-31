package com.xk.dbtool.helper;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DynamicDatasource extends AbstractRoutingDataSource
{
    /**
     * 线程LOCAL
     */
    private static final ThreadLocal<String> LOCAL = new ThreadLocal<String>();

    /**
     * 数据源集合 key
     */
    public Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

    public Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    /**
     * getDbKey
     * @return
     *     LOCAL.get();
     */
    public static String getDbKey()
    {
        return LOCAL.get();
    }

    /**
     * 判断数据源方法
     *
     * @param dbKey
     *            参数dbKey
     */
    public void setDbKey(String dbKey)
    {

        if (checkDbKey(dbKey)){
            LOCAL.set(dbKey);
        }
        else {
            try {
                throw new Exception("不存在id为\"" + dbKey + "\"的数据源");
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("错误信息：", e);
            }
        }

    }

    /**
     * clear()方法
     */
    public static void clear()
    {
        LOCAL.remove();
    }

    /**
     * 判断数据源方法
     *
     * @param dbKey
     *            数据源参数
     * @return 返回boolean类型
     */
    public boolean checkDbKey(String dbKey)
    {
        if (dataSourceMap.get(dbKey) != null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 抽象方法，必须重写，用来判断使用哪个数据源
     */
    @Override
    protected String determineCurrentLookupKey()
    {
        return getDbKey();
    }

    /**
     * 对数据源的初始化方法，由于这里已经将数据源集合放在本类中，如果不重写将会由于父类参数为null而抛出异常。
     */
    @Override
    public void afterPropertiesSet()
    {
        
    }

    /**
     * 确定使用哪一个数据源 这里不做null判断，因为是经过null判断后再进入的。
     */
    @Override
    protected DataSource determineTargetDataSource()
    {
        String dsKeyStr = determineCurrentLookupKey();
        DataSource dds = dataSourceMap.get(dsKeyStr);
        return dds;
    }

    /**
     * 添加数据源 为了防止多线程添加同一个数据源，这里采用同步,同时会判断是否已存在
     * 
     * @param dbkey
     *            数据源编号
     * @param driverClassName
     *            驱动信息
     * @param url
     *            数据源地址
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param maxWait
     *            等待时间
     * @param initialSize
     *            初始化大小
     * @param maxActive
     *              最大
     * @param minIdle
     *              最小
     * @return String 新建数据源对应的key，如果已经存在，则返回之前的key。
     */
    public synchronized String addDataSource(String dbkey,
            String driverClassName, String url, String username,
            String password, int initialSize, int maxActive, int minIdle, long maxWait)
    {
        DataSource ds = createDataSource(driverClassName, url, username,
                password, initialSize,maxActive,minIdle, maxWait);
        // 存储数据源集合
        dataSourceMap.put(dbkey, ds);

        return dbkey;
    }

    /**
     * 创建一个数据源
     * 
     * @param driverClassName
     *            驱动信息
     * @param url
     *            数据源地址
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param maxWait
     *            等待时间
     * @param initialSize
     *             初始化大小
     * @param maxActive
     *              最大
     * @param minIdle
     *              最小
     * @return 新建数据源对象
     */
    private DataSource createDataSource(String driverClassName, String url,
            String username, String password, int initialSize, int maxActive,
            int minIdle, long maxWait)
    {
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(driverClassName);
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setInitialSize(initialSize);
        dds.setMaxActive(maxActive);
        dds.setMinIdle(minIdle);
        dds.setMaxWait(maxWait);
        return dds;
    }

}
/**
 * CHANGE HISTORY
 * 
 * M1:2018-04-21 XK 初始创建
 */
