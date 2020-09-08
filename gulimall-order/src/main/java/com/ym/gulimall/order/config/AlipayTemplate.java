package com.ym.gulimall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.ym.gulimall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private String app_id = "2021000116669062";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDKwXc8AipySUNMMbpIe3LunCu57zI+euibolB8ynNm6VKfO89qI8oIjE7yE+kCQT3t0b8PSxDQ05IUg+iDrEkPbjrUmn6IRXAuKTECEWBlqR26EzvBKUZhBBqvHo+xRocS5GN2WTtV88abN2xf6QAwHKRwk5gtvrqO5ldH5aGq3v+mV21fB/pVTnjFEX0vzo4meIUeGiB/PnvTnQjrag3mmrS8uMR0JFEJeTzpB4iZVjZ9hSU+8i//4EW2ZCb61+8WPINmPiwJWg4A6CJWHXD7xICBJ0mM73uXiaMsaHMrJ6S/Tyr4T5zm+NaUHUlNrw27clAINVm2KsIYBhdiyiy3AgMBAAECggEAC+hq4UpbRdUJr8rn3uPpLxxa8ROcRtEfIizZP0sAlhWz9YgkuV4aun/LDyqeL7S3N2VvLC3DchBK7wuEN1+B9vcJW30rW0OAwCVJLI7qs23IQippAwIykPYVvx0zYTz8J97j/IpnErg7fNg3dDaR/UFhYv84G8+s00Gx9JYG9asCYVbR3zTP4yz40EVbT7+mxnASbGXzQPFKYY2vll23skjufsiJ6AvXu4I6Wx6nPhVSFdhO2bVMSjcZlxHXIxXRE4r8HWaOQ3IKulEDRmIw9teawaBCnS3DflD4Mih9fYg9bYcuUdMG72K506D6vx8jSX8KAsT1VxBE5E2G4I22OQKBgQDqHGEVRTWvK6JN+hUuEhTgtw5/XCGKfYqmJb3EUXSxGL9HizCC9MRKNLb5dzEj4feSHjfg2e8NYCpbjsFRirrpvi/lHwIpuDSt9mx5PI15+lWtii4J7z2+mY7M2RJ/2NSqilSxid9p72POZK7wNo4ru/QmenIO70985OaoBNI7PQKBgQDdtpPk5wc63lM+MqohJnDQNuPhJT8tU2h02ZC1mYoCjIB0VD+ljrBz87BhjQouZP3sl4s1fr32010Rt6t3ZYqNqsjd1kEnGOyQPMhO4oblccxdbMNb2pfSIyDp8A5XfD5tnluGEW7ihzOUXc1M6YJ+UpwO9lHiSXF0YmJVORUXAwKBgQDo2ntRrboc1likGIcygLeVX14jHV8bIY1jfoyGlMJtV4EBxxbkWlJWwKHx9TTCTQJyqm3HztxvwGPBF3d/BG6vCQUYbsQ2/Mog8caou6q4VV8csiDirrwKNTAM3igBU4kLa3Wb9NdrxbjO0+QQdyLnqxXHo+iIpEy6m+4z1j/IvQKBgDbV3lRB857SuAHtI/jY+hP1T30y6iI6u94SpWDU3bZtq6OZ5rf6x5eWEOy/pmH9JnRqssqDU1aFxkIIjEcWfSM2t0FuqWSTHIc8EUq5aiW7dJEywkqokN4vRAe4EMQAfaw8+2P4UdWDHPW4EXvRUS4PHnvw3iW6hm4Dm1B6ssWjAoGAJxpa8zoBCPMauIKxTDCLr145FJ3DdSEkRrWZ6GDVLqA4RlglBB5QVLCp1F1PVZ/CZg96yOGxmcQQjpBu9UiRkY9w1t8AAv0XKIIakT/jLnibY9Tfs3chm3pSYK72N+y+5Scb0nAuWIt125TnFInStnLfdSbXOLqBlVX9CPfSsUA=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjTshRNJyfrGJWQ16AeVvuOxsurajjIOjZi4CdoAOLoxvS6DIbVsbU3fhRH/GgDjgkLlqhQ+kZe7otO6aHqFRav2JyHnUpFislYr2pi6AgqiQti+MZ2NaVSeP4s7hlhQc1YsUp7hAh9Hc3PhnIHn/QqGcWqYZZsk7OBFidrgUnwW/KTzfxqHuF5GSev7STcyyod/ShcdENkQxphejEGyT3nvWgYYW4/h+8aFF+1gTV4nV3wVAQbCUN3BFgs/n43YNEs9P/NCCeMznoXFTDEOnq/r7bgxvU8xprDjxoHWXy0MUcVT2cr/q8/ccT9VCDgs3D+yeLVWFerWoxTcGRvCaAwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private String notify_url = "http://1dcqcs1tkv.52http.net/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private String return_url = "http://member.gulimall.com/memberOrder.html";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    private String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应：" + result);

        return result;

    }
}
