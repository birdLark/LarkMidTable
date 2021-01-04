package com.guoliang.flinkx.admin;

import com.guoliang.flinkx.admin.entity.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Engine是flinkx-web入口类，该类负责数据的初始化
 */
@EnableSwagger2
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class Engine {

    private static Logger logger = LoggerFactory.getLogger(Engine.class);

    public static void main(String[] args) throws UnknownHostException {
        Environment env = new SpringApplication(Engine.class).run(args).getEnvironment();
        String port = env.getProperty(Common.SERVERPORT)== null ? Common.PORT : env.getProperty(Common.SERVERPORT);
        String context = env.getProperty(Common.SERVERCONTEXTPATH)== null ? Common.PORT : env.getProperty(Common.SERVERCONTEXTPATH);
        String docPath = Common.DOCPATH;
        String path = port + "" + context + docPath;
        String externalAPI = InetAddress.getLocalHost().getHostAddress();
        logger.info(
                "Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local-API: \t\thttp://127.0.0.1:{}\n\t"
                        + "External-API: \thttp://{}:{}\n\t"
                        + "web-URL: \t\thttp://127.0.0.1:{}/index.html\n\t----------------------------------------------------------",
                path, externalAPI, path, port);
    }
}
