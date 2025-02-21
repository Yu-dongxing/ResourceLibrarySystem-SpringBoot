package top.yuxs.resourcelibrarysystem.config;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
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

    @Value("${ftp.data-timeout}")
    private int dataTimeout;

    @Value("${ftp.buffer-size}")
    private int bufferSize; // Add buffer size configuration

    @Bean
    public FTPClient ftpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(connectTimeout);
        try {
            ftpClient.connect(host, port);

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTP server refused connection.");
                throw new IOException("FTP server refused connection.");
            }

            if (!ftpClient.login(username, password)) {
                ftpClient.disconnect();
                log.error("FTP server authentication failed.");
                throw new IOException("FTP server authentication failed.");
            }

            ftpClient.setControlEncoding(encoding);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setDataTimeout(dataTimeout);
            ftpClient.setBufferSize(bufferSize);  // Set buffer size for faster transfers

            if (passiveMode) {
                ftpClient.enterLocalPassiveMode();
                log.info("FTP Client using Passive Mode");
            } else {
                log.info("FTP Client using Active Mode");
            }
            log.info("FTP Client connected to {}:{}", host, port);

        } catch (IOException e) {
            log.error("Error connecting to FTP server: {}", e.getMessage());
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException f) {
                    log.error("Error disconnecting from FTP server: {}", f.getMessage());
                }
            }
            throw e;
        }
        return ftpClient;
    }
}