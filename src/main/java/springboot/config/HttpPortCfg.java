package springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "httpportcfg")
public class HttpPortCfg {
    private String http_port;

    private String https_port;


}
