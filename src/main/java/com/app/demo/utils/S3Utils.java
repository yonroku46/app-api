package com.app.demo.utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * AWS S3用ユティリティー
 *
 * @author y_ha
 */
@Component
public class S3Utils {

    /**
     * ファイルサーバヘルプクラス
     */
    protected S3Utils() {
    }


    /**
     * ファイルをアップロードする
     *
     * @param fileName ファイル名
     * @param fileType ファイルタイプ
     * @param is アップロードするデータが入ったストリーム
     * @param bucketName バケット名
     * @param prefix プリフィックス名
     * @param amazonS3 S3実例
     * @return
     */
    public static String uploadFile(String fileName, String fileType, InputStream is, String bucketName, String prefix, AmazonS3 amazonS3) {
        try {
            TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).withMinimumUploadPartSize(1L * 1024 * 1024).build();
            byte[] bytes = IOUtils.toByteArray(is);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType(fileType);
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            // アップロード実行
            String uploadName = new StringBuilder(prefix).append("/").append(fileName).toString();
            Upload upload = transferManager.upload(bucketName, uploadName, byteArray, metadata);
            upload.waitForCompletion();
            return uploadName;
        } catch (AmazonClientException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * ファイルをダウンロード
     *
     * @param bucketName
     * @param fileName
     * @param localFileName
     * @param amazonS3
     * @return
     * @throws AmazonClientException
     */
    public static boolean downloadFile(String bucketName, String fileName, String localFileName, AmazonS3 amazonS3) {
        try {
            TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
            // データダウンロード
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(fileName)
                    .withMarker("");

            ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);
            if (objectListing.getObjectSummaries().isEmpty()) {
                // 指定ファイルが存在しない場合処理を終了
                return false;
            }
            Download xfer = transferManager.download(bucketName, fileName, new File(localFileName));
            xfer.waitForCompletion();
        } catch (AmazonClientException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    /**
     * ファイル削除
     *
     * @param bucketName
     * @param fileName
     * @param amazonS3
     * @return
     * @throws AmazonClientException
     */
    public static boolean deleteFile(String bucketName, String fileName, AmazonS3 amazonS3) {
        try {
            TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
            // データダウンロード
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(fileName)
                    .withMarker("");

            ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);
            if (!objectListing.getObjectSummaries().isEmpty()) {
                transferManager.getAmazonS3Client().deleteObject(new DeleteObjectRequest(bucketName, fileName));
            }

        } catch (AmazonClientException e) {
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    /**
     * ファイル一覧を取得
     *
     * @param bucketName
     * @param objectName
     * @param amazonS3
     * @return fileList
     * @throws AmazonClientException
     */
    public static List<String> getFileList(String bucketName, String objectName, AmazonS3 amazonS3) {
        List<String> fileList = new ArrayList<>();
        try {
            // ファイル一覧を取得
            ObjectListing objListing = amazonS3.listObjects(bucketName, objectName);
            List<S3ObjectSummary> objList = objListing.getObjectSummaries();
            if (!CollectionUtils.isEmpty(objList)) {
                objList.forEach((obj) -> {
                    fileList.add(obj.getKey());
                });
            }
        } catch (AmazonServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        return fileList;
    }
}
