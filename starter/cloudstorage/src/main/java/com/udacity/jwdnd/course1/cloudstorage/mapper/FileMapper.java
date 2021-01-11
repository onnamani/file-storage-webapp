package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT fileId, fileName FROM FILES WHERE userId = #{userId}")
    List<FileModel> getFileName(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileModel getFile(Integer fileId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, filePayload) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{filePayload})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(FileModel file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(Integer fileId);

}
