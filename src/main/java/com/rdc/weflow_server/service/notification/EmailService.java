package com.rdc.weflow_server.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    /**
     * 비동기 이메일 발송
     * @param to 수신자 이메일
     * @param subject 메일 제목
     * @param text 메일 본문
     */
    @Async
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("[WeFlow] " + subject); // 제목 앞에 [WeFlow] 말머리 자동 추가
            message.setText(text);

            javaMailSender.send(message);
            log.info("이메일 발송 성공: {}", to);
        } catch (Exception e) {
            // 이메일 발송 실패 시 로그만 남기고, 비즈니스 로직(예: 승인 요청)은 정상 진행되도록 함
            log.error("이메일 발송 실패: {}", to, e);
        }
    }
}