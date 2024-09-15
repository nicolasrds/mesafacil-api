package com.mesafacil.infra.util;

import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageService {

    public String getMessage(String mensagem) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource.getMessage(mensagem, null, LocaleContextHolder.getLocale());
    }
}
