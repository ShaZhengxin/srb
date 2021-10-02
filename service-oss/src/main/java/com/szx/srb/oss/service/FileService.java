package com.szx.srb.oss.service;

import java.io.InputStream;

/**
 * @author szx
 * @description TODO
 * @date 2021/10/2  18:32
 */
public interface FileService {
    /**
     * 文件上传至阿里云
     */
    String upload(InputStream inputStream,String module,String fileName);

    /**
     * 根据路径删除文件
     * @param url
     */
    void removeFile(String url);
}
