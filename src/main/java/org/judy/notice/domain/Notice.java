package org.judy.notice.domain;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	
	private Integer nno;
	private String title;
	private String content;
	private String writer;
	private Boolean showed;
	private String category;
	private boolean img;
	private boolean file;
	private Timestamp regdate, updatedate;
}
