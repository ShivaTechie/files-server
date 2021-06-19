package poc.filesserver.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import poc.filesserver.config.FileConfigProperties;
import poc.filesserver.dto.FileUploadResponse;
import poc.filesserver.service.FileUpload;

@Service
public class FileUploadImpl implements FileUpload {

	private FileConfigProperties fileConfigProperties;

	@Autowired
	public FileUploadImpl(FileConfigProperties fileConfigProperties) {
		this.fileConfigProperties = fileConfigProperties;
	}

	@Override
	public FileUploadResponse saveFile(MultipartFile file) {

		String filename = file.getOriginalFilename();
		FileUploadResponse response = new FileUploadResponse();
		Path path = null;
		try {
			path = Paths.get(fileConfigProperties.getUploadDir() + filename).normalize();
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {

		}
		response.setFileName(filename);
		response.setFileUri(path.toUri().getPath());
		response.setMessage("File uploaded successfully");
		response.setStatus("success");
		return response;
	}

}
