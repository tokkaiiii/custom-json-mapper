# 커스텀 json mapper

"커스텀 json mapper 를 만듭니다"

### 사용법
```java
 @DisplayName("JSON 데이터와, 클래스 타입을 넣으면 해당 클래스 타입 객체로 반환된다")
    @Test
    void JSON_데이터와_클래스_타입을_넣으면_해당_클래스_타입_객체로_반환된다() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        // Arrange
        JSONObject object = new JSONObject(); // 혹은 mock 또는 테스트용 객체 생성
        object.put("id", 1);
        object.put("username", "james");
        object.put("email", "james@gmail.com");
        object.put("password", "123456");

        Class<UserSimpleDto> targetType = UserSimpleDto.class; // 매핑할 대상 클래스
    
    // mapper 메소드에 json 과 변환하고 싶은 타입을 넣으면 해당 타입 객체를 받을 수 있다
        var userSimpleDto = CustomJsonMapper.mapper(object, targetType);
        System.out.println(userSimpleDto);

        assertThat(userSimpleDto).isInstanceOf(UserSimpleDto.class);
    }
```

테스트
- [x] JSON 데이터와, 클래스 타입을 넣으면 해당 클래스 타입 객체로 반환된다
