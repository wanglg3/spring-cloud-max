package cloud.max.core.properties;



import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;


@Data
@ConfigurationProperties("max.cloud.security")
public class SecurityProperties {

	private String publicKey;

	private String privateKey;

	public PublicKey publicKey() {
		return new RSA(null, this.publicKey).getPublicKey();
	}

	public RSAPublicKey rsaPublicKey() {
		return (RSAPublicKey) publicKey();
	}

	public PrivateKey privateKey() {
		return new RSA(this.privateKey, null).getPrivateKey();
	}

}
