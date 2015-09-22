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
			String urlName = url + "��" + params;
			URL realUrl = new URL(urlName);
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.1;SV1)");
			// ��������
			conn.connect();
			// �����Ӧ��ͷ�Ӷ�
			Map<String, List<String>> map = conn.getHeaderFields();
			// �������е���Ӧͷ�Ӷ�
			for (String key : map.keySet()) {
				System.out.println(key + "-------->" + map.keySet());
			}
			// ����BufferReader�������� ��ȡ����
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("����GET�����쳣" + e);
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
			// �������������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.1;SV1)");
			// ����Post�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConncetion��Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(params);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡ��Ӧ
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("����POST�����쳣");
			e.printStackTrace();
		}finally{
			try {
				//�ر������������
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
