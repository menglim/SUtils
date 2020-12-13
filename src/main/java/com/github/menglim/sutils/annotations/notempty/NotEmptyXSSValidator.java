package com.github.menglim.sutils.annotations.notempty;

import com.github.menglim.sutils.SUtils;
import org.springframework.web.util.HtmlUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyXSSValidator implements ConstraintValidator<NotEmptyXSS, Object> {

    private boolean stripHtml;
    private boolean removeSpace;
    private boolean allowedNull;
    private int length;

    @Override
    public void initialize(NotEmptyXSS constraintAnnotation) {
        stripHtml = constraintAnnotation.stripHtml();
        removeSpace = constraintAnnotation.removeSpace();
        allowedNull = constraintAnnotation.allowedNull();
        length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object instanceof String) {
            String value = String.valueOf(object);
            if (removeSpace) {
                value = value.replaceAll("\\s+", "");
            }
            if (allowedNull) {
                if (SUtils.getInstance().isNull(value)) {
                    return true;
                }
            }
            if (length != -1) {
                return value.length() == length;
            }
//            value = strip_html_tags(value);
            if (SUtils.getInstance().isNull(value)) return false;
            if (value.trim() == "") return false;
            return !isHtml(value);
        } else {
            return SUtils.getInstance().nonNull(object);
        }
    }

    public static boolean isHtml(String input) {
        boolean isHtml = false;
        if (input != null) {
            String htmlEscapedHtml = HtmlUtils.htmlEscape(input);
            if (!input.equals(htmlEscapedHtml)) {
                isHtml = true;
            }
        }
        return isHtml;
    }

//    public String strip_html_tags(String text) {
//
//        text = text.replaceAll("@(.*?)<style[^>]*?>.*?</style>@siu", " ");
//        text = text.replaceAll("@(.*?)<script[^>]*?.*?</script>@siu", " ");
//        text = text.replaceAll("@(.*?)<iframe[^>]*?.*?</iframe>@siu", " ");
//        text = text.replaceAll("@(.*?)<html[^>]*?.*?</html>@siu", " ");
//
//        if (text != null) {
//            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
//            // avoid encoded attacks.
//            // value = ESAPI.encoder().canonicalize(value);
//
//            // Avoid null characters
//            text = text.replaceAll("", "");
//
//            // Avoid anything between script tags
//            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Avoid anything in a src='...' type of expression
//            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Remove any lonesome </script> tag
//            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Remove any lonesome <script ...> tag
//            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Avoid eval(...) expressions
//            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Avoid expression(...) expressions
//            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Avoid javascript:... expressions
//            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Avoid vbscript:... expressions
//            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
//            text = scriptPattern.matcher(text).replaceAll("");
//
//            // Avoid onload= expressions
//            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            text = scriptPattern.matcher(text).replaceAll("");
//        }
//
//        if (isUTF8MisInterpreted(text) == true) {
//            text = Jsoup.clean(
//                    text,
//                    Whitelist
//                            .basic()
//                            .addTags("img")
//                            .addTags("table")
//                            .addTags("span")
//                            .addTags("cite")
//                            .addTags("dfn")
//                            .addTags("acronym")
//                            .addTags("sup")
//                            .addTags("samp")
//                            .addTags("strong")
//                            .addTags("caption")
//                            .addTags("blockquote")
//                            .addTags("tfoot")
//                            .addTags("tbody")
//                            .addTags("div")
//                            .addTags("td")
//                            .addTags("tr")
//                            .addTags("p")
//                            .addAttributes("table", "border", "class", "style")
//                            .addAttributes("span", "style", "strong")
//                            .addTags("br")
//                            .addAttributes("img", "alt", "style", "height",
//                                    "width", "src")
//                            .addAttributes("th", "colspan", "align", "*")
//                            .addAttributes("thead", "align"));
//
//        }
//
//        // System.out.println(text);
//        text = text.replaceAll("/alert/i", "");
//        text = text.replaceAll("/onerror=/i", "");
//        text = text.replaceAll("/onmouseover=/i", "");
//        text = text.replaceAll("/onmouseover/i", "");
//        text = text.replaceAll("/onmouseout=/i", "");
//        text = text.replaceAll("/onmouseout/i", "");
//        text = text.replaceAll("/onkeydown/i", "");
//        text = text.replaceAll("/onkeydown=/i", "");
//        text = text.replaceAll("/onkeypress=/i", "");
//        text = text.replaceAll("/onkeypress/i", "");
//        text = text.replaceAll("/onkeyup=/i", "");
//        text = text.replaceAll("/onkeyup/i", "");
//        text = text.replaceAll("/onclick=/i", "");
//        text = text.replaceAll("/onclick/i", "");
//        text = text.replaceAll("/onload=/i", "");
//        text = text.replaceAll("/onload/i", "");
//        text = text.replaceAll("/ondblclick/i", "");
//        text = text.replaceAll("/ondblclick=/i", "");
//        text = text.replaceAll("/ondrag=/i", "");
//        text = text.replaceAll("/ondrag/i", "");
//        text = text.replaceAll("/ondragend=/i", "");
//        text = text.replaceAll("/ondragend/i", "");
//        text = text.replaceAll("/ondragenter=/i", "");
//        text = text.replaceAll("/ondragenter/i", "");
//        text = text.replaceAll("/ondragleave/i", "");
//        text = text.replaceAll("/ondragleave=/i", "");
//        text = text.replaceAll("/ondragover=/i", "");
//        text = text.replaceAll("/ondragover/i", "");
//        text = text.replaceAll("/ondragstart=/i", "");
//        text = text.replaceAll("/ondragstart/i", "");
//        text = text.replaceAll("/ondrop=/i", "");
//        text = text.replaceAll("/ondrop/i", "");
//        text = text.replaceAll("/onmousedown/i", "");
//        text = text.replaceAll("/onmousedown=/i", "");
//        text = text.replaceAll("/onmousemove=/i", "");
//        text = text.replaceAll("/onmousemove/i", "");
//        text = text.replaceAll("/onmouseup=/i", "");
//        text = text.replaceAll("/onmouseup/i", "");
//        text = text.replaceAll("/onmousewheel=/i", "");
//        text = text.replaceAll("/onmousewheel/i", "");
//        text = text.replaceAll("/onscroll=/i", "");
//        text = text.replaceAll("/onscroll/i", "");
//        text = text.replaceAll("/document.cookie/i", "");
//        text = text.replaceAll("/prompt/i", "");
//        text = text.replaceAll("/onselect/i", "");
//        text = text.replaceAll("/type=/i", "");
//        text = text.replaceAll("/document.domain/i", "");
//        text = text.replaceAll("/confirm(domain)/i", "");
//        return escapeHtml(text).trim();
//    }

//    private boolean isUTF8MisInterpreted(String input) {
//        return isUTF8MisInterpreted(input, "Windows-1252");
//    }
//
//    private boolean isUTF8MisInterpreted(String input, String encoding) {
//
//        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
//        CharsetEncoder encoder = Charset.forName(encoding).newEncoder();
//        ByteBuffer tmp;
//        try {
//            tmp = encoder.encode(CharBuffer.wrap(input));
//        } catch (CharacterCodingException e) {
//            return false;
//        }
//
//        try {
//            decoder.decode(tmp);
//            return true;
//        } catch (CharacterCodingException e) {
//            return false;
//        }
//    }
}
