package br.com.ricvon.imageliteapi;

import br.com.ricvon.imageliteapi.domain.entity.Image;
import br.com.ricvon.imageliteapi.domain.enums.ImageExtension;
import br.com.ricvon.imageliteapi.infra.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImageliteapiApplication {
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired ImageRepository repository){
        return args -> {
            Image image = Image
                    .builder()
                    .extension(ImageExtension.PNG)
                    .name("MYIMAGE")
                    .tags("teste")
                    .size(1000L)
                    .build();
            repository.save(image);
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
