package com.udacity.jwdnd.course1.cloudstorage.model;

public class FileModel {

    private Integer fileId;
    private String filename;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] filePayload;

    public Integer getFileId() { return this.fileId; }
    public void setFileId(Integer fileId) { this.fileId = fileId; }

    public String getFilename() { return this.filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getContentType() { return this.contentType; }
    public void setContentType(String contentType) { this.contentType = contentType;}

    public Long getFileSize() { return this.fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public Integer getUserId() { return this.userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public byte[] getFilePayload() { return this.filePayload; }
    public void setFilePayload(byte[] filePayload) { this.filePayload = filePayload; }
}
