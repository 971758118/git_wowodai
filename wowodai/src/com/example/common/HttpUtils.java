package com.example.common;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpUtils {
	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 从服务器获取图片数据
	 * 
	 * @param url
	 * @return
	 */
	public byte[] getPhoto(String url) {
		byte[] result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				byte[] result2 = EntityUtils.toByteArray(httpResponse
						.getEntity());
				return result2;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

	/**
	 * 得到返回结果中"result"节点中的list内容
	 * 
	 * @param u
	 * @return
	 */

	public List<HashMap<String, Object>> getListResultFromUrl(String u) {
		List<HashMap<String, Object>> list = null;
		String url = u.replaceAll(" ", "%20").replaceAll("%", "%25");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String THERESULT = EntityUtils.toString(
						httpResponse.getEntity(), "UTF-8");
				if (THERESULT != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map;
					map = objectMapper.readValue(THERESULT, Map.class);
					if (!map.get("result").toString().equals("false")) {
						list = (List<HashMap<String, Object>>) map
								.get("result");
					}

					return list;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return list;
	}

	/**
	 * 得到返回结果中"result"节点中的map内容
	 * 
	 * @param u
	 * @return
	 */

	public HashMap<String, Object> getMapResultFromUrl(String u) {
		HashMap<String, Object> map = null;
		String url = u.replaceAll(" ", "%20").replaceAll("%", "%25");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String THERESULT = EntityUtils.toString(
						httpResponse.getEntity(), "UTF-8");
				if (THERESULT != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					map = (HashMap<String, Object>) objectMapper.readValue(
							THERESULT, Map.class);

					if (map != null && map.get("result") != null
							&& !map.get("result").toString().equals("false")) {
						return (HashMap<String, Object>) map.get("result");
					}

				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return map;
	}

	/**
	 * 得到返回结果中"code"节点的内容
	 * 
	 * @param u
	 * @return
	 */

	public int getCodeFromUrl(String u) {
		int result = 0;
		String url = u.replaceAll(" ", "%20").replaceAll("%", "%25");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String THERESULT = EntityUtils.toString(
						httpResponse.getEntity(), "UTF-8");
				if (THERESULT != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map;
					map = objectMapper.readValue(THERESULT, Map.class);
					if (map.get("code") != null) {
						result = Integer.parseInt(map.get("code").toString());
						return result;
					}

				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return result;
	}

	/**
	 * 得到返回结果中"msg"节点的内容
	 * 
	 * @param u
	 * @return
	 */

	public String getMsgFromUrl(String u) {
		String result = null;
		String url = u.replaceAll(" ", "%20").replaceAll("%", "%25");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String THERESULT = EntityUtils.toString(
						httpResponse.getEntity(), "UTF-8");
				if (THERESULT != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map;
					map = objectMapper.readValue(THERESULT, Map.class);
					if (map.get("msg") != null) {
						result = map.get("msg").toString();
						return result;
					}
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return result;
	}

	public Map<String, Object> getMapFromUrl(String u) {
		Map<String, Object> map = null;
		String url = u.replaceAll(" ", "%20").replaceAll("%", "%25");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String THERESULT = EntityUtils.toString(
						httpResponse.getEntity(), "UTF-8");
				if (THERESULT != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					// Map<String, Object> map;
					map = objectMapper.readValue(THERESULT, Map.class);
					return map;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return map;
	}

	/**
	 * 从服务器获取json数据
	 * 
	 * @param url
	 * @return
	 */
	public String getJsonStr(String u) {
		String url = u.replaceAll(" ", "%20").replaceAll("%", "%25");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String THERESULT = EntityUtils.toString(
						httpResponse.getEntity(), "UTF-8");
				return THERESULT;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 向服务器发送json数据
	 * 
	 * @param s
	 */
	public void sendJsonToServer(String str) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost("");
			HttpParams httpParams = new BasicHttpParams();
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("jsonString", URLEncoder
					.encode(str, "utf-8")));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			httpPost.setParams(httpParams);
			HttpResponse response = httpClient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine != null && statusLine.getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("---发送成功,有结果");
				} else {
					System.out.println("---发送成功,无结果");
				}
			} else {
				System.out.println("---发送失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
