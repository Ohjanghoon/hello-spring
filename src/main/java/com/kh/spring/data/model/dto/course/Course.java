package com.kh.spring.data.model.dto.course;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	private long id;
	private String title;
	private int price;
	private LocalDate created;
	private List<Student> students;
}
