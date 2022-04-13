package site.metacoding.blogv3.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import site.metacoding.blogv3.handler.ex.CustomException;

public class UtilValid {

    public static void 요청에러처리(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // 하나라도 오류가 있으면 true
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                // System.out.println(fe.getField()); // 어떤 변수인지 알려줌
                // System.out.println(fe.getDefaultMessage()); // 어떤 문제때문인지 알려줌.
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new CustomException(errorMap.toString());
        }
    }
}
