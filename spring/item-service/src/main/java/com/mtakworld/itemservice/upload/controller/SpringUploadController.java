package com.mtakworld.itemservice.upload.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class SpringUploadController {
	@Value("${file.dir}")
	private String fileDir;

	@GetMapping("/upload")
	public String newFile() {
		return "upload/upload-form";
	}

	@PostMapping("/upload")
	public String saveFile(@RequestParam String itemName, @RequestParam MultipartFile file,
		HttpServletRequest request) throws IOException {
		log.info("request={}", request);
		log.info("itemName={}", itemName);
		log.info("multipartFile={}", file);
		if (!file.isEmpty()) {
			String fullPath = fileDir + file.getOriginalFilename();
			log.info("saved file = {}", fullPath);
			file.transferTo(new File(fullPath));

		}
		return "upload/upload-form";
	}
}
