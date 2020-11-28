package com.task.webservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long receiverId;
    private Long senderId;
    private String text;
    private String subject;
    private String reply;
    private Status senderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
        this.senderStatus = Status.UNREAD;
    }

    public Status getStatus() {
        return senderStatus;
    }

    public void setStatus(Status status) {
        this.senderStatus = status;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public boolean isReplied() {
        return reply != null;
    }

    public enum Status {
        SENT, READ, UNREAD
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(receiverId, message.receiverId) &&
                Objects.equals(senderId, message.senderId) &&
                Objects.equals(text, message.text) &&
                Objects.equals(subject, message.subject) &&
                Objects.equals(reply, message.reply) &&
                senderStatus == message.senderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiverId, senderId, text, subject, reply, senderStatus);
    }
}
