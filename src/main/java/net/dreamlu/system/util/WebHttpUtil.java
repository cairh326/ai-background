package net.dreamlu.system.util;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Web请求调用工具类
 *
 * @author cairh
 */
public class WebHttpUtil {
	private static volatile CloseableHttpClient httpClient;
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(1500)
			.setSocketTimeout(1500)
			.setConnectionRequestTimeout(1000)
			.build();
		httpClient = HttpClients.custom()
			.setConnectionManager(cm)
			.setDefaultRequestConfig(requestConfig)
			.build();
	}
	/**
	 * 发送请求
	 * @param url 请求地址
	 * @param method 请求方法
	 * @param queryParams 请求参数,适用get,delete请求
	 * @param body 请求体,适用post,put请求
	 * @param headerParams 请求头部
	 * @param formParams 表单请求参数
	 * @param accept 发送端（客户端）希望接受的数据类型、
	 * @param contentType 发送端（客户端|服务器）发送的实体数据的数据类型
	 * @param authNames authNames认证名称
	 * @param connectTimeout 设置连接超时时间，单位毫秒。
	 * @param readTimeout    请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
	 * @return 返回影响结果
	 */
	public static <T> T invokeAPI(String url, String method, Map<String, String> queryParams
		, String body, Map<String, String> headerParams, Map<String, String> formParams
		, String accept, ContentType contentType, String[] authNames
		, Integer connectTimeout, Integer readTimeout, Class<T> cls) {
		CloseableHttpResponse response = null;
		try {
			response = getAPIResponse(url, method, queryParams, body, headerParams, formParams, accept, contentType, authNames, connectTimeout, readTimeout);
			int statusCode = response.getStatusLine().getStatusCode();
			switch (statusCode) {
				case 204:
					return null;
			}
			String respContent = EntityUtils.toString(response.getEntity());
			if (StringUtils.isBlank(respContent)) {
				return null;
			}
			if (cls == null) {
				return null;
			}
			return JsonUtils.parse(respContent, cls);
		} catch (URISyntaxException | IOException e) {
			throw new BaseException("远程调用URL【" + url + "】失败：" + e.getMessage());
		} finally {
			close(response);
		}


	}

	/**
	 * 发送请求
	 * @param url 请求地址
	 * @param method 请求方法
	 * @param queryParams 请求参数,适用get,delete请求
	 * @param body 请求体,适用post,put请求
	 * @param headerParams 请求头部
	 * @param formParams 表单请求参数
	 * @param accept 发送端（客户端）希望接受的数据类型、
	 * @param contentType 发送端（客户端|服务器）发送的实体数据的数据类型
	 * @param authNames authNames认证名称
	 * @param connectTimeout 设置连接超时时间，单位毫秒。
	 * @param readTimeout    请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
	 * @return 返回影响结果
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private static CloseableHttpResponse getAPIResponse(String url, String method, Map<String, String> queryParams
		,String body,Map<String, String> headerParams,Map<String, String> formParams
		,String accept,ContentType contentType,String[] authNames
		,Integer connectTimeout,Integer readTimeout)
		throws URISyntaxException, IOException {
		CloseableHttpResponse response;
		if (RestConstant.HTTPGET.equals(method)) {
			HttpGet request = new HttpGet();
			addRequestHeader(request, headerParams);
			URIBuilder builder = new URIBuilder(url);
			if (!CollectionUtils.isEmpty(queryParams)) {
				builder.setParameters(paramsConverter(queryParams));
			}
			request.setURI(builder.build());
			request.setConfig(getRequestConfig(connectTimeout,readTimeout));
			response = httpClient.execute(request);
		} else if (RestConstant.HTTPPOST.equals(method)) {
			HttpPost request = new HttpPost();
			addRequestHeader(request, headerParams);
			addURI(request, queryParams, url);
			HttpEntity he = (null == contentType) ? new UrlEncodedFormEntity(paramsConverter(formParams))
				: new StringEntity(body, ContentType.APPLICATION_JSON);
			request.setEntity(he);
			request.setConfig(getRequestConfig(connectTimeout,readTimeout));
			response = httpClient.execute(request);
		} else if (RestConstant.HTTPPUT.equals(method)) {
			HttpPut request = new HttpPut();
			addRequestHeader(request, headerParams);
			addURI(request, queryParams, url);
			if (null != body) {
				HttpEntity he = new StringEntity(body, ContentType.APPLICATION_JSON);
				request.setEntity(he);
			}
			request.setConfig(getRequestConfig(connectTimeout,readTimeout));
			response = httpClient.execute(request);
		} else if (RestConstant.HTTPDELETE.equals(method)) {
			HttpDelete request = new HttpDelete();
			addRequestHeader(request, headerParams);
			addURI(request, queryParams, url);
			request.setConfig(getRequestConfig(connectTimeout,readTimeout));
			response = httpClient.execute(request);
		} else {
			throw new BaseException("无效的Request Method【" + method + "】");
		}
		return response;
	}

	/**
	 * get请求url构建
	 * @param request
	 * @param queryParams
	 * @param url
	 * @throws URISyntaxException
	 */
	private static void addURI(HttpRequestBase request
		,Map<String, String> queryParams, String url)
		throws URISyntaxException {
		URIBuilder builder;
		try {
			builder = new URIBuilder(url);
		} catch (URISyntaxException e) {
			System.out.printf("URISyntaxException: {}", new Object[]{e});
			throw e;
		}
		if ((queryParams != null) && (!queryParams.isEmpty())) {
			builder.setParameters(paramsConverter(queryParams));
		}
		request.setURI(builder.build());
	}

	/**
	 * 参数格式转换
	 * @param params
	 * @return
	 */
	private static List<NameValuePair> paramsConverter(Map<String, String> params) {
		List<NameValuePair> nvps = new LinkedList();
		Set<Map.Entry<String, String>> paramsSet = params.entrySet();
		for (Map.Entry<String, String> paramEntry : paramsSet) {
			nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
		}
		return nvps;
	}

	/**
	 * 添加请求头部信息
	 * @param request
	 * @param headerMap
	 */
	private static void addRequestHeader(HttpUriRequest request, Map<String, String> headerMap) {
		if (headerMap == null) {
			return;
		}
		for (String headerName : headerMap.keySet()) {
			if (!RestConstant.CONTENT_LENGTH.equalsIgnoreCase(headerName)) {
				String headerValue = headerMap.get(headerName);
				request.addHeader(headerName, headerValue);
			}
		}
	}

	/**
	 * 获取连接配置
	 *
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 */
	private static RequestConfig getRequestConfig(Integer connectTimeout, Integer readTimeout) {
		if (connectTimeout == null) {
			connectTimeout = 500;
		}
		if (readTimeout == null) {
			readTimeout = 1500;
		}
		return RequestConfig.custom()
			.setConnectTimeout(connectTimeout)
			.setConnectionRequestTimeout(1000)
			.setSocketTimeout(readTimeout).build();
	}

	/**
	 * 关闭请求响应
	 * @param response
	 */
	private static void close(CloseableHttpResponse response){
		if(response != null){
			try {
				response.close();
			} catch (IOException e) {
			}
		}
	}
}
