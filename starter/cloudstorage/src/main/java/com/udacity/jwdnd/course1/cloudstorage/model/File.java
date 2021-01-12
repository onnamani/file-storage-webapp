package com.udacity.jwdnd.course1.cloudstorage.model;


public class File {

    private Integer fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;

    public Integer getFileId() { return this.fileId; }
    public void setFileId(Integer fileId) { this.fileId = fileId; }

    public String getFilename() { return this.filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getContentType() { return this.contentType; }
    public void setContentType(String contentType) { this.contentType = contentType;}

    public String getFileSize() { return this.fileSize; }
    public void setFileSize(String fileSize) { this.fileSize = fileSize; }

    public Integer getUserId() { return this.userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public byte[] getFilePayload() { return this.fileData; }
    public void setFilePayload(byte[] fileData) { this.fileData = fileData; }
}
