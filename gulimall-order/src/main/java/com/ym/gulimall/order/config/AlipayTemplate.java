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
    private  String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzBx1k+eNEB26MgKV/lpBSQyYWw5yIw6w3PaCbgb9f3RQhA1mOBqBHdAbUIhDYKGy1Sv2Dzgh9jzxM+yaqonC4cscTpiTwb9gPzXnXEzTrisKnX+nSEv0rIj3Mp710gk9JjV6b7GkJcb1fvOUw1RdMlr2a7Uy/XlI05O4/0cIJIh+Lz6bYyBVXeHl07VojPxFIz/j5PZBCfDe2hrVjH7PUjscEocYEPOarMI2U4K2k9IGWvksKbfEeHvBE3NGL+db6foxvewO87l39ZKdKZUZjM+Qomra6npaeipec6dDfDGodLl5DoZVVjoY8LKA92vZmmbcDWobQbc5KNMoP18ZTAgMBAAECggEAImUYiaDUv+opnAbPRP75RUSvWapFNaVaPdayN44q5VEFH/6TwwyU2IV7FmBhGhZSJFZ+9EauLOlK7YNG/3tr6pPqmWTPLf5e5VJVuTNwdFxZX2y28GxDhXjzpJEJfgvXDcuCRLSbzXuzpWQWKFaYPlern8s9dnq5kn31I/+5pqU8ZYGHRPVJhyiQ/+2TouQcoHs4tYkOXhxRHeHBWHrIQRuJ/A0P8ZjoLKbYC3Q1UYTkE41cUVD9IP7JzIILxkN4x9Pv+tCw1IQqZyL7j+9Z/6Pns+UqF/gDIDpaubfhXiesH9uQQ5bAsaak7jmv+l2JmbSGVcTQiqmTxvyObq+3kQKBgQDW7WHhfPTwKClWkSGGSMtjf8/2JdTwQLZC50s6zIHIy7dNDihG2b7FY3wqIL+8tAGXSrCfIQCME5H9xAzOj6guea82+Zu+pXbPIG9ikcsB67F1UO1uRQlG0Nob14aj+T3a7PJDckyaWnsmcLp6MQHOZ5MHc0HT2+2rmr+UAwm/WQKBgQDVPXeTVQXIR2aONlpIQ8bTfSoyLxci69I2O9kwfmtRNHp0qhtRsLUH72HTlBXdaF/sV3NlJIvnaQPAUVxgwBxoaSD9sTpYE9kXnCHCzSj1ao9bbfQJ3/ANK7HYv2r19b8PiJAyA237pL08cKapeZ0oQW2GLmsoxOvYa+iBWtDJiwKBgBkDOU/vpeXJFr5/V2J0WE81EbO4xWZMcampl0S5hiUjKCrsid1s+9mm8U1J0GaaoFDGHXX3dkPXBzd8OG1VpKH8AVspg2mVghUq1iungfOwRU+84lTh7ely5t80nvO4Tu2Kg8s7oxSXkRtxJRKRX39w6FiGnlOUlb1R3hfQnrLJAoGBAMyKJDunb1931ZCN5DWzBXFxetsePlQZ3c51w9P3P1tqOhfT31aRaGRPIwf+4KY66t/Z1m9rCeOT4KxFnpWTUOx6AtI1gKBhkXhLJDsl7abSauYpZs9eBXWstvAQVDnPWlA9DwtaLNIXaAkkiUjAxZWKhNMTjWhS02MJxsDlaqYXAoGAX+rYZiKLqa51jtG+yvt/KPbeoVVoZPg35z3NlvAnSmhqJBc95oGD+PGXCLyS+K/kZGrQ1TrxeLNix3tDNADSgPC2tdLQypP/TLUeOZWfDFmSfFILbCFoVMvzZndGaddkTfLXH+7kFLHVgx3xSLABZ/JbR4TuEmG9GpA2E8vO5GM=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjTshRNJyfrGJWQ16AeVvuOxsurajjIOjZi4CdoAOLoxvS6DIbVsbU3fhRH/GgDjgkLlqhQ+kZe7otO6aHqFRav2JyHnUpFislYr2pi6AgqiQti+MZ2NaVSeP4s7hlhQc1YsUp7hAh9Hc3PhnIHn/QqGcWqYZZsk7OBFidrgUnwW/KTzfxqHuF5GSev7STcyyod/ShcdENkQxphejEGyT3nvWgYYW4/h+8aFF+1gTV4nV3wVAQbCUN3BFgs/n43YNEs9P/NCCeMznoXFTDEOnq/r7bgxvU8xprDjxoHWXy0MUcVT2cr/q8/ccT9VCDgs3D+yeLVWFerWoxTcGRvCaAwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url="http://member.gulimall.com/memberOrder.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url="http://member.gulimall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

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

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
