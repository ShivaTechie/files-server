package poc.filesserver.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import poc.filesserver.config.FileConfigProperties;
import poc.filesserver.service.FileDownload;

@Service
public class FileDownloadImpl implements FileDownload {
	
	private FileConfigProperties fileConfigProperties;
	
	@Autowired
	public FileDownloadImpl(FileConfigProperties fileConfigProperties) {
		this.fileConfigProperties = fileConfigProperties;
	}
	
	@Override
	public Resource getFile(String fileName) {
		
		Resource resource = null;
		try {
			Path file = Paths.get(fileConfigProperties.getUploadDir() + fileName).normalize();
			resource = new UrlResource(file.toUri());
			
			if(resource.exists())
				return resource;
		} catch (IOException e) {
			System.out.println("exception occurred!!");
		}
		return null;
	}

}
