package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService implements StorageService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) { this.fileMapper = fileMapper; }


    @Override
    public Integer store(MultipartFile multipartFile, Integer userId) throws IOException {
        File file = new File();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(String.valueOf(multipartFile.getSize()));
        file.setUserId(userId);
        file.setFilePayload(multipartFile.getBytes());

        return this.fileMapper.insertFile(file);
    }

    @Override
    public File getFileByName(String filename) {
        return this.fileMapper.getFileByName(filename);
    }

    @Override
    public List<File> getUserFileNames(Integer userId) {
        return this.fileMapper.getFileName(userId);
    }

    @Override
    public File getFileById(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }

    @Override
    public Integer deleteFile(Integer fileId) {
        return this.fileMapper.deleteFile(fileId);
    }

    public boolean isFileNameAvailable(String filename) {
        return getFileByName(filename) == null;
    }
}
