package com.layermark.layermark_sarismet.generic;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class CustomMessageSource extends ReloadableResourceBundleMessageSource {

    public CustomMessageSource() {
        super();
    }

    public String getMessage(String messageCode){
        return  this.getMessage(messageCode,null, LocaleContextHolder.getLocale());
    }
}