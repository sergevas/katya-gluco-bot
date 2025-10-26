package dev.sergevas.tool.katya.gluco.bot.telegram;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationConfig;
import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.InMemoryConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBotFactory;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SchedulerControls;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotErrorProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotInsCommandProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotRecommendationRequestMessageProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUnknownCommandProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateCommandProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateDispatchProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateProcessor;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateValidationRules;
import dev.sergevas.tool.katya.gluco.bot.xdrip.XdripConfig;
import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.LastReadingCacheManager;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        XdripConfig.class,
        RecommendationConfig.class
})
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
            InfluxDbServerApiClient influxDbServerApiClient,
            LastReadingCacheManager lastReadingCacheManager) {
        return new ReadingService(influxDbServerApiClient, lastReadingCacheManager);
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
    public KatyaGlucoBotFactory katyaGlucoBotFactory(TelegramBotProperties telegramBotProperties,
                                                     BotUpdateDispatchProcessor botCommandDispatchProcessor) {
        return new KatyaGlucoBotFactory(telegramBotProperties, botCommandDispatchProcessor);
    }

    @Bean
    KatyaGlucoBot katyaGlucoBot(KatyaGlucoBotFactory katyaGlucoBotFactory) {
        return katyaGlucoBotFactory.getObject();
    }
}
