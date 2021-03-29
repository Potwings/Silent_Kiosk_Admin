package org.judy.notice.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/upload")
@Log4j
public class FileUploadController {

	 @PostMapping("/")
	 public ResponseEntity<String> uploadPost(MultipartFile[] uploadFile, Model model) {
		 
		 String path = "C:\\upload\\temp\\";
		 
		 log.info("upload------------------");
		 
		 for (MultipartFile multipartFile : uploadFile) {
			
			 log.info("---------------------------");
			 log.info("upload file name: " + multipartFile.getOriginalFilename());
			 log.info("upload file size: " + multipartFile.getSize());
			 
			 File uploadPath = new File(path, getFolder());
			 
			 if(uploadPath.exists() == false) {
				 
				 uploadPath.mkdirs();
			 }
			 
			 UUID uuid = UUID.randomUUID();
			 
			 String fileName = uuid.toString()+"_"+multipartFile.getOriginalFilename();
			 
			 File saveFile = new File(uploadPath ,fileName);
			 
			 try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
		    }
			 
		 }//end for
		 
		 return new ResponseEntity<String>("success",HttpStatus.OK);
	 }
	 
	 private String getFolder() {
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date date = new Date();
		 
		 String str = sdf.format(date);
		 
		 return str.replace("-", File.separator);
		 
	 }
	
}
