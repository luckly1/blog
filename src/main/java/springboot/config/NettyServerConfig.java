package springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xj
 */
@Data
@Component
@ConfigurationProperties(prefix = "nettyserver")
public class NettyServerConfig {
    private String serviceIp;
    private String servicePort;

}
