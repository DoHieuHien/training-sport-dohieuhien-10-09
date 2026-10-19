package com.dohieuhien.shop.trainingsport.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "openligadb", url = "${openligadb.base-url}")
public interface OpenLigaDbClient  {

}
