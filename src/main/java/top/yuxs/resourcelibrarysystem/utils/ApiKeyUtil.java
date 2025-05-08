package top.yuxs.resourcelibrarysystem.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Log4j2
@Component
public class ApiKeyUtil {

    // 定义日期格式
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 计算30天后的过期时间。
     *
     * @param creationTime 创建时间，格式为yyyy-MM-dd HH:mm:ss
     * @return 30天后的过期时间，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String calculateExpirationTime(String creationTime) {
        // 将字符串解析为 LocalDateTime
        LocalDateTime creationDateTime = LocalDateTime.parse(creationTime, formatter);

        // 计算30天后的过期时间
        LocalDateTime expirationDateTime = creationDateTime.plusDays(30);

        // 格式化为字符串返回
        return expirationDateTime.format(formatter);
    }

    /**
     * 检查给定的创建时间和过期时间是否已经过期。
     *
     * @param creationTime 创建时间，格式为yyyy-MM-dd HH:mm:ss
     * @param expirationTime 过期时间，格式为yyyy-MM-dd HH:mm:ss
     * @return 如果已经过期，返回true；否则，返回false
     */
    public static boolean isExpired(String creationTime, String expirationTime) {
        // 将字符串解析为 LocalDateTime
        LocalDateTime creationDateTime = LocalDateTime.parse(creationTime, formatter);
        LocalDateTime expirationDateTime = LocalDateTime.parse(expirationTime, formatter);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 比较当前时间是否超过过期时间
        return now.isAfter(expirationDateTime);
    }


//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 检查给定的创建时间和过期时间是否已经过期。
     *
     * @param creationTime 创建时间，支持 String、Date、Long（时间戳毫秒）或 LocalDateTime 类型
     * @param expirationTime 过期时间，类型同上
     * @return 如果当前时间超过过期时间，返回 true；否则返回 false
     * @throws IllegalArgumentException 如果参数类型不支持
     */
    public static boolean isExpired(Object creationTime, Object expirationTime) {
        LocalDateTime creationDateTime = ToLocalDateTime(creationTime);
        LocalDateTime expirationDateTime = ToLocalDateTime(expirationTime);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationDateTime);
    }

    private static LocalDateTime ToLocalDateTime(Object dateTime) {
        if (dateTime instanceof String) {
            return LocalDateTime.parse((String) dateTime, formatter);
        } else if (dateTime instanceof Date) {
            return ((Date) dateTime).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } else if (dateTime instanceof Long) {
            return Instant.ofEpochMilli((Long) dateTime)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } else if (dateTime instanceof LocalDateTime) {
            return (LocalDateTime) dateTime;
        } else {
            throw new IllegalArgumentException("不支持的参数类型: " + dateTime.getClass());
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        try {
            String creationTime = "2025-02-01 12:00:00";
            String expirationTime = calculateExpirationTime(creationTime);
            System.out.println("过期时间: " + expirationTime);

            boolean isExpired = isExpired(creationTime, expirationTime);
            System.out.println("是否过期: " + isExpired);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}