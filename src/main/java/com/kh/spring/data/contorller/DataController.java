package com.kh.spring.data.contorller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.data.model.dto.course.Course;
import com.kh.spring.data.model.dto.course.Student;
import com.kh.spring.data.model.service.DataService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/data")
public class DataController {

	@Autowired
	DataService dataService;
	
	@GetMapping("/data.do")
	public void data() {}
	
	//비동기 통신에서는 보통 ResponseEntity 사용
	@GetMapping("/xml/course.do")
	public ResponseEntity<?> xmlCourse(){
		
//		Course course = dataService.getXmlCourse();
		Course course = dataService.getXmlCourse2();
		log.debug("course = {}", course);
		
		//학생 이름 오름차순 정렬
		//기본 정렬 : Student implements Comparable
		//그외 정렬 : ComparatorImpl implements Comparator
		List<Student> students = course.getStudents();
		
		//익명 클래스 : 클래스 선언 + 객체 생성
		students.sort(new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				return o1.getName().compareTo(o2.getName());
			}
			
		});
		
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(course);
		//contentType=application/json;  charset=utf-8
	}
	
	@GetMapping("/json/course.do")
	public ResponseEntity<?> jsonCourse(){
		
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(dataService.getJsonCourse());
	}
	
	@GetMapping("/covid19.do")
	public ResponseEntity<?> covid19(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
		log.debug("date = {}", date);
		
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(dataService.getCovid19Status(date).getBody().getItems());
	}
}
