package com.isg.springboottest.service.impl;


import com.aliyun.oss.model.OSSObject;
import com.isg.springboottest.common.FileResponseUtils;
import com.isg.springboottest.common.FileUploadUtils;
import com.isg.springboottest.common.FileUtil;
import com.isg.springboottest.service.FileService;
import com.isg.springboottest.util.OSSUtils;
import com.isg.springboottest.vo.request.DownloadFileParam;
import com.isg.springboottest.vo.request.DownloadFileRequestParam;
import com.isg.springboottest.vo.response.ViewResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.FileSystemResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void batchDownloadFiles(DownloadFileRequestParam params, String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //if(params.getIdList().size()==1&&params.getIdList().get(0).getDirFlag().equals(0))
        if(true)
        {
            ViewResult result=null;
            Map<String,String> map=new HashMap<>();
            try
            {
//                org.springframework.http.HttpHeaders httpHeaders=new org.springframework.http.HttpHeaders();
//                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//                HttpEntity<String> entity=new HttpEntity<>("",httpHeaders);
//                ResponseEntity<ViewResult> responseEntity=restTemplate.postForEntity("",entity,ViewResult.class);
//                if(responseEntity!=null&&responseEntity.getStatusCodeValue()== HttpStatus.OK.value())
//                {
//                    result=responseEntity.getBody();
//                }
                for(int i=0;i<=0;i++) {
                    InputStream ossObjectInputStream = OSSUtils.downLoad("http://sfa-workbench-plus.oss-cn-shanghai.aliyuncs.com/sfaworkbench/0591954d-dfe8-4614-bee6-4adac1359958/contractimporttemplate.xlsx").getObjectContent();
                    String key=OSSUtils.downLoad("http://sfa-workbench-plus.oss-cn-shanghai.aliyuncs.com/sfaworkbench/0591954d-dfe8-4614-bee6-4adac1359958/contractimporttemplate.xlsx").getKey();
                    byte[] bytes = FileUtil.read(ossObjectInputStream);
                    byte[] base64Bytes = Base64.encodeBase64(bytes);
                    String s = new String(base64Bytes);
                    map.put(key,s);
                }
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
            }
            //Map<String,String> map=(Map<String,String>)result.getData();

            for(Map.Entry<String,String> entry:map.entrySet())
            {
                String chars=entry.getValue();
                byte[] bytes=chars.getBytes();
                byte[] base64Bytes= Base64.decodeBase64(bytes);
                FileOutputStream fos=null;
                FileInputStream fis=null;
                String filePath=null;
                try {
                    filePath="fileUpload"+System.currentTimeMillis()+"fullPathCode";
                    File dir=new File(filePath);
                    if(!dir.exists()&&!dir.isDirectory())
                    {
                        dir.mkdirs();
                    }
                    String fileName=entry.getKey().split("/")[entry.getKey().split("/").length-1];
                    String realPath=filePath+"fileName";
                    File file=new File(realPath);
                    fos=new FileOutputStream(file);
                    fos.write(base64Bytes);
                    fis=new FileInputStream(realPath);
//                    FileResponseUtils.setResponseHeader(response,fileName,base64Bytes.length);
//                    ServletOutputStream sos=response.getOutputStream();
//                    byte[] buffer=new byte[1024*8];
//                    int length=0;
//                    while ((length=fis.read(buffer))!=-1)
//                    {
//                        sos.write(buffer,0,length);
//                    }
                    FileUploadUtils.download(request,response,fis,fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        if(fos!=null)
                        {
                            fos.close();
                        }
                        if(fis!=null)
                        {
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        else
        {
            Date now=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
            String format=simpleDateFormat.format(now);
            String fileName=format+"fileNameCode"+System.currentTimeMillis()+".zip";
            String tempDir="fileUpload"+format;
            File realPath=new File(tempDir);
            if(!realPath.exists())
            {
                realPath.mkdirs();
            }
            for(DownloadFileParam downloadFileParam:params.getIdList())
            {
                //文件夹
                if(downloadFileParam.getDirFlag().equals(1))
                {
                    String folderPath=tempDir;
                    //addFiles();
                }
                //文件
                if(downloadFileParam.getDirFlag().equals(0))
                {
                    downloadFile(tempDir,downloadFileParam.getId(),userId);
                }
            }
            InputStream fis=null;
            File zipFile=null;
            try
            {
                zipFile=Zip(tempDir);
                fis=new FileInputStream(zipFile);
                FileUploadUtils.download(request,response,fis,fileName);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                IOUtils.closeQuietly(fis);
                FileUtils.deleteQuietly(zipFile);
            }
        }

    }
    private void downloadFile(String tempDire,String field,String userId)
    {
        ViewResult result=null;
        try
        {
            org.springframework.http.HttpHeaders httpHeaders=new org.springframework.http.HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity=new HttpEntity<>("",httpHeaders);
            ResponseEntity<ViewResult> responseEntity=restTemplate.postForEntity("",entity,ViewResult.class);
            if(responseEntity!=null&&responseEntity.getStatusCodeValue()== HttpStatus.OK.value())
            {
                result=responseEntity.getBody();
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        Map<String,String> map=(Map<String,String>)result.getData();
        for(Map.Entry<String,String> entry:map.entrySet())
        {
            String chars=entry.getValue();
            byte[] bytes=chars.getBytes();
            byte[] base64Bytes= Base64.decodeBase64(bytes);
            FileOutputStream fos=null;
            try {
                String filePath="fileUpload"+System.currentTimeMillis()+"fullPathCode";
                File dir=new File(filePath);
                if(!dir.exists()&&!dir.isDirectory())
                {
                    dir.mkdirs();
                }
                String fileName=entry.getKey().split("/")[entry.getKey().split("/").length-1];
                String realPath=filePath+"fileName";
                File file=new File(realPath);
                fos=new FileOutputStream(file);
                fos.write(base64Bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if(fos!=null)
                    {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private File Zip(String filePath)
    {
        File target=null;
        File source=new File(filePath);
        if(source.exists())
        {
            String zipName=source.getName()+".zip";
            target=new File(source.getParent(),zipName);
            if(target.exists())
            {
                target.delete();
            }
        }
        FileOutputStream fos=null;
        ZipOutputStream zos=null;
        try
        {
            fos=new FileOutputStream(target);
            zos=new ZipOutputStream(new BufferedOutputStream(fos));
            addRootEntry("/",source,zos);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally {
            IOUtils.closeQuietly(zos,fos);
        }
        return target;
    }
    private void addRootEntry(String base,File source,ZipOutputStream zos) throws IOException
    {
        String entry=base+source.getName();
        if(source.isDirectory())
        {
            for(File file:source.listFiles())
            {
                addEntry(entry+"/",file,zos);
            }
        }
        else
        {
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try {
                byte[] buffer=new byte[1024*10];
                fis=new FileInputStream(source);
                bis=new BufferedInputStream(fis,buffer.length);
                int read=0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read=bis.read(buffer,0,buffer.length))!=-1)
                {
                    zos.write(buffer,0,read);
                }
                zos.closeEntry();
            }
            finally {
                IOUtils.closeQuietly(bis, fis);

            }
        }
    }
    private void addEntry(String base,File source,ZipOutputStream zos) throws IOException {
        String entry=base+source.getName();
        if(source.isDirectory())
        {
            zos.putNextEntry(new ZipEntry(entry+"/"));
            zos.closeEntry();
            for(File file:source.listFiles())
            {
            addEntry(entry+"/",file,zos);
            }
        }
        else
        {
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try {
                byte[] buffer=new byte[1024*10];
                fis=new FileInputStream(source);
                bis=new BufferedInputStream(fis,buffer.length);
                int read=0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read=bis.read(buffer,0,buffer.length))!=-1)
                {
                    zos.write(buffer,0,read);
                }
                zos.closeEntry();
            }
            finally {
                IOUtils.closeQuietly(bis,fis);
            }
        }
    }
}
