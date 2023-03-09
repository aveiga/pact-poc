package com.aveiga.pactpocconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(url = "localhost:8080", name = "guitars")
public interface ProducerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/guitars")
    List<Guitar> getGuitars();
}
