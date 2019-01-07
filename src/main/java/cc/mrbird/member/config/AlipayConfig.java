package cc.mrbird.member.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class AlipayConfig {

/**
 * 测试环境
  */

//    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//    public static String app_id = "2016092000553510";
//
//    // 商户私钥，您的PKCS8格式RSA2私钥
////    public static String merchant_private_key = "AszmHwck5i6dCHapJMMOLQ==";
//    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVea3L3Ubxey39m8PoCQdilNCTwhB11eWkYHqGUdQOgpA/ObouzsGFwVEbNZjaoEaX/GJn2N1Amenmgs5KF3UC1wMOS2HhYuq6DwLKC3eeavPz7em5GMJWG6aXex2hd/OS5wuM+9sbxngwRyTNrWFDCK/q5F9MK7BM50fIpgpi/0ysqIdjcT1H7BeZJ1jgf/2XLfwC7W5t9yax9/ZUhoaYtFfAsd2qoiSxr1fuQiHLo/sZbBIOowsIeea8V1SfmSmo+Fa2JX+yTUdsl909OTsNO9ovYFr9neDNrziJfSBZKuK+ByQ4lMkGQ/pqA8eAATI7d1Y/9INwAHCqF5OvmxZFAgMBAAECggEANSmiy6g2zDpAYDvfUI6thr1g9byG+DIMTAtYaJ9/6W08epBAjNdScw0PmLrWU6O/l9zW7xJtlsVnMGoDjsyK/GiWmKXs+SmiTGx8VHoBoGFvxKkwgHmy3MdW1/ec0UoFYpE0RX9qLZXIN3uGNnTc6+eSEdwJnODJAoEbwdx0Q0L10PCd74uNWDDnSKONhMyhpsBQvG88SDWAQFRBuyb8SVcoDYoIO3D9/JFb2tdmGKPj5ycU7YkOvJKjAwPuL7QY4SnDpx4RoDds9+hBoSFlTCMy0JQJ0NGS8/6ep2qy+ngkelUCz+dFzZPND0liTmv+oxA1++AvcJEqkvPhGOVpnQKBgQDwnnXh7O29Q0SQyomIWLYFJx455v3vClozS3ZifYgRQpLNVUnqrRiuiYJfBieXm9HWh0e6sMXe53vSo1ZUeuCeHSYwPOyu8JCXL0zzVV+uOPB93Qm0C0oVXlTyBQdD+IOiblXZ8QeI7nccheL0sWx52s7Em537k1QhOzfCXIDhGwKBgQCfB7livXrNhvaszUoRgFR1q90cXoOa2jg4wlm1f3PZfFuJd6ap+JH6xOL52VcDIT8p+VZYb6rYh/8xPTRBZZJZ6WNS9JtQETVpBdOcmpiPSJj/yEY3qJseuRA+Y8KuiHymMbyiRnl5MdKz4XGKh96tpYt2uNG45G5rkeeanxC8HwKBgFJHPTQWGm1fdTC1sTXIb+VvLOzLygVVWuazZTXkiwSr6RsBkRcIPaphJw1rQ1qaU7Sx6ZszG1ZPfY3XgIu+wNQQT6CrFJv1ctvftsZ2aRNgMQw46zp/qcOI0C85/PQHJKwEYqL1wofWUzCTr7nQebFpWs7sD6eZB2mdoEF79C4FAoGAHFycS2fPhugTBi52jzh716CXBoA0qGFStYyzwxK/7oGnMZUNPDMAleCXOkfwpLZBNZMqOwtvRL68XGO19hPPTgm5lQnaySTIibpoSv2nR6aC4tss6rA/OfN3Wikgh1AAKwOjyK0Y7Oxdz4bdYP9qTz2+6aV8gaT7rGOREppbubECgYEAiFnRSSN4qb6AhiudH7gadtEkQr39KMB9zqAm2UpUqvFIryw2Ql9ovK9EZZRnyCNyJiTk/hUeFFOvL7FJHcTk8EQP1HzLqbumJRkhlUssJq+P9uhBmo9+unk47Vz4Z525GlEvzyLmln3G/Jdjtu4wn9cYgpKo/otGQfety92ocN4=";
//    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArjTbrbnU9xKIPOevR10QqneM4nz7ytn+eZK0hL6FpDgSTTxJive8cKNgaBodo/ZzIRW3pxi/qWwL37z0pVAPKwF67w//91i2MgsV/SyXveSKADmd0GYEOcm6g0WHR4jhCumXC0zdppNq65wWy2phTniMKXXgkLEzDDcf1Sd2P22WPzPMHVILTW0ELPfzN4puQWOdhLfkgyZHPZ80F04m0a6QRm0kknR39NbLcZRv+pUX4HuIMivIjliDo2Unq4dy4vw/xK35RfPJKVCfZ7ArXZGIVlBHZt8MSWjeST3aFg6pM/pxbKzvjOv7a4Y4gTSUa6RshrdR/N4RIE52CZh6PwIDAQAB";
//
//    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String notify_url = "http://qiweb.shangyixx.com/aliPay/notifyUrl";
//
//    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = "http://qiweb.shangyixx.com/aliPay/returnUrl";
//
//    // 签名方式
//    public static String sign_type = "RSA2";
//
//    // 字符编码格式
//    public static String charset = "utf-8";
//
//    // 支付宝网关
//    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
//
//    // 支付宝网关
//    public static String log_path = "d:\\";


    /**
     * 正式环境
      */
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2018110962105577";

    // 商户私钥，您的PKCS8格式RSA2私钥
//    public static String merchant_private_key = "AszmHwck5i6dCHapJMMOLQ==";
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDoEVu/2WPvxtM4eX2hIoFocfWXoqaeeTiLHHN6vXj9OH+9UY9HCo1eQoV8A8mJwIJj2uwffQfy6D0ohSacQKzdRu7sKznKOA3MeEXb9WBYty77U546I3W7TBSbzQohed0kJVUEPcA4JwHHFiR7sdU3oU3HdXcyBx2M5erTZ/eh/L2qYGGVz7WemOxl5Y0sYnVxhrmOoSKRY6iaTwerotdtD9iinQd7ZE5Njvmw60CNImQcCA8eHpBTbPn8DaG9pOsjS+zca6uYsLRbNeZ00bvMRlcURFe8gzeSPaCu2ixUro0+Vkc/OTSj84eJfcFbwCxM13CvumQ7mwNcidmoB8DAgMBAAECggEAOEtKht9qE2F7jpUqohUrZp87dZ53jvvE/Upe3d+4jGcGZOGHyckK8FW1D47dG8chIu+Y8iHcT+DqFNZhE3Bz5EaYttwoBrE/u7t0dm6ZFL4wg5tJrYJgb2R//zGaUB580CiqBQtAaLqnYI8p/jfuPojw1pLHDab0LUWBf30ye5xvn9ilZDY1W7dTqSYlQ6Sb2m/RA0oZogJqXl8j3auEG6/NhA0YUhJN9M3SQLif5mcyRuwhR4RxQCd9znOjj3vyxV5ChHrTSzK6QEtwcXkvrVI/eykaUy5XwOAASuV1XYdVX5S9DfSnxj6G/NZiQMABdK3IECKGL++tjK25SyxmeQKBgQDb+c/am9QK+WnrtjzUpKOJV+cQ8aqjcD27/hK/y6kIGSeA7KyoBGegFyDnaUTWA4h/JOKhY2+4wehsI1nAquq78a80Hdcflq+hFgxZCmdowKVwFN2PXx5eLeGxewltjG6Udv+LhZLcepQ8MSi7N5f6mjVsDsPRAyn4GrJvQ6/ipQKBgQCZLoPgy7M4hbJ23PzcarWat8MBH1LVFxuAeICD7yTzsrMJ2UvOPpleUBJvKfZomWQSjDpxlIbx6t7kUvIo2tFXSLoNkgeKbNmm/0SvgcUzReZ8TXJOkQqz20l06tqvqnOdoLrJNdE+ETbhVAKxzmwPiWBgi/ZJ7xET45gW+DAShwKBgEmqV9QSmSjvq2v3RFnFpMpquIOxGn7PKK1bAXZfQoTmv5bqK0u85eLzu94jH503468IcCixvwk1TptgHz9pPWChKBYg5Bi9AqXEV3A+DrFCymRFaIkJxoatgMZJuLeJ7sIjM6LKoYraNegOYmCH1opit/8u1p6ZG6XtctHV7gHNAoGAQ2DVe1y+YX9Vh7ketGlvpjVsXiMEEV0PqMvYY7AeRd91p0BgesDIP1bjNOh1TX8jKJFr56fYT7LrBe2CPPw7FrSTugJgtL+6YR1pffWL40i84DJEtFBSL9imD/p55MkJ/+zCHF1Cw5VY2XZVlRZiK9dWgEEwPUcd8OysanYS1yMCgYEAjytU99cWdT36xGvtPX+quV40cFB3guHT8ajROUyi7/ZDThAhf240xICbxxMc7EPbgAPq3I6EeCwoMGA9AUgsK7tT/BAeOJaoJhSCUAI/kHWB7NwwADeumhbg04a5ryez1NdoPnHKYNT+sQZjqHtl/dpHdLS2zFl06JgBiYX6eDQ=" ;
             // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoE2pZJQTNCjOa/y5YXeP2rNT3o0CZDYyWOgy2bY3S6FMv8g9CRw1DdhiiyoU2496mp49hPkVjq2q0xstS7/efbkNsEEJOCe5TtOLu5Vq29aD87XIdaNYiRcQLtDoO6WpQaEXTw8/sQK70+Nti39bXHXwndC2uRypQP1PbdDlmW9Mk4Kd70crR5OTQY8wNcweLx7O5GOsViC1IVi/6V5FpK8CaH3O+1mDfnK1TlwuH0ZZbvTc9QBq4v/qr3+LG1Z/+jaxksacZmp3M4CMRdxGdQPWpa9oMJ54UdzWVMCDnyIc+C9O9H4hgAV/lfzltQrOHDf5vljaxeAiqKjsUOrVTQIDAQAB";
            //"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCU4GHTJt4zh+pXDYtQOoTQkJ0HXm/mjimDnp7Gt8b/DHQg+e2R2cPTYazuC6wsy8J9LDjRd8L8hVDWKzuPU+LUGW+3tntWnsWE0ymXegHxy8iXeRErCiZoTQpijpI87KoC+Y+rDLxJ+zf0hHlH6N507dmXMVtji1+ptj30tyUihpxBtMi8rERCbZB/JHNwoTHUek+HjwQqxDFj8+bgK2pR0UMhT8wPmtqZdeD1wpQMja9U6HeV/5O7GDotaMJlKuyUCr7l4A5uUwEL2l/hmEUe/JIL96vZF06NAGVOdK0ktQXwKCMm8kBoBI6N+mOadQ1zeY8Yy9thtZmlh+oGLCDnAgMBAAECggEAPp66xwP8RpSOvxsVVCOpJckOI8FXNBTr5U7c3h0C+NiWKCC4CrccJVdaU65ZN2ZlYh0tsP286FZzEAKTRNA2ApzIXu/EpUSXNC1Vuz84fioHL59e10mnqgttqNLrM/Ef+axa/8mVkVC30oO+N779gxVghrXeCNrg3BOpr7sxRXnQkUtzyEYC0ZSKtQ16oanbtkxe2k71trKfH41c36WbjNLUIyKtUY0o7y6Zm7ojINi0fGPYb7e24QVky5C8d6O0htRwU7KnUCBBqMIIZuu5Re+HbsFYRNVqW3IupeNMxMjsLz8vG7Q6hfq0QJKL5xPc56pkzd6T7wY4hDtBo053QQKBgQDThiobdzaxBE/HcyuMLC1DLg+Y9zIxmHfIKr5Bos/b6bKprlRV+0x87tyR7Obgz7gWPysLYnZa7/IHuNOFcLGYDBSxgsbv7rcDS9e3afPJFFOBMOhc7XKTJpcakB4dpE3zR7riUSKM4nNarFIY4M5SpZMD6cQUIYOtXkn83IJ5EQKBgQC0Lgw8frR+Dc/1kkt8DMiA7y/9xNDxzADpNmtDdXbK6l9pkRUgMV6OvBS5FjvKwuGyEye2M95zWWulP/yjFnZIV4MgbHy9EprXdd4Oq0amHTB96tJSVhw9+6ntX5N500yruK898rMPA23a3WpJ7hY1ab1X00ORjuPTQWSw1Jg6dwKBgQC/KiiR6/q8nnXvfnGj3CUH6u/jwbxWQEI44BBUR21thVaXrVrA3j1xR3qGU0ERegkX6fzf7Buk8eed/PcFSSTnkjt9dgCOxQIXc4rAU3wkhT82qdnGaj47KQw00Hy3M0/Th0cp1EPSabHxB32myD0PSl6EkddIjkVwAKADa9Jq0QKBgHndYz1PXXickvk/V3qxkrNSE52KDuBb4Inb2rSnsA8ScjIOhMFfYtsbbtC4rvw2zzwIJKXhcyWFZ38/RfpwaNQJDJFTjxK6kKwF9ojpY0FbIL8podM6lqN9obMBH9gYCBVlNnGpw132LckIVKloNPEvcXcDVwIOKR1rb1UClWuFAoGAKR4+wBEI7DaUexOcGeczMl+3qkBE0N3gthLpF6JGHsEb//myHf22qHtdvG5EEArBi4L7gMsebd8klrGusV5rGVYjzYvc0UGobwK+ZoRo9sr7XudYvlebGIrV3WhbYVsY8A4dokLNLftm/g4gOv8PAqQ+5Kq163sq86xZfIPFkiI=";
            //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoE2pZJQTNCjOa/y5YXeP2rNT3o0CZDYyWOgy2bY3S6FMv8g9CRw1DdhiiyoU2496mp49hPkVjq2q0xstS7/efbkNsEEJOCe5TtOLu5Vq29aD87XIdaNYiRcQLtDoO6WpQaEXTw8/sQK70+Nti39bXHXwndC2uRypQP1PbdDlmW9Mk4Kd70crR5OTQY8wNcweLx7O5GOsViC1IVi/6V5FpK8CaH3O+1mDfnK1TlwuH0ZZbvTc9QBq4v/qr3+LG1Z/+jaxksacZmp3M4CMRdxGdQPWpa9oMJ54UdzWVMCDnyIc+C9O9H4hgAV/lfzltQrOHDf5vljaxeAiqKjsUOrVTQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://qiweb.shangyixx.com/aliPay/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://qiweb.shangyixx.com/aliPay/returnUrl";//

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String log_path = "d:\\";

    //


    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    @Bean
    AlipayClient alipayClient() {
        return new DefaultAlipayClient
                (gatewayUrl, app_id, merchant_private_key, "json", "utf-8", alipay_public_key, sign_type);
    }


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

