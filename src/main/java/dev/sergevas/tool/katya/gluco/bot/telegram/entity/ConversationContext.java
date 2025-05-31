package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

import java.time.OffsetDateTime;
import java.util.Objects;

public class ConversationContext {

    private Long chatId;
    private Integer messageId;
    private String commandName;
    private Boolean isPending;
    private OffsetDateTime created;
    private Boolean isDeleted;

    public ConversationContext(Long chatId, Integer messageId, String commandName, Boolean isPending,
                               OffsetDateTime created, Boolean isDeleted) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.commandName = commandName;
        this.isPending = isPending;
        this.created = created;
        this.isDeleted = isDeleted;
    }

    public Long getChatId() {
        return chatId;
    }

    public ConversationContext setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public ConversationContext setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getCommandName() {
        return commandName;
    }

    public ConversationContext setCommandName(String commandName) {
        this.commandName = commandName;
        return this;
    }

    public Boolean isPending() {
        return isPending;
    }

    public ConversationContext setPending(Boolean pending) {
        isPending = pending;
        return this;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public ConversationContext setCreated(OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public Boolean isDeleted() {
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
                && Objects.equals(commandName, that.commandName) && Objects.equals(isPending, that.isPending)
                && Objects.equals(created, that.created) && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, messageId, commandName, isPending, created, isDeleted);
    }

    @Override
    public String toString() {
        return "ConversationContext{" +
                "chatId=" + chatId +
                ", messageId='" + messageId + '\'' +
                ", commandName='" + commandName + '\'' +
                ", isPending=" + isPending +
                ", created=" + created +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
