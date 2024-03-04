package com.project.findjob.dto;

import com.project.findjob.model.Chat;
import com.project.findjob.model.Notification;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class NotificationResponse {
    private String receiver;
    private String notificationType;
    private Chat chat;
    private Timestamp time;
    private String url;
    private boolean isRead;

    public static NotificationResponse from(Notification notification) {


        return
                NotificationResponse.builder()
                        .receiver(notification.getReceiver())
                        .notificationType(notification.getNotificationType())
                        .chat(notification.getChat())
                        .url(notification.getUrl())
                        .time(notification.getTime())
                        .isRead(notification.getIsRead())
                        .build();

    }
}
