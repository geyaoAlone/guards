package com.geyao.guards.dao;

import com.geyao.guards.bean.CilentServiceConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends CrudRepository<CilentServiceConfig, String> {

    @Query(value = "select * from client_service_config",nativeQuery = true)
    List<CilentServiceConfig> queryByList();

    @Query(value = "select * from client_service_config where client_no=?1",nativeQuery = true)
    CilentServiceConfig queryByObj(String clientNo);

    @Query(value = "select * from client_service_config where ?1=?2",nativeQuery = true)
    CilentServiceConfig queryByAloneParam(String key,String clientName);



}
