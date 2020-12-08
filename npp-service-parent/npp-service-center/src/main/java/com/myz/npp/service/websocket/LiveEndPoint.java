package com.myz.npp.service.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author yzMa
 * @desc
 * @date 2020/12/8 16:23
 * @email 2641007740@qq.com
 */
@Slf4j
@Component
@ServerEndpoint("/live-endpoint")
public class LiveEndPoint {

    @OnOpen
    public void onOpen(Session session){
        log.info("onOpen invoked !");
    }

    @OnMessage
    public void onMessage(String message,Session session){
        log.info("onMessage invoked!");
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        log.info("onError invoked!");
    }

    @OnClose
    public void onClose(Session session){
        log.info("onClose invoked");
    }
}

