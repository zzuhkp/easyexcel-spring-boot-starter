package com.zzuhkp.easyexcel.handler;

import com.alibaba.excel.EasyExcel;
import com.zzuhkp.easyexcel.annotation.ExcelResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author hkp
 * @date 2022/6/6 3:19 PM
 * @since 1.0
 */
public class ExcelResponseHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);
        return excelResponse != null && List.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);

        String fileName = URLEncoder.encode(excelResponse.fileName(), "UTF-8").replaceAll("\\+", "%20");
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        Class<?> component = ResolvableType.forMethodParameter(returnType).as(List.class).getGeneric(0).resolve();
        EasyExcel.write(response.getOutputStream(), component).autoCloseStream(Boolean.FALSE)
                .sheet(excelResponse.sheetName()).doWrite((List<?>) returnValue);

        response.getOutputStream().flush();
    }
}
