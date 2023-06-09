package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("*****插入操作返回结果："+ result);
        if(result >0)
        {
            return new CommonResult<>(200,"插入数据库成功" + result +"\t 服务端口：" + serverPort,payment);
        }
        else
        {
            return new CommonResult<>(444,"插入数据库失败",null);
        }
    }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id")  Long id)
    {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询操作返回结果："+ payment);
        log.info("6666");
        log.info("6666");
        if(payment != null)
        {
            return new CommonResult<>(200,"查询数据库成功"+" 服务端口：" + serverPort,payment);
        }
        else
        {
            return new CommonResult<>(444,"没有对应记录，查询Id：",null);
        }
    }
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service:services) {
            log.info("***********service:"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance: instances){
            log.info(instance.getInstanceId()+"\t" +instance.getHost()+"\t" +instance.getPort());
        }
        return this.discoveryClient;
    }
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        System.out.println("******paymentFeignTimeOut from port :" + serverPort);
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
