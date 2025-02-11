package cloud.max.passport.properties;

import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author: wanglg
 * @create: 2025-02-11 21:41
 **/
@Data
@Component
@ConfigurationProperties("max.cloud.passport")
@RefreshScope
public class PassportProperties {
    private String title;
}
