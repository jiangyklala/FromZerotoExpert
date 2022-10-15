//package com.jiang.fzte.webSocket;
//
//import com.alibaba.fastjson.JSON;
//import com.jiang.fzte.service.UserService;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//@ServerEndpoint("/WebSocket")
//public class UserWebSocketServer {
//
//    public static UserService userService;
//
//    // 用来记录当前在线连接数, 线程安全?
//    private static int onlineCount = 0;
//
//    // 与某个客户端连接的会话
//    private Session session;
//
//    /**
//     * 连接成功调用方法
//     */
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
//        addOnlineCount();
//    }
//
//    /**
//     * 连接关闭调用方法
//     */
//    public void onClose() {
//        subOnlineCount();
//    }
//
//    /**
//     * 服务器发送信息
//     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    /**
//     * 服务器接受消息
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        if (Objects.equals(message, "1")) {
//            List<String> stringList = new ArrayList<>();
//            stringList.add(message);
//            stringList.add(Integer.toString(getOnlineCount()));
//            sendMessage(JSON.toJSONString(stringList));
//        }
////        logger.debug("收到用户{}的消息{}",this.userId,message);
////        session.getBasicRemote().sendText("收到 "+this.userId+" 的消息 "); //回复用户
//    }
//
//    public static int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static void addOnlineCount() {
//        ++UserWebSocketServer.onlineCount;
//    }
//
//    public static void subOnlineCount() {
//        --UserWebSocketServer.onlineCount;
//    }
//}
