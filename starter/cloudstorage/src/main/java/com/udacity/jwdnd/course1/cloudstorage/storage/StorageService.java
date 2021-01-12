package com.udacity.jwdnd.course1.cloudstorage.storage;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public interface StorageService {

    Integer store(MultipartFile file, Integer userId) throws IOException;

    File getFileByName(String filename);

    List<File> getUserFileNames(Integer userId);

    File getFileById(Integer fileId);

    Integer deleteFile(Integer fileId);
}
