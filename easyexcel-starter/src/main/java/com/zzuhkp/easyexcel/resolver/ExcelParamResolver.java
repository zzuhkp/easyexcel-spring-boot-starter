package com.zzuhkp.easyexcel.resolver;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zzuhkp.easyexcel.annotation.ExcelParam;
import com.zzuhkp.easyexcel.validator.*;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hkp
 * @date 2022/6/2 11:11 AM
 * @since
 */
public class ExcelParamResolver implements HandlerMethodArgumentResolver, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String contentType = request.getContentType();
        boolean multipart = contentType != null && contentType.toLowerCase().startsWith("multipart/");

        ExcelParam excelParam = methodParameter.getParameterAnnotation(ExcelParam.class);
        ResolvableType param = ResolvableType.forMethodParameter(methodParameter);
        return multipart && excelParam != null
                && (ResolvableType.forClass(ReadRows.class).isAssignableFrom(param)
                || ResolvableType.forClass(List.class).isAssignableFrom(param));
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        ExcelParam excelParam = methodParameter.getParameterAnnotation(ExcelParam.class);
        MultipartRequest request = nativeWebRequest.getNativeRequest(MultipartRequest.class);
        MultipartFile file = request.getFile(excelParam.value());
        if (file == null) {
            if (excelParam.required()) {
                throw new MissingServletRequestPartException(excelParam.value());
            }
            return null;
        }

        ReadRows<Object> readRows = new ReadRows<>();

        ResolvableType[] generics = ResolvableType.forType(methodParameter.getGenericParameterType()).getGenerics();
        Class<?> component = generics[generics.length - 1].resolve();
        EasyExcel.read(file.getInputStream(), component, new AnalysisEventListener<Object>() {

            @Override
            public void invoke(Object data, AnalysisContext context) {
                Integer rowIndex = context.readRowHolder().getRowIndex();
                readRows.getRows().add(new ReadRow<>(rowIndex, data));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                readRows.setExcelReadHeadProperty(context.currentReadHolder().excelReadHeadProperty());
            }
        }).sheet().doRead();

        ExcelValidErrors errors = this.validateIfApplicable(methodParameter, readRows);
        if (errors.hasErrors() && isBindExceptionRequired(methodParameter)) {
            throw new ExcelValidException("参数校验有误", errors);
        }
        if (modelAndViewContainer != null) {
            modelAndViewContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + "excel", errors);
        }

        if (List.class.isAssignableFrom(methodParameter.getParameterType())) {
            return readRows.getRows().stream().map(ReadRow::getData).collect(Collectors.toList());
        }
        if (ReadRows.class == methodParameter.getParameterType()) {
            return readRows;
        }
        return null;
    }

    private ExcelValidErrors validateIfApplicable(MethodParameter parameter, ReadRows<Object> readRows) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        boolean valid = false;
        for (Annotation ann : annotations) {
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            return new ExcelValidErrors();
        }

        Class<?> headClazz = readRows.getExcelReadHeadProperty().getHeadClazz();

        List<ExcelValidator<Object>> validators =
                this.beanFactory.getBeanProvider(ExcelValidator.class).stream().filter(item -> {
                    Class<?> component = ResolvableType.forInstance(item).as(ExcelValidator.class).resolveGeneric(0);
                    return component == Object.class || component.isAssignableFrom(headClazz);
                }).map(item -> (ExcelValidator<Object>) item).collect(Collectors.toList());
        CompositeExcelValidator validator = new CompositeExcelValidator(validators);

        return validator.validate(readRows);
    }

    private boolean isBindExceptionRequired(MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getExecutable().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && ExcelValidErrors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }
}
