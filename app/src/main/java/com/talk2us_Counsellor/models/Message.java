package com.talk2us_Counsellor.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class Message {
    @PrimaryKey
    @NonNull
    public String word;
    public String timeStamp;
    public Boolean sent;
    public Boolean seen;
    public String sentFrom;
    public String messageId;

    public Message(String word, String timeStamp, Boolean sent, Boolean seen, String sentFrom, String messageId) {
        this.word = word;
        this.timeStamp = timeStamp;
        this.sent = sent;
        this.seen = seen;
        this.sentFrom = sentFrom;
        this.messageId = messageId;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Message() {
        word = "Hello";
        timeStamp = "right now";
        sent = false;
        seen = false;
        sentFrom = "Counsellor";
        messageId = "not_defined";
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
