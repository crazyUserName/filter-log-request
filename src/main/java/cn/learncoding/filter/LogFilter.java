package cn.learncoding.filter;

import cn.learncoding.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class LogFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		long requestTime = System.currentTimeMillis();
		String requestParameter = JsonUtil.toJson(request.getParameterMap());
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = uri.substring(contextPath.length());

//		暂定只有json 输出请求体
		String requestBody = "";
		String requestContentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
		if (requestContentType != null && requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE)){
			byte[] requestBodyBytes = getRequestBody(request);
			requestBody = new String(requestBodyBytes, StandardCharsets.UTF_8);
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBodyBytes);
			request = new HttpServletRequestWrapper(request) {
				@Override
				public ServletInputStream getInputStream() throws IOException {
					return new ByteArrayServletInputStream(byteArrayInputStream);
				}
			};
		}

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		response = new HttpServletResponseWrapper(response) {
			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return new TeeServletOutputStream(super.getOutputStream(), byteArrayOutputStream);
			}
		};
		filterChain.doFilter(request, response);
		long costTime = System.currentTimeMillis() - requestTime;
		String responseBody = "";
//		暂定只有json 输出响应体
		String contentType = response.getHeader(HttpHeaders.CONTENT_TYPE);
		if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)){
			responseBody = byteArrayOutputStream.toString();
		}
		log.info("URL:{}, Total time:{} ms, RequestParameter:{}, RequestBody:{}, ResponseBody:{}", url, costTime, requestParameter, requestBody, responseBody);
	}

	private byte[] getRequestBody(HttpServletRequest request) {
		int contentLength = request.getContentLength();
		if(contentLength <= 0){
			return new byte[0];
		}
		byte[] buffer = new byte[contentLength];
		try {
			IOUtils.read(request.getInputStream(), buffer);
            return buffer;
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
}
