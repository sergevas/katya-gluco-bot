package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary.aiservice;

//import dev.langchain4j.service.SystemMessage;
//import dev.langchain4j.service.UserMessage;
import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;
//import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

//@ApplicationScoped
//@RegisterAiService
public interface DeepSeekService {

//    @SystemMessage(fromResource = "prompts/system.txt")
//    @UserMessage(fromResource = "prompts/user.txt")
    Recommendation generateRecommendation(String request);
}
