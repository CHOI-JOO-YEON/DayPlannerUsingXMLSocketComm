package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;
import server.NewUser;
import server.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {
    private static User user;
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static NewUser newUser;
    String duplicateFilepath = "source/test/duplicate_newUser.xml";
    String nonDuplicateFilepath = "source/test/nonDuplicate_newUser.xml";

    @BeforeEach
    void beforeAll() {
        user = new User(new File("source/user.xml"));
    }

    static void setFactory() {
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    static void setNewUser(String id) {
        newUser = new NewUser(id, "1234", "hi");
    }


    @Test
    void 로그인_테스트() {
        assertThat(user.isLogin("Joo", "1234")).isTrue();
    }

    @Test
    void 로그인_실패_테스트() {
        assertThat(user.isLogin("Joo", "5678")).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"Joo:false", "Kim:true"}, delimiter = ':')
    void ID_중복_테스트(String id, boolean answer) {
        assertThat(user.isNonDuplicateId(id)).isEqualTo(answer);
    }

    @Test
    void 자기소개_XML_생성테스트() {
        Document document = user.createIntroduceDocument("안녕하세요");
        String intro = document.getDocumentElement().getFirstChild().getNodeValue();
        assertThat(intro).isEqualTo("안녕하세요");
    }

    @Test
    void userXML에서_자기소개_문자열_생성() {
        String result = user.getIntroByUserXML("Joo");
        assertThat(result).isEqualTo("My name is Joo");
    }

    @Test
    void 신규유저추가_중복() {
        setFactory();
        setNewUser("Joo");
        Optional<Document> result = user.getNewXML(newUser);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    void 신규유저추가() {
        setFactory();
        setNewUser("Kim");
        Optional<Document> result = user.getNewXML(newUser);
        int nodeListSize = result.get().getElementsByTagName("user").getLength();
        assertThat(nodeListSize).isEqualTo(2);
    }

}
