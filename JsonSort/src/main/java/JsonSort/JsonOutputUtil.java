package JsonSort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * ソートした結果をコンソールに出力し、jsonファイルをダウンロードするクラス
 * 
 * @author Harada
 * @version 1.1
 */
public class JsonOutputUtil {

	/**
	 * コンソールに結果のログを出力する
	 * 
	 * @param json     test.jsonの入ったJsonNode
	 * @param sortList ソートしたい項目を並べ替えた配列
	 * @param item     ソートしたい項目
	 * @param order    ソートする順序
	 */
	public static void output(JsonNode json, ArrayList<String> sortList, final String item, final int order) {

		// jsonを出力する準備
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode result = mapper.createObjectNode();
		ArrayNode temp = mapper.createArrayNode();

		// 表の頭
		System.out.println("\n番号  名前            クラス  年齢   点数");
		// 表の中身
		for (int i = 0; i < sortList.size(); i++) { // sortListのi番目の値と一致するjsonを探す
			for (JsonNode j : json.get("datas")) { // 拡張forでjsonを1つずつ調べる

				if (sortList.get(i).equals(j.get(item).asText())) { // JsonNode j の持つ値がsortListの値と一致しているかを確認

					// jsonをprint
					System.out.printf("%4s  ", j.get("no").asText());
					System.out.print(format(j.get("name").asText(), 16));
					// for (int na = 8 - (j.get("name").asText().length()); na > 0; na--) {
					// System.out.print("  ");
					// }
					System.out.printf("%-7s", j.get("kurasu").asText());
					System.out.printf("%2s歳  ", j.get("age").asText());
					System.out.printf("%3s点%n", j.get("val").asText());

					temp.add(j); // 並び変えたjsonオブジェクトを作る
					break;
				}

			}
		}
		result.set("datas", temp); // 並び変えたjsonオブジェクトをdatasの配列にする
		outputFile(result, item, order); // jsonファイルの出力へ
	}

	/**
	 * nameのバイト長を考慮して列を揃えるためのメソッド
	 * 
	 * @param target nameの文字列
	 * @param length 半角何文字分まで埋めるか指定した数値
	 * @return 文末が調整されたname
	 */
	private static String format(String target, int length) {
		int byteDiff = (getByteLength(target, Charset.forName("UTF-8")) - target.length()) / 2;
		return String.format("%-" + (length - byteDiff) + "s", target);
	}

	/**
	 * nameのバイト長を計算するメソッド
	 * 
	 * @param string  nameの文字列
	 * @param charset UTF-8
	 * @return stringのバイト長
	 */
	private static int getByteLength(String string, Charset charset) {
		return string.getBytes(charset).length;
	}

	/**
	 * 受け取ったObjectNodeをファイル出力する
	 * 
	 * @param result ソートされたtest.jsonの入ったObjectNode
	 * @param item   ソートしたい項目
	 * @param order  ソートする順序
	 */
	private static void outputFile(ObjectNode result, final String item, final int order) {

		// result.jsonの名前とパスを生成
		// ファイル名にソートした項目と順序、作成した日付を追記する機能追加
		String orderstr = ""; // 順序
		if (order == 0) {
			orderstr = "asc";
		} else if (order == 1) {
			orderstr = "desc";
		} else {
			orderstr = "err";
		}

		Calendar cl = Calendar.getInstance(); // 日付
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		String date = String.valueOf(sdf.format(cl.getTime()));

		String name = ("result_" + item + "_" + orderstr + "_" + date + ".json");
		// 追加機能ここまで
		// String name = "result.json";
		String path = ("..\\..\\Downloads\\" + name);
		// System.out.println(System.getProperty("user.dir"));

		// ファイルを作成
		File jsonFile = new File(path);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));) {
			// resultをstringに変換
			ObjectMapper mapper = new ObjectMapper();
			String strRes = mapper.writeValueAsString(result);
			writer.write(strRes); // resultをjsonにファイルに記入

			// 正常にjsonを出力した報告
			System.out.println("\n" + name + "を、/Downloads に出力しました");

			// キャッチ
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
