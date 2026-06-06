package com.trustlink.trustlink.analyzer;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

@Service
public class InputAnalyzer {
    private static final List<Pattern> SQLI_PATTERNS = List.of(
            Pattern.compile("(\'|\")(\s)*(or|and)(\s)*(\'|\")", CASE_INSENSITIVE),
            Pattern.compile("union(\s)+select", CASE_INSENSITIVE),
            Pattern.compile("drop(\s)+table", CASE_INSENSITIVE),
            Pattern.compile("insert(\s)+into", CASE_INSENSITIVE),
            Pattern.compile("select(\s)+.*from", CASE_INSENSITIVE),
            Pattern.compile("(\'|\")(\s)*(;|--)", CASE_INSENSITIVE),
            Pattern.compile("exec(\s)*\\(", CASE_INSENSITIVE)
    );

    private static final List<Pattern> XSS_PATTERNS = List.of(
            Pattern.compile("<script[^>]*>", CASE_INSENSITIVE),
            Pattern.compile("javascript\\s*:", CASE_INSENSITIVE),
            Pattern.compile("on\\w+\\s*=", CASE_INSENSITIVE),  // onerror=, onclick=
            Pattern.compile("<iframe", CASE_INSENSITIVE),
            Pattern.compile("document\\.cookie", CASE_INSENSITIVE),
            Pattern.compile("eval\\s*\\(", CASE_INSENSITIVE)
    );

    public int getSqliDeduction(String payload){
        long matches = SQLI_PATTERNS.stream().filter(p -> p.matcher(payload).find()).count();
        if(matches == 0)  return 0;
        if(matches == 1)  return 15;
        if(matches == 2)  return 25;
        return 35;
    }
    public int getXssDeduction(String payload){
        long matches = XSS_PATTERNS.stream().filter(p -> p.matcher(payload).find()).count();
        if(matches == 0)  return 0;
        if(matches == 1)  return 10;
        if(matches == 2)  return 20;
        return 30;
    }
    public int getHeaderDeduction(HttpServletRequest request){
        if((request.getHeader("User-Agent") == null || request.getHeader("User-Agent").isEmpty())  || (request.getHeader("Content-Type") == null || request.getHeader("Content-Type").isEmpty())){
            return 10;
        }else{
            return 0;
        }
    }
    public int getPayloadSizeDeduction(String payload){
        if(payload == null || payload.isEmpty()) return 0;
        if(payload.getBytes().length > 10240) return 10;
        return 0;
    }


}
