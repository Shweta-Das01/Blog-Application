package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;
@Service
public class FIleSErviceimpl implements FileService {

	@Override
	public String UploadImage(String path, MultipartFile file) throws IOException {
		//file name
		String name=file.getOriginalFilename();
		//full path
		
		String filePath = path+ File.separator+name;
		
		String randomId=UUID.randomUUID().toString();//for random id of a string
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		
		//create file if it's not exist
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName ;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		return is;
	}

}
