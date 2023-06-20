package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class ServerInfoLauncher {
    public static void main(String[] args) throws InterruptedException {
        Configuration conf = new Configuration();
        conf.setHostname("localhost");
        conf.setPort(10106);

        SocketIOServer server = new SocketIOServer(conf);
        boolean syncInfo = false;

        server.addConnectListener(e -> {

            System.out.println("客户端已链接");
        });

        server.addDisconnectListener(e -> {
            System.out.println("客户端已断开链接");
        });

        server.addEventListener("req_server_info",ServerObject.class,(c, d, r)->{
            while (true){
                d = new ServerObject();
                d.copyTo();
                server.getBroadcastOperations().sendEvent("res_server_info", d);
                Thread.sleep(100);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
