//package com.vuan.controller.API;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController()
//@RequestMapping("/api")
//@CrossOrigin(origins = "*", maxAge = -1)
//public class CommonApiWebController {
//	
//	@GetMapping(value = "/download/{image}")
//	public void download(HttpServletResponse response, @PathVariable String image) {
//		System.out.println("method download image called");
//		final String uploadFolder = "D:\\file\\curnon\\";
//		File file = new File(uploadFolder + File.separator + image);
//		if (file.exists()) {
//			try {
//				Files.copy(file.toPath(), response.getOutputStream());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

//}
