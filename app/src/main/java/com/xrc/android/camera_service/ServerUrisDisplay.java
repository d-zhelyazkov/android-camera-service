package com.xrc.android.camera_service;

import android.widget.TextView;
import com.xrc.util.Enumerations;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;

//TODO improve this class - name, implementation, etc.
class ServerUrisDisplay {

    static void display(TextView serverUrlsView) {

        try {
            StringBuilder urlsListBuilder = new StringBuilder();
            Enumerations.stream(NetworkInterface.getNetworkInterfaces())
                    .forEach(networkInterface -> Enumerations.stream(networkInterface.getInetAddresses())
                            .filter(inetAddress -> (inetAddress instanceof Inet4Address))
                            .filter(inetAddress -> !(inetAddress.isLoopbackAddress()))
                            .forEach(inetAddress -> {
                                try {
                                    URL url = new URL(
                                            Server.PROTOCOL.getSchemeName(),
                                            inetAddress.getHostAddress(),
                                            Server.PORT,
                                            WebApplication.PATH
                                    );
                                    urlsListBuilder.append("\n").append(url);
                                } catch (MalformedURLException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                    );

            serverUrlsView.setText(urlsListBuilder);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }

}
