package com.example.urltest;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownUtil {

	// 定义下载资源路径
	private String path;
	// 定义下载文件保存位置
	private String tragetFile;
	// 定义需要多少个线程下载资源
	private int threadNum;
	// 定义线程的下载对象
	private DownloadThread[] threads;
	// 定义下载文件总大小
	private int fileSize;

	public DownUtil(String path, String tragetFile, int threadNum) {
		this.path = path;
		this.tragetFile = tragetFile;
		this.threadNum = threadNum;
		// 初始化threads数组
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
			// 得到文件大小
			fileSize = conn.getContentLength();
			conn.disconnect();
			int currentParSize = fileSize / threadNum + 1;
			RandomAccessFile file = new RandomAccessFile(tragetFile, "rw");
			// 设置本地文件大小
			file.setLength(fileSize);
			file.close();
			for (int i = 0; i < threadNum; i++) {
				// 计算每条线程开始现在的位置
				int startPos = i * currentParSize;
				// 每条线程使用RandomAccessFile进行下载
				RandomAccessFile currentPar = new RandomAccessFile(tragetFile,
						"rw");
				// 定义线程下载位置
				currentPar.seek(startPos);
				// 创建下载线程
				threads[i] = new DownloadThread(startPos, currentParSize,
						currentPar);
				// 启动下载线程
				threads[i].start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得下载完成的百分比
	public double getCompleteRate() {
		// 统计多条线程下载的总大小
		int sumSize = 0;
		for (int i = 0; i < threadNum; i++) {
			sumSize += threads[i].length;
		}
		// 返回完成的百分比
		return sumSize * 1.0 / fileSize;
	}

	private class DownloadThread extends Thread {
		// 当前线程下载的位置
		private int startPos;
		// 当前线程负责下载文件的大小
		private int currentPartSize;
		// 当前线程负责下载的文件块
		private RandomAccessFile currentPart;
		// 定义该线程现在的字节数
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
				// 跳过startPos个字节，表面该线程自负责下载自己负责的部分
				is.skip(this.startPos);

				byte[] buffer = new byte[1024];
				int hasRead = 0;
				// 读取网络数据，并写入本地文件
				while (length < currentPartSize
						&& (hasRead = is.read(buffer)) != -1) {
					currentPart.write(buffer, 0, hasRead);
					// 累计下载总大小
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
