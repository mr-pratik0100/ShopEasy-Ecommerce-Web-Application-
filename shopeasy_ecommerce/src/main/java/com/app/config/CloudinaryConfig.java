package com.app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	@Bean
	public Cloudinary getCloudinary() {
		Map map=new HashMap();
		map.put("cloud_name", "drcchhnrb");
		map.put("api_key","659199161687342");
		map.put("api_secret", "4p_FpwyQTXadfK8ScAKr5FixdzQ");
		
		return new Cloudinary(map);
	}
}
