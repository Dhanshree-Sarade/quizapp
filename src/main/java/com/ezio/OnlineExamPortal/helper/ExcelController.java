package com.ezio.OnlineExamPortal.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
public class ExcelController {

	
	  @Autowired
	    private ExcelService excelService;

	    @GetMapping("/download-template")
	    @ResponseBody
	    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
	        ByteArrayInputStream in = excelService.createExcelTemplate();

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=template.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(new InputStreamResource(in));
	    }
}
