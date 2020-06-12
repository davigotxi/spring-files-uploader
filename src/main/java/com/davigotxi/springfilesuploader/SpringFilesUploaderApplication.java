package com.davigotxi.springfilesuploader;

import com.davigotxi.springfilesuploader.storage.StorageProperties;
import com.davigotxi.springfilesuploader.storage.StorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringFilesUploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFilesUploaderApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
