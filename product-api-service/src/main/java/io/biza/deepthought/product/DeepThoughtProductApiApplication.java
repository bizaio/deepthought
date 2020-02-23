package io.biza.deepthought.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@ComponentScan({"io.biza.deepthought.shared.component.persistence",
  "io.biza.deepthought.shared.component.mapper", "io.biza.deepthought.product"})
public class DeepThoughtProductApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(DeepThoughtProductApiApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenAPI(@Value("${deepthought.version}") String appVersion) {
    /**
     * OpenID Connect is available in OAS annotations but not yet in swagger-ui :(
     * https://github.com/swagger-api/swagger-ui/issues/3517
     * 
     * TODO: Contribute OpenID support to swagger-ui We want our swagger definition to be accurate
     * so we leave this as is but it means swagger-ui is a "view spec" only interface
     */
    return new OpenAPI().info(new Info().title("Deep Thought Product API").version(appVersion)
        .description(
            "This is the Deep Thought Product API. You can find out more about Deep Thought at [https://github.com/bizaio/deepthought](https://github.com/bizaio/deepthought) or on the [DataRight.io Slack, #oss](https://join.slack.com/t/datarightio/shared_invite/enQtNzAyNTI2MjA2MzU1LTU1NGE4MmQ2N2JiZWI2ODA5MTQ2N2Q0NTRmYmM0OWRlM2U5YzA3NzU5NDYyODlhNjRmNzU3ZDZmNTI0MDE3NjY).")
        .license(new License().name("GPL 3.0")
            .url("https://github.com/bizaio/deepthought/blob/develop/LICENSE")));
  }
}
