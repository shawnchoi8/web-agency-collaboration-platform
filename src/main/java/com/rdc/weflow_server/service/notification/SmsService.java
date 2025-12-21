package com.rdc.weflow_server.service.notification;

import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final DefaultMessageService messageService;

    @Value("${solapi.from-number}")
    private String fromNumber;

    /**
     * 비동기 sms 발송
     */
    @Async
    public void sendSms(String to, String content) {
        try {
            Message message = new Message();
            message.setFrom(fromNumber);
            message.setTo(to.replaceAll("-", ""));
            message.setText(content);

            // SDK가 문자 길이에 따라 SMS/LMS 자동 결정 -> NotificationService에서 45자 제한으로 SMS로만 발송
            var response = messageService.send(message);

            log.info("SMS 발송 요청 완료: To={}, Response={}", to, response);

        } catch (Exception e) {
            log.error("SMS 발송 실패: To={}, Error={}", to, e.getMessage());
        }
    }
}
