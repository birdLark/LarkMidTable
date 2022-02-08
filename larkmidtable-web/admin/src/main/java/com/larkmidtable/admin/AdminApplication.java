package com.larkmidtable.admin;

import com.larkmidtable.admin.entity.Common;
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

@EnableSwagger2
@SpringBootApplication(exclude = {
		MongoAutoConfiguration.class, MongoDataAutoConfiguration.class
})
public class AdminApplication {

    private static Logger logger = LoggerFactory.getLogger(AdminApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        Environment env = new SpringApplication(AdminApplication.class).run(args).getEnvironment();
        String envPort = env.getProperty(Common.SERVERPORT);
        String envContext = env.getProperty(Common.SERVERCONTEXTPATH);
        String port = envPort == null ? Common.PORT : envPort;
        String docPage= Common.DOCPAGE;
        String context = envContext == null ? "" : envContext;
        String path = port + "" + context + docPage;
        String externalAPI = InetAddress.getLocalHost().getHostAddress();
        logger.info(
                "Access URLs:\n" //
				+ "----------------------------------------------------------\n\t" //
                + "Local-API: \t\thttp://127.0.0.1:{}\n\t" //
				+ "External-API: \thttp://{}:{}\n\t" //
				+ "web-URL: \t\thttp://127.0.0.1:{}/index.html\n\t" //
				+ "----------------------------------------------------------", //
                path, externalAPI, path, port);
    }
}
