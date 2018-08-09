package com.example.demo.config;


import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class DataSourceConfig {

    /**
     * sharding-jdbc数据源
     * @return
     * @throws SQLException
     */
    @Bean(name = "shardingDataSource")
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration;
        shardingRuleConfiguration = new ShardingRuleConfiguration();
        //表规则配置
        shardingRuleConfiguration.getTableRuleConfigs().add(getUserTableRuleConfiguration());
        //表的组 user_info
        shardingRuleConfiguration.getBindingTableGroups().add("user_info");
        //DataBase的分片策略  配合DemoDatabaseShardingAlgorithm数据库分片逻辑
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", DemoDatabaseShardingAlgorithm.class.getName()));
        //Table的分片策略
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", DemoTableShardingAlgorithm.class.getName()));
        //根据配置实例化一个ShardingDataSource  bean
        return new ShardingDataSource(shardingRuleConfiguration.build(createDataSourceMap()));
    }

    /**
     * 用户表规则配置
     * @return
     */
    @Bean
    TableRuleConfiguration getUserTableRuleConfiguration() {
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        //逻辑表是user_info
        orderTableRuleConfig.setLogicTable("user_info");
        //实际的物理节点是user_${0..1}.user_info_${0..1} 即database.table
        orderTableRuleConfig.setActualDataNodes("user_${0..1}.user_info_${0..1}");
        orderTableRuleConfig.setKeyGeneratorColumnName("user_id");
        return orderTableRuleConfig;
    }

    /**
     * 封装数据源
     * @return
     */
    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2);
        result.put("user_0", createDataSource("user_0"));
        result.put("user_1", createDataSource("user_1"));
        return result;
    }

    /**
     * MySQL数据源
     * @param dataSourceName
     * @return
     */
    private DataSource createDataSource(final String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
        result.setUsername("root");
        result.setPassword("123456");
        return result;
    }
}
