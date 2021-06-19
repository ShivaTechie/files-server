package poc.filesserver.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import poc.filesserver.dto.FileUploadResponse;
import poc.filesserver.service.FileDownload;
import poc.filesserver.service.FileUpload;

@RestController
public class FileServerController {

	private static final Logger logger = LoggerFactory.getLogger(FileServerController.class);

	private FileUpload fileUploadService;
	private FileDownload fileDownloadService;

	@Autowired
	public FileServerController(FileDownload fileDownloadService, FileUpload fileUploadService) {

		this.fileUploadService = fileUploadService;
		this.fileDownloadService = fileDownloadService;

	}

	@GetMapping("/file/download/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable(value = "filename") String fileName,
			HttpServletRequest request) throws IOException {

		logger.info(String.format("In method getFile() of class %s with paramter filename: %s",
				this.getClass().toString(), fileName));
		Resource resource = fileDownloadService.getFile(fileName);

		HttpHeaders headers = new HttpHeaders();
		List<String> list = new ArrayList<>();
		list.add("attachment; filename=\"" + resource.getFilename() + "\"");
		headers.put(HttpHeaders.CONTENT_DISPOSITION, list);
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/file/upload/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

		logger.info(String.format("In method uploadFile() of class %s with paramter file: %s",
				this.getClass().toString(), file.getOriginalFilename()));
		FileUploadResponse fileUploadResponse = fileUploadService.saveFile(file);
		logger.info(String.format("file: %s successfully saved: ", fileUploadResponse.getFileName()));
		return new ResponseEntity<FileUploadResponse>(fileUploadResponse, HttpStatus.OK);
	}

	@PostMapping("/file/uploads/")
	public ResponseEntity<List<FileUploadResponse>> uploadFiles(@RequestParam(value = "file") MultipartFile[] file) {

		logger.info(String.format("In method uploadFiles() of class %s", this.getClass().toString()));

		List<FileUploadResponse> files = Arrays.asList(file).stream().map(fileObject -> {
			ResponseEntity<FileUploadResponse> uploadData = null;
			try {
				uploadData = uploadFile(fileObject);
			} catch (IOException e) {
				System.out.println("exception occurred in saving files!!");
				logger.info(String.format("Exception %s occurred in saving file %s!!", e.getLocalizedMessage(),
						fileObject.getOriginalFilename()));
			}
			return uploadData.getBody();
		}).collect(Collectors.toList());

		return new ResponseEntity<List<FileUploadResponse>>(files, HttpStatus.OK);
	}
}
