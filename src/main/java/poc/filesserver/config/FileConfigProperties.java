package poc.filesserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileConfigProperties {

	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	@Override
	public String toString() {
		return "FileConfigProperties [uploadDir=" + uploadDir + "]";
	}

}
