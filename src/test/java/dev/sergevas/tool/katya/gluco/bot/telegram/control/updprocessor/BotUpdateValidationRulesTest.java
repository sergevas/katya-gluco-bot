package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@QuarkusTest
class BotUpdateValidationRulesTest {

    @InjectMock
    ConversationContextStore conversationContextStore;

    @Inject
    BotUpdateValidationRules botUpdateValidationRules;

    @Test
    void givenPendingCommand_whenCommandNamesEqual_thenShouldReturnTrue() {
        when(conversationContextStore.getLast(eq(123456789L)))
                .thenReturn(Optional.of(new ConversationContext(123456789L,
                        12345,
                        BotCommand.INS.getCommand(),
                        Boolean.TRUE,
                        OffsetDateTime.now(),
                        Boolean.FALSE)));
        assertTrue(botUpdateValidationRules.isCommandPending(123456789L));
    }

    @Test
    void givenPendingCommand_whenCommandNamesEqualButIsDeleted_thenShouldReturnFalse() {
        when(conversationContextStore.getLast(eq(12345679L)))
                .thenReturn(Optional.of(new ConversationContext(123456789L,
                        12345,
                        BotCommand.INS.getCommand(),
                        Boolean.TRUE,
                        OffsetDateTime.now(),
                        Boolean.TRUE)));
        assertFalse(botUpdateValidationRules.isCommandPending(123456789L));
    }
}
