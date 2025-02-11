package top.yuxs.resourcelibrarysystem.config;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Log4j2
@Configuration
public class FtpConfig {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.passive-mode}")
    private boolean passiveMode;

    @Value("${ftp.encoding}")
    private String encoding;

    @Value("${ftp.connect-timeout}")
    private int connectTimeout;
//ftpUtil.setConnectTimeout(60000); // 设置连接超时时间为60秒
//ftpUtil.setDataTimeout(60000);    // 设置数据传输超时时间为60秒
    @Bean
    public FTPClient ftpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(connectTimeout);
        ftpClient.connect(host, port);
        ftpClient.login(username, password);
        ftpClient.setControlEncoding(encoding);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        log.info("we");
        if (passiveMode) {
            ftpClient.enterLocalPassiveMode();
        }
        log.info(String.valueOf(passiveMode));
        return ftpClient;
    }
}