package com.xk.dbtool.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xk.dbtool.entity.DbInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DbMapper extends BaseMapper<DbInfoEntity> {
}
