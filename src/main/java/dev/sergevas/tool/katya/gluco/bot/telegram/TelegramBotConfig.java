package dev.sergevas.tool.katya.gluco.bot.telegram;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationConfig;
import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.InMemoryConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBotFactory;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.*;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.*;
import dev.sergevas.tool.katya.gluco.bot.xdrip.XdripConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({
        XdripConfig.class,
        RecommendationConfig.class
})
@EnableScheduling
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
    public LastReadingCacheManager lastReadingCacheManager() {
        return new LastReadingCacheManager();
    }

    @Bean
    public ReadingService readingService(SensorDataReader sensorDataReader,
                                         LastReadingCacheManager lastReadingCacheManager) {
        return new ReadingService(sensorDataReader, lastReadingCacheManager);
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
                                                         RecommendationMessagesProperties recommendationMessagesProperties,
                                                         ConversationContextStore conversationContextStore) {
        return new BotInsCommandProcessor(katyaGlucoBot, recommendationMessagesProperties, conversationContextStore);
    }

    @Bean("recommendationRequest")
    public BotRecommendationRequestMessageProcessor botRecommendationRequestMessageProcessor(KatyaGlucoBot katyaGlucoBot,
                                                                                             RecommendationProvider recommendationProvider,
                                                                                             RecommendationMessagesProperties recommendationMessagesProperties,
                                                                                             ConversationContextStore conversationContextStore) {
        return new BotRecommendationRequestMessageProcessor(katyaGlucoBot, recommendationProvider, recommendationMessagesProperties, conversationContextStore);
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
    public KatyaGlucoBotFactory katyaGlucoBotFactory(TelegramBotProperties telegramBotProperties) {
        return new KatyaGlucoBotFactory(telegramBotProperties);
    }

    @Bean
    KatyaGlucoBot katyaGlucoBot(KatyaGlucoBotFactory katyaGlucoBotFactory) {
        return katyaGlucoBotFactory.getObject();
    }

    @Bean
    public SchedulerService schedulerService(KatyaGlucoBot katyaGlucoBot,
                                             ReadingService readingService,
                                             SchedulerControls schedulerControls,
                                             @Value("${scheduler.period.accelerated}") Long periodAccelerated,
                                             @Value("${scheduler.period.default}") Long periodDefault,
                                             @Value("${scheduler.period.alert}") Long periodAlert) {
        return new SchedulerService(katyaGlucoBot, readingService, schedulerControls, periodAccelerated,
                periodDefault, periodAlert);
    }
}
