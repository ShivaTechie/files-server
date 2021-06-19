package poc.filesserver.service;

import org.springframework.core.io.Resource;

public interface FileDownload {

	public Resource getFile(String fileName);

}
