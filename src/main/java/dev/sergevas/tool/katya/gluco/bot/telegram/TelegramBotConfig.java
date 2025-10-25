package dev.sergevas.tool.katya.gluco.bot.telegram;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.InMemoryConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBotFactory;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SchedulerControls;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.*;
import dev.sergevas.tool.katya.gluco.bot.xdrip.XdripConfig;
import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApi;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.LastReadingCacheManager;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(XdripConfig.class)
public class TelegramBotConfig {

    @Bean
    public SchedulerControls schedulerControls(@Value("${scheduler.period.accelerated}") Long periodAccelerated,
                                               @Value("${scheduler.period.default}") Long periodDefault,
                                               @Value("${scheduler.period.alert}") Long periodAlert) {
        return new SchedulerControls(periodAccelerated, periodDefault, periodAlert);
    }

    @Bean
    public InMemoryConversationContextStore inMemoryConversationContextStore() {
        return new InMemoryConversationContextStore();
    }

    @Bean
    public ReadingService readingService(
            @Value("${influxdb.db}") String db,
            @Value("${influxdb.query}") String query,
            InfluxDbServerApi influxDbServerApi,
            LastReadingCacheManager lastReadingCacheManager) {
        return new ReadingService(db, query, influxDbServerApi, lastReadingCacheManager);
    }

    @Bean
    public BotUpdateValidationRules botUpdateValidationRules(TelegramBotProperties telegramBotProperties,
                                                             InMemoryConversationContextStore inMemoryConversationContextStore) {
        return new BotUpdateValidationRules(telegramBotProperties, inMemoryConversationContextStore);
    }

    @Bean("botErrorProcessor")
    public BotUpdateProcessor botErrorProcessor(KatyaGlucoBot katyaGlucoBot,
                                                TelegramBotProperties telegramBotProperties,
                                                ConversationContextStore conversationContextStore) {
        return new BotErrorProcessor(katyaGlucoBot, telegramBotProperties, conversationContextStore);
    }

    @Bean("unknown")
    public BotUnknownCommandProcessor botUnknownCommandProcessor() {
        return new BotUnknownCommandProcessor();
    }

    @Bean("update")
    public BotUpdateCommandProcessor botUpdateCommandProcessor(KatyaGlucoBot katyaGlucoBot,
                                                               ReadingService readingService,
                                                               TelegramBotProperties telegramBotProperties) {
        return new BotUpdateCommandProcessor(katyaGlucoBot, readingService, telegramBotProperties);
    }

    @Bean("ins")
    public BotInsCommandProcessor botInsCommandProcessor(KatyaGlucoBot katyaGlucoBot,
                                                         RecommendationMessagesConfig recommendationMessagesConfig,
                                                         ConversationContextStore conversationContextStore) {
        return new BotInsCommandProcessor(katyaGlucoBot, recommendationMessagesConfig, conversationContextStore);
    }

    @Bean("recommendationRequest")
    public BotRecommendationRequestMessageProcessor botRecommendationRequestMessageProcessor(KatyaGlucoBot katyaGlucoBot,
                                                                                             RecommendationProvider recommendationProvider,
                                                                                             RecommendationMessagesConfig recommendationMessagesConfig,
                                                                                             ConversationContextStore conversationContextStore) {
        return new BotRecommendationRequestMessageProcessor(katyaGlucoBot, recommendationProvider, recommendationMessagesConfig, conversationContextStore);
    }

    @Bean
    public BotUpdateDispatchProcessor botUpdateDispatchProcessor(
            ConversationContextStore conversationContextStore,
            BotUpdateValidationRules botUpdateValidationRules,
            @Qualifier("update") BotUpdateProcessor botUpdateCommandProcessor,
            @Qualifier("ins") BotUpdateProcessor botInsCommandProcessor,
            @Qualifier("unknown") BotUpdateProcessor botUnknownCommandProcessor,
            @Qualifier("botErrorProcessor") BotUpdateProcessor botErrorProcessor,
            @Qualifier("recommendationRequest") BotUpdateProcessor botRecommendationRequestMessageProcessor
    ) {
        return new BotUpdateDispatchProcessor(conversationContextStore,
                botUpdateValidationRules,
                botUpdateCommandProcessor,
                botInsCommandProcessor,
                botUnknownCommandProcessor,
                botErrorProcessor,
                botRecommendationRequestMessageProcessor);
    }


    @Bean
    public KatyaGlucoBotFactory katyaGlucoBotFactory(TelegramBotProperties telegramBotProperties,
                                                     BotUpdateDispatchProcessor botCommandDispatchProcessor) {
        return new KatyaGlucoBotFactory(telegramBotProperties, botCommandDispatchProcessor);
    }

    @Bean
    KatyaGlucoBot katyaGlucoBot(KatyaGlucoBotFactory katyaGlucoBotFactory) {
        return katyaGlucoBotFactory.getObject();
    }
}
