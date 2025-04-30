package com.hmdp;

import com.hmdp.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test1 {
    @Autowired
    private ShopServiceImpl shopService;
    @Test
    public void Test() throws InterruptedException {
        shopService.saveShop2Redis(1L, 10L);
    }
}
