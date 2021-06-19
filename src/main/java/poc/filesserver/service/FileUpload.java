package poc.filesserver.service;

import org.springframework.web.multipart.MultipartFile;

import poc.filesserver.dto.FileUploadResponse;

public interface FileUpload {

	public FileUploadResponse saveFile(MultipartFile file);

}
