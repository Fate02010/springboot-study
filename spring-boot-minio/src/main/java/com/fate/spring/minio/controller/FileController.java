package com.fate.spring.minio.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import com.fate.spring.minio.service.FileService;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.NoResponseException;

/**
 * 文件上传下载接口
 * 
 * @author maijinchao
 *
 * @date 2020/09/17
 */
@RequestMapping("file")
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * 
     * @param multipartFiles
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     * @throws InvalidArgumentException
     * @throws InternalException
     * @throws ErrorResponseException
     * @throws NoResponseException
     * @throws InsufficientDataException
     * @throws NoSuchAlgorithmException
     * @throws InvalidBucketNameException
     * @throws InvalidKeyException
     */
    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam(name = "file") MultipartFile multipartFile)
        throws IOException, InvalidKeyException, InvalidBucketNameException, NoSuchAlgorithmException,
        InsufficientDataException, NoResponseException, ErrorResponseException, InternalException,
        InvalidArgumentException, XmlPullParserException {
        String fileName = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        String fileUrl =
            fileService.fileUpload(inputStream, fileName, multipartFile.getSize(), multipartFile.getContentType());
        return fileUrl;
    }

    /**
     * 下载
     * 
     * @param fileName
     *            文件名
     * @return
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
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> downloadFile(@PathVariable(name = "fileName") String fileName,
        HttpServletResponse response) throws InvalidKeyException, InvalidBucketNameException, NoSuchAlgorithmException,
        InsufficientDataException, NoResponseException, ErrorResponseException, InternalException,
        InvalidArgumentException, IOException, XmlPullParserException {
        fileService.download(fileName, response);
        return null;
    }

}
