package com.geyao.guards.dao;

import com.geyao.guards.bean.FunctionConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FunctionConfigRepository extends CrudRepository<FunctionConfig, String> {

    @Query(value = "select * from function_config where function_Switch = TRUE ",nativeQuery = true)
    List<FunctionConfig> queryByList();
}
