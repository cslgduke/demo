package com.example.demo.azure;

import com.alibaba.fastjson.JSON;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author i565244
 */

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AzureBlobServiceClientTest {

//    public static final String accessKeyContent = "SharedAccessSignature=se=2032-03-22T03%3A18%3A07Z&sp=rwdxlacupft&sv=2021-04-10&ss=b&srt=sco&sig=6GnsTCK9yeTJ1WgvcaHVIRdhMKANAdjJMwXY2zaeYmo%3D;BlobEndpoint=https://insightstoragedev.blob.core.windows.net/;";

    public static final String accessKeyContent = "BlobEndpoint=https://storagefdipoc.blob.core.windows.net/;QueueEndpoint=https://storagefdipoc.queue.core.windows.net/;FileEndpoint=https://storagefdipoc.file.core.windows.net/;TableEndpoint=https://storagefdipoc.table.core.windows.net/;SharedAccessSignature=sv=2021-06-08&ss=b&srt=sco&sp=rwdlacitfx&se=2032-06-29T15:09:58Z&st=2022-06-29T07:09:58Z&spr=https&sig=79%2BLs42g6SGW0Y994pznPoixZiod9Z5vMfaXeLWGT8k%3D";

    @Test
    public void test_blobContainerClient() {
        var blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(accessKeyContent)
                .buildClient();

        var container = "433746360799232-data-cic-ome-dev-rao-dev";
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
        var resources = getBlobOfDirectory(containerClient, "rao-ome-dev-rao-dev-data-publish");
        resources.forEach(rs -> log.info("resource item :{}", rs));
    }

    protected List<String> getBlobOfDirectory(BlobContainerClient containerClient, String directory) {
        List<String> resources = new LinkedList<>();
        ListBlobsOptions options = new ListBlobsOptions().setPrefix(directory);
        for (BlobItem blob : containerClient.listBlobsByHierarchy("/", options, Duration.ofSeconds(30))) {
            if (blob.isPrefix() != null && blob.isPrefix()) {
                log.info("enter the sub fold [{}]", blob.getName());
                resources.addAll(getBlobOfDirectory(containerClient, blob.getName()));
                log.info("exit the sub fold [{}]", blob.getName());
            } else {
                log.info("add blob [{}]", blob.getName());
                resources.add(blob.getName());
            }
        }
        return resources;
    }

    public static final String containerName = "1001-datalake-encrypt-poc";


    public static final String containerName_1002 = "1002-data-cic-di-poc";
    @Test
    public void test_createContainer() {
        var blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(accessKeyContent)
                .buildClient();

        var containerName = "1003-data-cic-di-poc";
        var scopeName = "scopeContainer1003";
        BlobContainerClient containerClient = blobServiceClient.createBlobContainer(containerName);
    }

    @Test
    public void test_uploadBlob() throws IOException {
        for (int i = 0; i < 5; i++) {
            String localPath = "./data/upload/";
            String fileName = "quickstart" + UUID.randomUUID() + ".txt";
            File localFile = new File(localPath + fileName);

            // Write text to the file
            FileWriter writer = new FileWriter(localPath + fileName, true);
            writer.write("Hello, World!");
            writer.write("uid:" + UUID.randomUUID());
            writer.close();


            var blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(accessKeyContent)
                    .buildClient();
            //container client
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName_1002);
//            containerClient.listBlobs().forEach(blob -> log.info("blob info:{}", JSON.toJSONString(blob)));

            //blob  client
            BlobClient blobClient = containerClient.getBlobClient("poc/encryption/cmk/" + fileName);
            blobClient.uploadFromFile(localPath + fileName);
        }

    }


    @Test
    public void test_download() throws IOException {
        String downLoadPath = "./data/download/";
        var blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(accessKeyContent)
                .buildClient();
        //container client
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        containerClient.listBlobs().forEach(blob -> {
            log.info("blob item info:{}", JSON.toJSONString(blob));
            var blobName = blob.getName();
            String downloadFileName = blobName.replace(".txt", "-DOWNLOAD.txt");
            File downloadedFile = new File(downLoadPath + downloadFileName);

            var blobClient = containerClient.getBlobClient(blobName);
            blobClient.downloadToFile(downLoadPath + downloadFileName);
            log.info("download {} to {} success",blobName,downloadFileName);
        });
    }
}
