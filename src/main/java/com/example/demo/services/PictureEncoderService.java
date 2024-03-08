package com.example.demo.services;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class PictureEncoderService {
	
	public String encode(byte[] image) {
		return Base64.getEncoder().encodeToString(image);
	}
}
