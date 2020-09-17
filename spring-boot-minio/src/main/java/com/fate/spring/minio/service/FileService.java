package com.fate.spring.minio.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import com.fate.spring.minio.config.FileServerConfig;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件业务处理
 * 
 * @author maijinchao
 *
 * @date 2020/09/17
 */
@Slf4j
@Service
public class FileService {

    /**
     * 配置
     */
    @Autowired
    private FileServerConfig fileServerConfig;

    /**
     * 客户端
     */
    private MinioClient minioClient = null;

    /**
     * 初始化函数
     * 
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InternalException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InsufficientDataException
     * @throws NoSuchAlgorithmException
     * @throws InvalidBucketNameException
     * @throws InvalidKeyException
     * @throws RegionConflictException
     */
    @PostConstruct
    private void init() throws InvalidEndpointException, InvalidPortException, InvalidKeyException,
        InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, NoResponseException,
        ErrorResponseException, InternalException, IOException, XmlPullParserException, RegionConflictException {
        // 初始化文件服务
        minioClient = new MinioClient(fileServerConfig.getUrl(), fileServerConfig.getUser(), fileServerConfig.getPwd());

        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(fileServerConfig.getBucket());
        if (isExist) {
            log.info("Bucket already exists.");
        } else {
            // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
            minioClient.makeBucket(fileServerConfig.getBucket());
        }
    }

    /**
     * 上传文件到 minio
     * 
     * @param inputStream
     *            输入流
     * @param fileName
     *            文件名
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InvalidArgumentException
     * @throws InternalException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InsufficientDataException
     * @throws NoSuchAlgorithmException
     * @throws InvalidBucketNameException
     * @throws InvalidKeyException
     */
    public String fileUpload(InputStream inputStream, String fileName, long fileSize, String fileType)
        throws InvalidKeyException, InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException,
        NoResponseException, ErrorResponseException, InternalException, InvalidArgumentException, IOException,
        XmlPullParserException {
        String objectName = UUID.randomUUID().toString();
        this.minioClient.putObject(fileServerConfig.getBucket(), fileName, inputStream, fileSize,
            fileType);
        log.info("文件上传成功!");
        return fileServerConfig.getUrl() + "/" + fileServerConfig.getBucket() + "/" + fileName;
    }

    /**
     * 下载文件
     * 
     * @param fileName
     *            文件名
     * @param response
     *            返回对象
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InternalException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InsufficientDataException
     * @throws NoSuchAlgorithmException
     * @throws InvalidBucketNameException
     * @throws InvalidKeyException
     * @throws InvalidArgumentException
     */
    public void download(String fileName, HttpServletResponse response)
        throws InvalidKeyException, InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException,
        NoResponseException, ErrorResponseException, InternalException, IOException, XmlPullParserException,
        InvalidArgumentException {
        final ObjectStat stat = minioClient.statObject(fileServerConfig.getBucket(), fileName);
        response.setContentType(stat.contentType());
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        InputStream is = minioClient.getObject(fileServerConfig.getBucket(), fileName);
        IOUtils.copy(is, response.getOutputStream());
        is.close();

    }

}
