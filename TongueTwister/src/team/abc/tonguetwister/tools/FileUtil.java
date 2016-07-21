package team.abc.tonguetwister.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.bean.TongueTwister;

public class FileUtil {

	public static void main(String[] args) {
		/*List<TongueTwister> list = new ArrayList<TongueTwister>();
		readFile("raokouling.txt", list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i);
			System.out.println(list.get(i));
		}*/
	}

	public static void readFile(String path, List<TongueTwister> list) {

		BufferedReader br = null;
		String line = null;
		TongueTwister tt = null;
		try {
			br = new BufferedReader(new InputStreamReader(
						MyApplication.getAppResources().getAssets().open("raokouling.txt"),"UTF-8")
					);

			br.readLine();// 第一行有问题，暂时跳过这一行。
			int id = 0;
			while ((line = br.readLine()) != null) {

				// 除去文档空白部分
				line = line.trim();
				if (line.equals("")) {
					continue;
				}

				// 如果这一行是标题
				if (startsWithNumber(line)) {
					line = line.replaceAll("[0-9]+[、|\\.]", "");
					// System.out.println(">>>>>>>"+line);
					tt = new TongueTwister();
					tt.setTitle(line);
					tt.setId(id++);;
				} else {
					// System.out.println(line);
					tt.setContent(line);
					list.add(tt);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static boolean startsWithNumber(String line) {
		for (int i = 1; i < 10; i++) {
			if (line.startsWith(i + "")) {
				return true;
			}
		}
		return false;
	}
}
