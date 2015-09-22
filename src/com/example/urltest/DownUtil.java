package com.example.urltest;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownUtil {

	// ����������Դ·��
	private String path;
	// ���������ļ�����λ��
	private String tragetFile;
	// ������Ҫ���ٸ��߳�������Դ
	private int threadNum;
	// �����̵߳����ض���
	private DownloadThread[] threads;
	// ���������ļ��ܴ�С
	private int fileSize;

	public DownUtil(String path, String tragetFile, int threadNum) {
		this.path = path;
		this.tragetFile = tragetFile;
		this.threadNum = threadNum;
		// ��ʼ��threads����
		threads = new DownloadThread[threadNum];
	}

	public void download() {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty(
					"Accept",
					"image/gif,image/jpeg,image/pjpeg,application/x-shockwaveflash,"
							+ "application/xaml+xml,application/vnd.ms-xpsdocument,application/ x-ms-xbap,"
							+ "application/x-ms-application,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/msword,*/*");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0(compatible;MSIE7.0;Windows NT 5.2;Trident/4.0;"
							+ ".NET CLR 1.1.4322;.NET CLR 2.0.50727;.NET CLR 3.0.4506.2152;.NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			// �õ��ļ���С
			fileSize = conn.getContentLength();
			conn.disconnect();
			int currentParSize = fileSize / threadNum + 1;
			RandomAccessFile file = new RandomAccessFile(tragetFile, "rw");
			// ���ñ����ļ���С
			file.setLength(fileSize);
			file.close();
			for (int i = 0; i < threadNum; i++) {
				// ����ÿ���߳̿�ʼ���ڵ�λ��
				int startPos = i * currentParSize;
				// ÿ���߳�ʹ��RandomAccessFile��������
				RandomAccessFile currentPar = new RandomAccessFile(tragetFile,
						"rw");
				// �����߳�����λ��
				currentPar.seek(startPos);
				// ���������߳�
				threads[i] = new DownloadThread(startPos, currentParSize,
						currentPar);
				// ���������߳�
				threads[i].start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���������ɵİٷֱ�
	public double getCompleteRate() {
		// ͳ�ƶ����߳����ص��ܴ�С
		int sumSize = 0;
		for (int i = 0; i < threadNum; i++) {
			sumSize += threads[i].length;
		}
		// ������ɵİٷֱ�
		return sumSize * 1.0 / fileSize;
	}

	private class DownloadThread extends Thread {
		// ��ǰ�߳����ص�λ��
		private int startPos;
		// ��ǰ�̸߳��������ļ��Ĵ�С
		private int currentPartSize;
		// ��ǰ�̸߳������ص��ļ���
		private RandomAccessFile currentPart;
		// ������߳����ڵ��ֽ���
		public int length;

		public DownloadThread(int startPos, int currentPartSize,
				RandomAccessFile currentPart) {
			this.startPos = startPos;
			this.currentPartSize = currentPartSize;
			this.currentPart = currentPart;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				conn.setRequestProperty(
						"Accept",
						"image/gif,image/jpeg,image/pjpeg,application/x-shockwaveflash,"
								+ "application/xaml+xml,application/vnd.ms-xpsdocument,application/ x-ms-xbap,"
								+ "application/x-ms-application,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/msword,*/*");
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Charset", "UTF-8");
				InputStream is = conn.getInputStream();
				// ����startPos���ֽڣ�������߳��Ը��������Լ�����Ĳ���
				is.skip(this.startPos);

				byte[] buffer = new byte[1024];
				int hasRead = 0;
				// ��ȡ�������ݣ���д�뱾���ļ�
				while (length < currentPartSize
						&& (hasRead = is.read(buffer)) != -1) {
					currentPart.write(buffer, 0, hasRead);
					// �ۼ������ܴ�С
					length += hasRead;
				}
				currentPart.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
