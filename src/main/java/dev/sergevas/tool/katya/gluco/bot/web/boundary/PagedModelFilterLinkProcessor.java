package dev.sergevas.tool.katya.gluco.bot.web.boundary;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Enumeration;

public class PagedModelFilterLinkProcessor implements RepresentationModelProcessor<PagedModel<?>> {

    @Override
    public PagedModel<?> process(PagedModel<?> model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        // Извлекаем все query-параметры текущего запроса
        UriComponentsBuilder baseBuilder = UriComponentsBuilder
                .fromPath(request.getRequestURI());

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            for (String value : request.getParameterValues(name)) {
                baseBuilder.queryParam(name, value);
            }
        }

        // Добавляем фильтры к существующим ссылкам
        model.replaceLinks(model.getLinks().stream()
                .map(link -> addQueryParams(link, baseBuilder))
                .toList());

        return model;
    }

    private Link addQueryParams(Link originalLink, UriComponentsBuilder baseBuilder) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(originalLink.getHref());
        baseBuilder.build().getQueryParams()
                .forEach((k, v) -> v.forEach(val -> builder.queryParam(k, val)));
        return Link.of(builder.toUriString(), originalLink.getRel());
    }
}