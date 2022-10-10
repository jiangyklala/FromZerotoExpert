package com.jiang.fzte.timer;

import com.jiang.fzte.domain.Ipuvpv_data;
import com.jiang.fzte.domain.Ipuvpv_dataExample;
import com.jiang.fzte.mapper.Ipuvpv_dataMapper;
import com.jiang.fzte.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class IPUVPVTransfer {

    @Resource
    UserService userService;

    @Resource
    Ipuvpv_dataMapper ipuvpv_dataMapper;

    @Scheduled(cron = "0 0 3 * * ?")
    public void ipuvpvTransfer() {
        Date date = new Date();
        String twoDaysBefore = new SimpleDateFormat("yyyyMMdd").format(new Date(date.getTime() - 2 * 24 * 60 * 60 * 1000)); // 只要年月日
        String oneMonthBefore = new SimpleDateFormat("yyyyMMdd").format(new Date(date.getTime() - 30L * 24 * 60 * 60 * 1000)); // 只要年月日
        Jedis jedis = null;

        try {
            jedis = userService.jedisPool.getResource();
            Ipuvpv_data ipuvpv_data = new Ipuvpv_data();

            ipuvpv_data.setDate(twoDaysBefore);
            if (jedis.exists("fU:ip:" + twoDaysBefore)) {
                ipuvpv_data.setIp(Long.toString(jedis.pfcount("fU:ip:" + twoDaysBefore)));
                jedis.expire("fU:ip:" + twoDaysBefore, 0);  // redis中删除
            } else {
                ipuvpv_data.setIp("0"); // 如果当天无数据, 就记为 0
            }
            if (jedis.exists("fU:uv:" + twoDaysBefore)) {
                ipuvpv_data.setUv(Long.toString(jedis.pfcount("fU:uv:" + twoDaysBefore)));
                jedis.expire("fU:uv:" + twoDaysBefore, 0);
            } else {
                ipuvpv_data.setUv("0");
            }
            if (jedis.exists("fU:pv:" + twoDaysBefore)) {
                ipuvpv_data.setPv(jedis.get("fU:pv:" + twoDaysBefore));
                jedis.expire("fU:pv:" + twoDaysBefore, 0);
            } else {
                ipuvpv_data.setPv("0");
            }

            ipuvpv_dataMapper.insert(ipuvpv_data);  // mysql中增加

            // 根据[日期]删除这条数据
            Ipuvpv_dataExample ipuvpv_dataExample = new Ipuvpv_dataExample();
            Ipuvpv_dataExample.Criteria criteria = ipuvpv_dataExample.createCriteria();
            criteria.andDateEqualTo(oneMonthBefore);

            ipuvpv_dataMapper.deleteByExample(ipuvpv_dataExample); // mysql中删除

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
