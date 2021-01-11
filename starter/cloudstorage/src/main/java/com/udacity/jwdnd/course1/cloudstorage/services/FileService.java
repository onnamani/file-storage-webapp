package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
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

    public List<FileModel> getFiles(Integer userId) {
        return this.fileMapper.getFiles(userId);
    }

    public boolean isFileNameAvailable(String fileName, Integer userId) {
        List<FileModel> userFiles = fileMapper.getFiles(userId);
        if (userFiles.size() == 0) return true;
        else{
            return !userFiles.contains(fileName);
        }
    }

    public Integer insertFile(MultipartFile multipartFile, Integer userId) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();

        FileModel fileModel = new FileModel();

        fileModel.setFilename(multipartFile.getOriginalFilename());
        fileModel.setContentType(multipartFile.getContentType());
        fileModel.setFileSize(multipartFile.getSize());
        fileModel.setUserId(userId);
        fileModel.setFilePayload(multipartFile.getBytes());

        return fileMapper.insertFile(fileModel);

    }
}
