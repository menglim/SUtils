package com.github.menglim.sutils;

import com.github.menglim.mutils.AppUtils;
import com.github.menglim.sutils.annotations.navigation.Navigation;
import com.github.menglim.sutils.models.MessageResult;
import com.github.menglim.sutils.models.NavigationModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SUtils extends AppUtils {

    private static SUtils instance;

    public static SUtils getInstance() {
        if (instance == null) instance = new SUtils();
        return instance;
    }

    public boolean isAjaxRequest(HttpServletRequest httpServletRequest) {
        String ajaxHeader = httpServletRequest.getHeader("X-Requested-With");
        //log.info("AjaxHeader is " + ajaxHeader);
        if (ajaxHeader != null && ajaxHeader.equals("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    private NavigationModel getNavigation(String prefixUrl, Annotation annotation, NavigationModel parent) {
        String url;
        String name;
        NavigationModel model;
        if (annotation instanceof RequestMapping) {
            RequestMapping requestMapping = (RequestMapping) annotation;
            url = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
            name = requestMapping.name();
            model = new NavigationModel(prefixUrl + url, name, prefixUrl.equals(""), null, parent);
            return model;
        } else if (annotation instanceof GetMapping) {
            GetMapping requestMapping = (GetMapping) annotation;
            url = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
            name = requestMapping.name();
            model = new NavigationModel(prefixUrl + url, name, prefixUrl.equals(""), null, parent);
            return model;
        } else if (annotation instanceof PostMapping) {
            PostMapping requestMapping = (PostMapping) annotation;
            url = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
            name = requestMapping.name();
            model = new NavigationModel(prefixUrl + url, name, prefixUrl.equals(""), null, parent);
            return model;
        }
        return null;
    }

    public List<NavigationModel> getNavigation(Class clazz) {
        List<NavigationModel> navigationModels = new ArrayList<>();
        Annotation[] annotations = clazz.getAnnotations();
        NavigationModel rootParent = null;
        for (Annotation ann : annotations) {
            //Getting Parent
            if (ann instanceof Navigation) {
                Navigation rootNavigationAnn = (Navigation) ann;
                if (rootNavigationAnn.clazz() == Void.class) {
                    rootParent = new NavigationModel("javascript:void(0);", rootNavigationAnn.name(), rootNavigationAnn.menu(), rootNavigationAnn.icon(), null);
                    if (!navigationModels.contains(rootParent))
                        navigationModels.add(rootParent);
                }
            }

            NavigationModel rootNavigationModel = getNavigation("", ann, rootParent);
            if (nonNull(rootNavigationModel)) {
                navigationModels.add(rootNavigationModel);
                Method[] methods = clazz.getMethods();
                for (Method m : methods) {
//                    System.out.println("MethodName =>" + m.getName());
//                    Annotation[] subAnnotations = m.getAnnotations();
                    Annotation[] subAnnotations = m.getAnnotations();
                    for (Annotation subAnn : subAnnotations) {
                        NavigationModel subNavigationModel = getNavigation(rootNavigationModel.getUrl(), subAnn, rootNavigationModel);
                        if (nonNull(subNavigationModel)) {
                            if (!navigationModels.contains(subNavigationModel))
                                navigationModels.add(subNavigationModel);
                        }
                    }
                }
                Method[] superMethod = clazz.getSuperclass().getMethods();
                for (Method m : superMethod) {
//                    System.out.println("MethodName =>" + m.getName());
                    Annotation[] subAnnotations = m.getAnnotations();
                    for (Annotation subAnn : subAnnotations) {
                        NavigationModel subNavigationModel = getNavigation(rootNavigationModel.getUrl(), subAnn, rootNavigationModel);
                        if (nonNull(subNavigationModel)) {
                            if (!navigationModels.contains(subNavigationModel))
                                navigationModels.add(subNavigationModel);
                        }
                    }
                }
            }
        }
        return navigationModels;
    }

    public List<String> getMappingUrl(Class clazzOfController) {
        List<String> listUrl = new ArrayList<>();
        Annotation[] annotations = clazzOfController.getAnnotations();
        final String[] urls = new String[1];
        Arrays.stream(annotations).forEach(annotation ->
        {
            if (annotation instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) annotation;
                urls[0] = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
            } else if (annotation instanceof GetMapping) {
                GetMapping requestMapping = (GetMapping) annotation;
                urls[0] = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
            } else if (annotation instanceof PostMapping) {
                PostMapping requestMapping = (PostMapping) annotation;
                urls[0] = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
            }
        });
        String rootUrl = urls[0];
        String subUrl = "";
        Method[] methods = clazzOfController.getMethods();
        for (Method m : methods) {
            Annotation[] subAnnotations = m.getAnnotations();
            for (Annotation subAnn : subAnnotations) {
                if (subAnn instanceof RequestMapping) {
                    RequestMapping requestMapping = (RequestMapping) subAnn;
                    subUrl = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
                } else if (subAnn instanceof GetMapping) {
                    GetMapping requestMapping = (GetMapping) subAnn;
                    subUrl = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
                } else if (subAnn instanceof PostMapping) {
                    PostMapping requestMapping = (PostMapping) subAnn;
                    subUrl = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
                }
            }
            listUrl.add(rootUrl + subUrl);
        }
        Method[] superMethod = clazzOfController.getSuperclass().getMethods();
        for (Method m : superMethod) {
            Annotation[] subAnnotations = m.getAnnotations();
            for (Annotation subAnn : subAnnotations) {
                if (subAnn instanceof RequestMapping) {
                    RequestMapping requestMapping = (RequestMapping) subAnn;
                    subUrl = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
                } else if (subAnn instanceof GetMapping) {
                    GetMapping requestMapping = (GetMapping) subAnn;
                    subUrl = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
                } else if (subAnn instanceof PostMapping) {
                    PostMapping requestMapping = (PostMapping) subAnn;
                    subUrl = (requestMapping.value().length > 0) ? requestMapping.value()[0] : "";
                }
            }
            listUrl.add(rootUrl + subUrl);
        }
        return listUrl.stream().distinct().collect(Collectors.toList());
    }

    public String getExtensionFilename(MultipartFile file) {
        return getExtensionFilename(file.getOriginalFilename());
    }

    public boolean isUserAuthenticated() {
        boolean result = (
                SecurityContextHolder.getContext().getAuthentication() != null &&
                        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken));
        return result;
    }

    public String[] getUsernamePasswordBasicAuthentication(String authorization) {
        if (nonNull(authorization) && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            return credentials.split(":");
        }
        return null;
    }

    public MessageResult getMessageResult(boolean isSuccess, String message) {
        MessageResult result = new MessageResult();
        result.setSuccess(isSuccess);
        result.setMessage(message);
        return result;
    }

    public String getHeaderParameter(HttpServletRequest request, String parameterName) {
        return request.getHeader(parameterName);
    }

    public String getClientIP(HttpServletRequest request) {
        String[] IP_HEADER_CANDIDATES = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"};

        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public String getBaseUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        return basePath;
    }

    public String getRequestUrl(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getScheme() + "://" +
                httpServletRequest.getServerName() +
                ("http".equals(httpServletRequest.getScheme()) && httpServletRequest.getServerPort() == 80 || "https".equals(httpServletRequest.getScheme()) && httpServletRequest.getServerPort() == 443 ? "" : ":" + httpServletRequest.getServerPort()) +
                httpServletRequest.getRequestURI() +
                (httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "");
        return uri;
    }

    public String getLanguageCode(HttpServletRequest httpServletRequest) {
        return AppUtils.getInstance().getLanguageCode(httpServletRequest.getLocale());
    }


    public boolean isPost(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString());
    }

    public boolean isPut(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.PUT.toString());
    }

    public static String getLoggedUsername() {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "";
    }

    public String getRequestedUrl(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getScheme() + "://" +
                httpServletRequest.getServerName() +
                ("http".equals(httpServletRequest.getScheme()) && httpServletRequest.getServerPort() == 80 || "https".equals(httpServletRequest.getScheme()) && httpServletRequest.getServerPort() == 443 ? "" : ":" + httpServletRequest.getServerPort()) +
                httpServletRequest.getRequestURI() +
                (httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "");
        return uri;
    }
}
