package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

import java.time.OffsetDateTime;
import java.util.Objects;

public class ConversationContext {

    private String chatId;
    private String messageId;
    private String messageText;
    private Boolean isCommand;
    private OffsetDateTime created;
    private Boolean isDeleted;

    public ConversationContext(String chatId, String messageId, String messageText, Boolean isCommand,
                               OffsetDateTime created, Boolean isDeleted) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.messageText = messageText;
        this.isCommand = isCommand;
        this.created = created;
        this.isDeleted = isDeleted;
    }

    public String getChatId() {
        return chatId;
    }

    public ConversationContext setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public ConversationContext setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public ConversationContext setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public Boolean getCommand() {
        return isCommand;
    }

    public ConversationContext setCommand(Boolean command) {
        isCommand = command;
        return this;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public ConversationContext setCreated(OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public ConversationContext setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConversationContext that = (ConversationContext) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(messageId, that.messageId)
                && Objects.equals(messageText, that.messageText)
                && Objects.equals(isCommand, that.isCommand) && Objects.equals(created, that.created)
                && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, messageId, messageText, isCommand, created, isDeleted);
    }

    @Override
    public String toString() {
        return "ConversationContext{" +
                "chatId='" + chatId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", messageText='" + messageText + '\'' +
                ", isCommand=" + isCommand +
                ", created=" + created +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
