package org.judy.notice.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.validation.constraints.NotBlank;

import org.judy.common.util.NoticeFileDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Data
@Log4j
public class NoticeDTO {

	private Integer nno;
	@NotBlank(message = "제목을 입력하세요")
	private String title;
	@NotBlank(message = "내용을 입력하세요")
	private String content;
	private String writer;
	private Boolean showed;
	private String category;
	private boolean img;
	private boolean file;
	private Timestamp regdate, updatedate;
	private ArrayList<NoticeFileDTO> list;
	
	private String regdate1,updatedate1;

	public String getTimeFormat(Timestamp time) {

		// db regdate
		long timeValue = time.getTime();
		
		log.info("regdate"+regdate);

		log.info("timevalue:" + timeValue);

		// 오늘날짜
		Date currentDate = new Date(new java.util.Date().getTime());

		log.info("currentdate:" + currentDate);

		// 오늘날짜 long 변환
		long currentValue = currentDate.getTime();

		log.info("currentvalue:" + currentValue);

		// 오늘날짜 - db날짜
		long dis = currentValue - timeValue;

		log.info("dis:" + dis);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

		String result = "";

		if (dis > 86400000) {

			result = sdf.format(timeValue);

			log.info("1111" + result);
		} else {

			result = sdf2.format(timeValue);

			log.info("2222" + result);
		}

		return result;
		

	}

}
