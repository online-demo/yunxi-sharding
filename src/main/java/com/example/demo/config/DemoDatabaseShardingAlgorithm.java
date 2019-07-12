package com.example.demo.config;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-12 21:21
 * @Description: 数据库分片的计算逻辑
 */
public class DemoDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String databaseName : collection) {
            //数据库后缀名
            String suffix = String.valueOf(preciseShardingValue.getValue() % 2);
            //如果数据库后缀 = suffix  则选择这个库
            if (databaseName.endsWith(suffix)) {
                return databaseName;
            }
        }
        throw new IllegalArgumentException("参数异常");
    }
}
