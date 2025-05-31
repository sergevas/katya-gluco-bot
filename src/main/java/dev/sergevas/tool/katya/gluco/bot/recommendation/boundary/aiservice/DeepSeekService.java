package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary.aiservice;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService
public interface DeepSeekService {

    @SystemMessage("""
            Ты - медицинская экспертная система, твоя специализация - диабет.
            Помогаешь выполнять расчеты доз (единиц) быстрого инсулина перед принятием пищи.
            Перепроверяй математические расчеты, не допускай ошибок при округлении, пожалуйста.
            """)
    @UserMessage("""
            Сколько нужно инсулина на {{it}} из расчета 1 ед инсулина на 12 г углеводов?
            Сгенерируй ответ в JSON. Краткий ответ в формате 'На {} грамм нужно сделать {} ед инсулина' добавь в поле message.
            Дополнительную информацию, например, объяснение расчетов, добавь в поле additionalInfo""")
    Recommendation generateBook(String request);
}
