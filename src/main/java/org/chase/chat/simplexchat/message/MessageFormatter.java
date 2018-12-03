package org.chase.chat.simplexchat.message;

import lombok.extern.slf4j.Slf4j;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;
import org.nibor.autolink.Span;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.EnumSet;

@Component
@Slf4j
public class MessageFormatter {
    public String format(final String message) {
        log.info("Formatting message: {}", message);
        if (StringUtils.isEmpty(message)) {
            log.warn("Empty message!");
            return message;
        }
        String input = HtmlUtils.htmlEscape(message.trim());
        LinkExtractor linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL)) // limit to URLs
                .build();
        Iterable<Span> spans = linkExtractor.extractSpans(input);

        StringBuilder sb = new StringBuilder();
        for (Span span : spans) {
            String text = input.substring(span.getBeginIndex(), span.getEndIndex());
            if (span instanceof LinkSpan) {
                // span is a URL
                sb.append("<a href=\"");
                sb.append(Encode.forHtmlAttribute(text));
                sb.append("\">");
                sb.append(Encode.forHtml(text));
                sb.append("</a>");
            } else {
                // span is plain text before/after link
                sb.append(HtmlUtils.htmlEscape(text));
            }
        }

        return sb.toString();
    }
}
