package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Student;
import com.example.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository respository;
	
	// ID から検索
	public Student getStudentById(Long id) {
		return respository.findById(id).orElseThrow();
	}

	// 全件検索
	public List<Student> getStudents() {
		return respository.findAll();
	}

	// Student 登録
	public Student createStudent(Student student) {
		return respository.save(student);
	}

}