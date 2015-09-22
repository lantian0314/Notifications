package com.example.urltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class GetPostUtil {

	public static String sendGet(String url, String params) {
		String result = "";
		BufferedReader br = null;
		try {
			String urlName = url + "？" + params;
			URL realUrl = new URL(urlName);
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			conn.connect();
			// 获得响应的头子段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头子段
			for (String key : map.keySet()) {
				System.out.println(key + "-------->" + map.keySet());
			}
			// 定义BufferReader的输入流 读取内容
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求异常" + e);
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String params) {
		String result = "";
		BufferedReader br = null;
		PrintWriter out = null;
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			// 设置请求的属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送Post请求必须设置下面两项
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConncetion对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取响应
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求异常");
			e.printStackTrace();
		}finally{
			try {
				//关闭输出、输入流
				if (out!=null) {
					out.close();
				}
				if (br!=null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
