package JsonSort.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * ソート対象のJsonファイルを、ローカルサーバかローカルフォルダから取得するクラス
 * 
 * @author Harada
 * @version 1.0
 */
public class JsonLoadUtil {

	// tomcatのローカルサーバからファイルを取得する時のURI
	final static String uri = "http://localhost:8080/test.json";
	// Eclipseのローカルフォルダからファイルを取得する時のパス
	final static String Path = "JsonSort/src/main/resources/file/test.json";

	/**
	 * Jsonファイルをローカルサーバから探してきて、読み込んだ文字列を返す
	 * 
	 * @return response.body() ローカルサーバから取得したJson文字列
	 * getLocalFile() ローカルフォルダから取得したJson文字列(サーバ接続に失敗した時に返す)
	 */
	public static String getJsonFile() throws InterruptedException, FileNotFoundException, IOException {

		try {
			// HttpClientを生成
			HttpClient httpClient = HttpClient.newBuilder().build();
			// HttpRequestを生成
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
			// リクエストを送信
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			// HttpStatusを確認して分岐する
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				System.out.println("サーバからのJsonデータ取得に失敗しました\nローカルからデータを取得します");
				return getLocalFile();
			}
		} catch (ConnectException e) {
			System.out.println("サーバへの接続に失敗しました\nローカルからデータを取得します");
			return getLocalFile();
		}

	}

	/**
	 * Jsonファイルをローカルフォルダから探してきて、読み込んだ文字列を返す
	 * 
	 * @return getLocalFile() ローカルフォルダから取得したJson文字列
	 */
	public static String getLocalFile() throws FileNotFoundException, IOException {
		// Jsonファイルを読み込む
		File file = new File(Path);
		// 中身をStringに書き出す
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String temp = br.readLine();
			String datas = "";
			while (temp != null) {
				datas += temp;
				temp = br.readLine();
			}
			return datas;
		}
	}

}
