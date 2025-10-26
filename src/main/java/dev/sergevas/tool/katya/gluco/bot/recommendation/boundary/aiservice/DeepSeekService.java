package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary.aiservice;

import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;

//@ApplicationScoped
//@RegisterAiService
public interface DeepSeekService {

    //    @SystemMessage(fromResource = "prompts/system.txt")
//    @UserMessage(fromResource = "prompts/user.txt")
    Recommendation generateRecommendation(String request);
}
