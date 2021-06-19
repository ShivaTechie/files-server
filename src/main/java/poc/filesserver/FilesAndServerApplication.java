package poc.filesserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import poc.filesserver.config.FileConfigProperties;

@EnableConfigurationProperties(FileConfigProperties.class)
@SpringBootApplication
public class FilesAndServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilesAndServerApplication.class, args);
	}

}
