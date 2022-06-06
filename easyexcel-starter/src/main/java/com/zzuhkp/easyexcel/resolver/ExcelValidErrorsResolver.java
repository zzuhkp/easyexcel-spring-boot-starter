package com.zzuhkp.easyexcel.resolver;

import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author hkp
 * @date 2022/6/6 2:06 PM
 * @since 1.0
 */
public class ExcelValidErrorsResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == ExcelValidErrors.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ModelMap model = mavContainer.getModel();
        return model.getAttribute(BindingResult.MODEL_KEY_PREFIX + "excel");
    }
}
