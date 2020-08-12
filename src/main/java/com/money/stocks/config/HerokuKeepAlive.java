package com.money.stocks.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HerokuKeepAlive {

    private static final long MIN_15 = 15 * 60 * 1000L;
    private static final Logger LOG = LoggerFactory.getLogger(HerokuKeepAlive.class);

    @Scheduled(fixedDelay = MIN_15)
    public void herokuKeepAlive() {
        LOG.debug("Heroku not idle execution");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("https://money-stocks.herokuapp.com/stocks", Object.class);
    }

}
