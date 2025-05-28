package com.example.utils;

import com.example.dto.UserSimpleDto;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CustomJsonMapper 테스트")
class CustomJsonMapperTest {

    @DisplayName("json 타입을 받는다")
    @Test
    void json_타입을_받는다() {
        // Arrange
        JSONObject jsonInput = new JSONObject(); // 혹은 mock 또는 테스트용 객체 생성
        jsonInput.put("name", "chatgpt");
        jsonInput.put("age", 10);

        Class<?> targetType = UserSimpleDto.class; // 매핑할 대상 클래스
        Method method = null;
        try {
            method = CustomJsonMapper.class.getMethod("mapper", JSONObject.class, Class.class);
        } catch (NoSuchMethodException e) {
        }

        assertThat(method).isNotNull();
    }

    @DisplayName("클래스 타입을 받는다")
    @Test
    void 클래스_타입을_받는다() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        JSONObject object = new JSONObject(); // 혹은 mock 또는 테스트용 객체 생성
        object.put("id", 1);
        object.put("username", "james");
        object.put("email", "james@gmail.com");
        object.put("password", "123456");

        Class<UserSimpleDto> targetType = UserSimpleDto.class; // 매핑할 대상 클래스
        var userSimpleDto = CustomJsonMapper.mapper(object, targetType);
    }

    @DisplayName("파라미터로 받은 클래스 타입을 리턴한다")
    @Test
    void 파라미터로_받은_클래스_타입을_리턴한다() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        JSONObject object = new JSONObject(); // 혹은 mock 또는 테스트용 객체 생성
        object.put("id", 1);
        object.put("username", "james");
        object.put("email", "james@gmail.com");
        object.put("password", "123456");

        Class<UserSimpleDto> targetType = UserSimpleDto.class; // 매핑할 대상 클래스
        var userSimpleDto = CustomJsonMapper.mapper(object, targetType);

        assertThat(userSimpleDto).isInstanceOf(UserSimpleDto.class);
    }

    @DisplayName("json 을 key-value로 분류한다")
    @Test
    void json을_key_value로_분류한다(){

    }
}