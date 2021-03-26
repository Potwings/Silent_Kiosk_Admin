package org.judy.notice.controller;

import java.util.List;

import org.judy.common.util.PageDTO;
import org.judy.notice.dto.NoticeDTO;
import org.judy.notice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeRestController {

	private final NoticeService service;
	
	@GetMapping(value = "/{page}/{perSheet}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<NoticeDTO>> getList(@PathVariable("page") Integer page, @PathVariable("perSheet") Integer perSheet) {
		
		log.info("list controller..............");
		
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(page);
		pageDTO.setPerSheet(perSheet);
		
		return new ResponseEntity<>(service.getList(pageDTO), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{nno}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<NoticeDTO> getOne(@PathVariable("nno") Integer nno) {
		
		return new ResponseEntity<>(service.getOne(nno),HttpStatus.OK);
	}
	
	@PostMapping("/")
	public void postInsert(@RequestBody NoticeDTO dto) {
		
		log.info("insert.................");
		
		service.insert(dto);
		
	}
	
	@PutMapping("/")
	public void modify() {
		
	}
	
	@DeleteMapping("/")
	public void delete(Integer nno) {
		
		log.info("delete................");
		
		service.delete(nno);
		
	}
	
	
}
