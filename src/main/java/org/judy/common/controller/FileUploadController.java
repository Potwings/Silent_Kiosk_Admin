package org.judy.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.judy.notice.domain.NoticeFileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/common")
@Log4j
public class FileUploadController {

	@GetMapping("/notice/view")
	@ResponseBody
	public ResponseEntity<byte[]> getView(String link) {

		String path = "C:\\upload\\temp\\admin\\notice";
		
		ResponseEntity<byte[]> result = null;
		
		try {
			String str = URLDecoder.decode(link, "UTF-8");

			String fileLink = str.replace("#", ".");

			File viewFile = new File(path, fileLink);

			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(viewFile.toPath()));
			
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(viewFile),header, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	

	@PostMapping("/notice/upload")
	public ResponseEntity<List<NoticeFileDTO>> uploadPost(MultipartFile[] uploadFile, Model model) {

		String path = "C:\\upload\\temp\\admin\\notice";

		log.info("upload------------------");

		List<NoticeFileDTO> fileList = new ArrayList<>();

		for (MultipartFile multipartFile : uploadFile) {

			log.info("---------------------------");
			log.info("upload file name: " + multipartFile.getOriginalFilename());
			log.info("upload file size: " + multipartFile.getSize());

			String folderPath = getFolder();

			File uploadPath = new File(path, folderPath);

			if (uploadPath.exists() == false) {

				uploadPath.mkdirs();
			}

			UUID uuid = UUID.randomUUID();

			String fileName = multipartFile.getOriginalFilename();

			File saveFile = new File(uploadPath, uuid.toString() + "_" + fileName);

			boolean isImage = multipartFile.getContentType().startsWith("image");

			try {

				if (isImage) {
					File sFile = new File(uploadPath, "s_" + uuid.toString() + "_" + fileName);

					FileOutputStream fos = new FileOutputStream(sFile);
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), fos, 100, 100);

				}
				NoticeFileDTO noticeFile = new NoticeFileDTO(folderPath,uuid.toString(), fileName,  isImage);
				
				fileList.add(noticeFile);
				
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} // end for

		return new ResponseEntity<>(fileList, HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping(value = "/notice/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String link){
		
		String path = "C:\\upload\\temp\\admin\\notice";
		
		String str = "";
		
		try {
			str = URLDecoder.decode(link, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String fileLink = str.replace("#", ".");

		File viewFile = new File(path, fileLink);
		
		Resource resource = new FileSystemResource(viewFile);
		
		String resourceName = resource.getFilename();
		
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);

	}

}
