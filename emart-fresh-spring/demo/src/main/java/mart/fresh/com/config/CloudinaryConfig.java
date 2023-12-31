package mart.fresh.com.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloudName}")
    private String cloudName;

    @Value("${cloudinary.apiKey}")
    private String apiKey;
    
    @Value("${cloudinary.apiSecret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
        return cloudinary;
    }
}