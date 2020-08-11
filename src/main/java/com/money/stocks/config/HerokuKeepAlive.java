package com.money.stocks.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


public class HerokuKeepAlive {

    private static final long MIN_25 = 25 * 60 * 1000L;
    private static final Logger LOG = LoggerFactory.getLogger(HerokuKeepAlive.class);

    @Scheduled(fixedDelay = MIN_25)
    public void herokuKeepAlive() {
        LOG.debug("Heroku not idle execution");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("https://money-stocks.herokuapp.com/", Object.class);
    }

}
