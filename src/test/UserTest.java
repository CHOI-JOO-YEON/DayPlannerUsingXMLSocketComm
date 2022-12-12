package test;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import server.User;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {
    User user = new User();
    @Test
    void 로그인_테스트(){
        assertThat(user.isLogin("Joo", "1234")).isTrue();
    }

    @Test
    void 로그인_실패_테스트() {
        assertThat(user.isLogin("Joo", "5678")).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"Joo:false","Kim:true"}, delimiter = ':')
    void ID_중복_테스트(String id, boolean answer) {
        assertThat(user.isNonDuplicateId(id)).isEqualTo(answer);
    }

    @Test
    void 자기소개_XML_생성테스트() {
        Document document =  user.createIntroduceDocument("안녕하세요");
        String intro = document.getDocumentElement().getFirstChild().getNodeValue();
        assertThat(intro).isEqualTo("안녕하세요");
    }

}
