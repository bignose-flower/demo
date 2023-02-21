package com.example.demo.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Student;
import com.example.demo.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService service;
	
	@GetMapping("/{id}")
	public Student get(@PathVariable Long id) {
		return service.getStudentById(id);
	}
	
	@GetMapping
	public List<Student> getAll() {
		return service.getStudents();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String upload(@RequestParam("uploadFile") MultipartFile file ) {
		//サイズチェック
		//ファイル未設定 or サイズ0
		if (file.isEmpty()) {
			throw new RuntimeException();
		}
		//上限チェック(5MB)
		if(file.getSize() > 5242880) {
			throw new RuntimeException();
		}
		return readFile(file);
	}
	
	// ファイルの読み込み
	// 読み込み結果の各行をカンマ区切りの一行へ
	private String readFile(MultipartFile uploadFile) {
		try(var is = uploadFile.getInputStream();
				var isr = new InputStreamReader(is, "Windows-31J");
				var br = new BufferedReader(isr);){
			//拡張子csv形式だった場合は、カンマをjoinせずに返却
			//String extention = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
			//if (extention == ".csv") {
				//return br.lines().collect(Collectors.joining());
			//}
			return br.lines().collect(Collectors.joining());
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
