package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {

    @Select("SELECT fileId, filename, contentType FROM FILES WHERE userId = #{userId}")
    List<File> getFileName(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByName(String filename);

    @Insert("INSERT INTO FILES (filename, contentType, fileSize, userId, fileData) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(Integer fileId);

}
