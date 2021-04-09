package org.judy.notice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.judy.common.util.NoticeFileDTO;
import org.judy.common.util.PageDTO;
import org.judy.common.util.PageMaker;
import org.judy.notice.dto.NoticeDTO;
import org.judy.notice.service.NoticeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/notice")
@Log4j
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService service;

	@GetMapping("/list")
	public void getList(PageDTO pageDTO, Model model) {

		PageMaker pageMaker = new PageMaker(pageDTO, service.getTotal(pageDTO));

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("list", service.getList(pageDTO));

	}

	@GetMapping("/read")
	public void getOne(@ModelAttribute("nno") Integer nno, PageDTO pageDTO, Model model) {

		model.addAttribute("notice", service.getOne(nno));
	}

	@GetMapping("/register")
	public void getInsert(PageDTO pageDTO) {

	}

	@PostMapping("/register")
	public ResponseEntity<String> postInsert(@RequestBody NoticeDTO dto) {

		log.info("insert.................");
		log.info("------------------");
		log.info(dto);
		log.info("------------------");

		String path = "C:\\upload\\admin\\notice\\" + getFolder();

		File uploadPath = new File(path);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		dto.getList().forEach(file -> copyFile(file));

		service.insert(dto);

		return new ResponseEntity<String>("success", HttpStatus.OK);

	}

	@GetMapping("/modify")
	public void getModify(@ModelAttribute("nno") Integer nno, Model model) {

		model.addAttribute("notice", service.getOne(nno));

	}

	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody NoticeDTO dto) {

		log.info("dto: " + dto);

		service.update(dto);

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		return new ResponseEntity<String>("수정하였습니다.", resHeaders, HttpStatus.OK);
	}

	@PostMapping("/delete")
	@ResponseBody
	public ResponseEntity<String> delete(Integer nno) {

		log.info("delete................");

		log.info("------------------");
		log.info(nno);
		log.info("------------------");

		service.delete(nno);

		return new ResponseEntity<String>("success", HttpStatus.OK);

	}

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);

	}

	private void copyFile(NoticeFileDTO file) {

		String originalFile = file.getUploadPath() + "\\" + file.getUuid() + "_" + file.getFileName();

		String sOriginalFile = file.getUploadPath() + "\\s_" + file.getUuid() + "_" + file.getFileName();

		makeCopyFile(originalFile);

		if (file.isImage()) {
			makeCopyFile(sOriginalFile);
		}
	}

	private void makeCopyFile(String originalFile) {

		File tempFile = new File("C:\\upload\\temp\\admin\\notice\\" + originalFile);
		try {
			FileInputStream inputStream = new FileInputStream(tempFile);
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);

			File targetFile = new File("C:\\upload\\admin\\notice\\" + originalFile);
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			outStream.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tempFile.delete();

	}

}
