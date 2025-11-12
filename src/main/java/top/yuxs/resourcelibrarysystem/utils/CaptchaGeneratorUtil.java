package top.yuxs.resourcelibrarysystem.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CaptchaGeneratorUtil {
    /**
     * 生成随机验证码
     * @return 随机验证码字符串
     */
    public static String generateCaptcha() {
        StringBuilder captcha = new StringBuilder();                // 在循环外定义String类型的变量用来连接生成的随机字符
        Random random = new Random();
        for (int i = 0; i < 5; i++) {                               // 定义一个for循环，循环5次
        int type = random.nextInt(3);                        // 随机生成0|1|2的数据
            switch (type) {                                         // 把0、1、2交给switch生成对应类型的随机字符
                case 0:                                             // 生成数字
                    int digit = random.nextInt(10);
                    captcha.append(digit);
                    break;
                case 1:                                             // 生成大写字母
                    char uppercase = (char) (random.nextInt(26) + 'A');
                    captcha.append(uppercase);
                    break;
                case 2:                                             // 生成小写字母
                    char lowercase = (char) (random.nextInt(26) + 'a');
                    captcha.append(lowercase);
                    break;
            }
        }
        return captcha.toString();                                  // 循环结束后，返回String类型的变量即是生成的随机验证码
    }
}
