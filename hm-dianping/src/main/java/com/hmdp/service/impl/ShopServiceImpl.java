package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_TTL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static long sumTimeForCache;
    private static long sumTimeForDB;

    @Override
    public Result queryById(Long id) {
//        long beginTime = System.currentTimeMillis();

        String s = stringRedisTemplate.opsForValue().get("cache:shop:" + id);
        if(StrUtil.isNotBlank(s)) {
            long dataTime = System.currentTimeMillis();
//            sumTimeForCache += (dataTime - beginTime);
//            System.out.println("sumTimeForCache = " + sumTimeForCache);
            Shop shop = JSONUtil.toBean(s, Shop.class);
            return Result.ok(shop);
        }
        Shop shop = getById(id);

//        sumTimeForDB += (System.currentTimeMillis() - beginTime);
//        System.out.println("sumTimeForDB = " + sumTimeForDB);

        if(shop == null) {
            return Result.fail("店铺信息不存在");
        }
        stringRedisTemplate.opsForValue().set("cache:shop:" + id, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return Result.ok(shop);
    }
}
