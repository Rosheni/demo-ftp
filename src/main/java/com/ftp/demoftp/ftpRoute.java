package com.ftp.demoftp;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ftpRoute extends RouteBuilder {

    @Value("${loc.source}")
    private String src;

    @Override
    public void configure() throws Exception {
        consumeFileFromFtp();
        readMsg();
    }

    private void readMsg() {
        from("ftp://ftpuser@localhost:21/input?password=1234&fileName=Test_Camel.txt&noop=true")
                .routeId("Rename-route-demo")
                .log("Consumed file from FTP server ${body}")
                .log(LoggingLevel.INFO," ${file:name}")
                .setHeader(Exchange.FILE_NAME,simple("${file:name.noext}_Modified"))
                .to("ftp://ftpuser@localhost:21/output?password=1234");

    }

    private void consumeFileFromFtp() {
        from( src)
                .routeId("Ftp-route-demo")
                .log("Consumed file from FTP server ${body}");
    }

}
