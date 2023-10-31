package com.xk.dbtool.helper;

import com.xk.dbtool.dao.DbMapper;
import com.xk.dbtool.entity.DbInfoEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
@Slf4j
@Data
@ToString
public class DbHelper {

    @Autowired
    DbMapper dbMapper;

    DynamicDatasource dds = new DynamicDatasource();

    public void init(){
        List<DbInfoEntity> list = dbMapper.selectList(null);
        dds.getDataSourceMap().clear();
        for (DbInfoEntity m : list) {
            try {
                dds.addDataSource(m.getId() , m.getDriverClassName(), m.getUrl() , m.getUserName() ,
                        m.getPwd() ,m.getInitialSize(),m.getMaxActive(),m.getMinIdle(),m.getMaxWait() );
            }
            catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                log.error("错误信息：", e);
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("错误信息：", e);
            }
        }

    }

    public DataSource getDataSource(String dbId){

        return dds.getDataSourceMap().get(dbId);

    }

}
