/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.davigotxi.springfilesuploader.storage;


import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class FileSystemStorageServiceTest {

    StorageProperties properties = new StorageProperties();
    FileSystemStorageService service;

    @BeforeEach
    void setUp() {
        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        service = new FileSystemStorageService(properties);
        service.init();
    }

    @Test
    void loadNonExistent() {
        assertThat(service.load("foo.txt")).doesNotExist();
    }

    @Test
    void saveAndLoad() {
        service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()));
        assertThat(service.load("foo.txt")).exists();
    }

    @Test
    void saveNotPermitted() {
        assertThrows(StorageException.class, () -> {
            service.store(new MockMultipartFile("foo", "../foo.txt",
                    MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
        });
    }

    @Test
    void savePermitted() {
        service.store(new MockMultipartFile("foo", "bar/../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
    }

}