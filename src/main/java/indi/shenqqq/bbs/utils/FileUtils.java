package indi.shenqqq.bbs.utils;

import indi.shenqqq.bbs.service.ISystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author Shen Qi
 * @Date 2022/3/8 11:12
 * @Description XX
 */
@Component
public class FileUtils {

    @Resource
    ISystemConfigService systemConfigService;

    private final Logger log = LoggerFactory.getLogger(FileUtils.class);
    private static String static_url;
    private static String url_pre;

    private void init() {
        static_url = systemConfigService.selectByKey("file_upload_path");
        url_pre = systemConfigService.selectByKey("file_upload_address");
    }

    public static String getFileMD5(InputStream in) {
        byte[] buffer = new byte[1024];
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            while (true) {
                int len;
                if ((len = in.read(buffer, 0, 1024)) == -1) {
                    in.close();
                    break;
                }

                digest.update(buffer, 0, len);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }

        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 上传文件
     *
     * @param file       要上传的文件对象
     * @param fileName   文件名，可以为空，为空的话，就生成一串uuid代替文件名
     * @param customPath 自定义存放路径，这个地址是跟在数据库里配置的路径后面的，
     *                   格式类似 avatar/admin 前后没有 / 前面表示头像，后面是用户的昵称，举例，如果将用户头像全都放在一个文件夹里，这里可以直接传个 avatar
     * @return
     */
    public String upload(MultipartFile file, String fileName, String customPath) {
        init();
        try {
            if (file == null || file.isEmpty()) return null;

            if (org.springframework.util.StringUtils.isEmpty(fileName)) fileName = UUID.randomUUID().toString();
            String suffix = "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
            // 如果存放目录不存在，则创建
            File savePath = new File(static_url + customPath);
            if (!savePath.exists()) savePath.mkdirs();

            // 给上传的路径拼上文件名与后缀
            String localPath = static_url + customPath + "/" + fileName + suffix;
            File file1 = new File(localPath);
            if (file1.exists()) {
                file1.delete();
            }
            System.out.println(static_url);
            System.out.println(file);
            System.out.println(file1);
            file.transferTo(file1);

            // 上传成功后返回访问路径
            return url_pre + customPath + "/" + fileName +
                    suffix + "?v=" + StringUtils.randomNumber(6);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
