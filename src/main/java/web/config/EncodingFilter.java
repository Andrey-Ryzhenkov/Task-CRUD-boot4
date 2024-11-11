package web.config;

import org.springframework.web.filter.CharacterEncodingFilter;

public class EncodingFilter extends CharacterEncodingFilter {
    public EncodingFilter() {
        setEncoding("UTF-8");
        setForceEncoding(true);
    }
}