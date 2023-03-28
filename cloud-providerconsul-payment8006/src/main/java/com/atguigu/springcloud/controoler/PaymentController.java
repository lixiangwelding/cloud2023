package com.atguigu.springcloud.controoler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/consul")
    public String paymentInfo(){
        return "springcloud with consul: "+serverPort+"\t\t"+ UUID.randomUUID().toString();
    }
}
