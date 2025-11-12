package top.yuxs.resourcelibrarysystem.utils;
//
//import jakarta.annotation.Resource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//
//@Component
public class EmailApiUtil {
//    @Resource
//    private JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String from ;// 发件人
//    /**
//     * 发送纯文本的邮件
//     * @param to 收件人
//     * @param subject 主题
//     * @param content 内容
//     * @return 是否成功
//     */
//    public boolean sendGeneralEmail(String subject, String content, String to) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(from);
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(content);
//            mailSender.send(message);
//            return true; // 发送成功
//        } catch (MailException e) {
//            e.printStackTrace(); // 打印异常信息
//            return false; // 发送失败
//        }
//    }
}
