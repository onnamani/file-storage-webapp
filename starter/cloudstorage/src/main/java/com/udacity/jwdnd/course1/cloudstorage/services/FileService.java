package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

@Service
@Transactional
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) { this.fileMapper = fileMapper; }

    public List<File> getFiles(Integer userId) {
        return this.fileMapper.getFiles(userId);
    }

    public File getFile(Integer fileId) { return this.fileMapper.getFile(fileId); }

    public boolean isFileNameAvailable(String fileName, Integer userId) {
        List<File> userFiles = fileMapper.getFiles(userId);
        if (userFiles.size() == 0) return true;
        else{
            return !userFiles.contains(fileName);
        }
    }

    public Integer insertFile(MultipartFile multipartFile, Integer userId) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();

        File file = new File();

        file.setFilename(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize());
        file.setUserId(userId);
        file.setFilePayload(multipartFile.getBytes());

        return fileMapper.insertFile(file);

    }
}
