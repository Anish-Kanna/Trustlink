package com.trustlink.trustlink;

import com.trustlink.trustlink.analyzer.InputAnalyzer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class InputAnalyzerTest {
    InputAnalyzer analyzer = new InputAnalyzer();

    @Test
    void sqliZeroPatterns_returnsZero(){
        assertEquals(0, analyzer.getSqliDeduction("hello world"));
    }

    @Test
    void sqliOnePattern_returns15(){
        assertEquals(15, analyzer.getSqliDeduction("1' OR '1'='1"));
    }

    @Test
    void sqliTwoPattern_returns25(){
        assertEquals(25, analyzer.getSqliDeduction("1' OR '1'='1 UNION SELECT"));
    }

    @Test
    void sqliThreePattern_returns35(){
        assertEquals(35, analyzer.getSqliDeduction("1' OR '1'='1 UNION SELECT DROP TABLE"));
    }

    @Test
    void xssZeroPattern_returnsZero(){
        assertEquals(0, analyzer.getXssDeduction("hello"));
    }

    @Test
    void xssOnePattern_returns10(){
        assertEquals(10, analyzer.getXssDeduction("<script>alert(1)</script>"));
    }

    @Test
    void xssTwoPattern_returns20(){
        assertEquals(20, analyzer.getXssDeduction("<script>alert(1)</script> onerror="));
    }

    @Test
    void payloadOverLimit_returnsZero(){
        assertEquals(0, analyzer.getPayloadSizeDeduction("small"));
    }

    @Test
    void payloadOverLimit_returns10(){
        String payload = "a".repeat(11000);
        assertEquals(10, analyzer.getPayloadSizeDeduction(payload));
    }

}
