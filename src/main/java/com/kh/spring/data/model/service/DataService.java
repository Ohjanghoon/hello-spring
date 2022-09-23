package com.kh.spring.data.model.service;

import java.time.LocalDate;

import com.kh.spring.data.model.dto.course.Course;
import com.kh.spring.data.model.dto.covid19.Response;

public interface DataService {

	Course getXmlCourse();

	Course getXmlCourse2();

	Object getJsonCourse();

	Response getCovid19Status(LocalDate date);

}
