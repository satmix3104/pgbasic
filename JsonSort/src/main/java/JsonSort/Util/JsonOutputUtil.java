package JsonSort.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import JsonSort.Entity.SortVariable;

/**
 * ソートした結果をコンソールに出力し、jsonファイルをダウンロードするクラス
 * 
 * @author Harada
 * @version 1.2
 */
public class JsonOutputUtil {

	/**
	 * コンソールに結果のログを出力する
	 * 
	 * @param json     test.jsonの入ったJsonNode
	 * @param sortList ソートしたい項目を並べ替えた配列
	 * @param sv       ソートしたい項目と順序
	 */
	public static void output(JsonNode json, ArrayList<String> sortList, SortVariable sv) {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode result = mapper.createObjectNode();
		ArrayNode temp = mapper.createArrayNode();
		String item = sv.getItem();

		// 表の頭
		System.out.println("\n番号  名前            クラス  年齢   点数");
		// 表の中身
		for (int i = 0; i < sortList.size(); i++) { // sortListのi番目の値と一致するjsonを探す
			for (JsonNode j : json.get("datas")) { // 拡張forでjsonを1つずつ調べる

				if (sortList.get(i).equals(j.get(item).asText())) { // JsonNode j の持つ値がsortListの値と一致しているかを確認

					// jsonをprint
					System.out.printf("%4s  ", j.get("no").asText());
					System.out.print(FormatByteUtil.format(j.get("name").asText(), 16, true));
					System.out.printf("%-7s", j.get("kurasu").asText());
					System.out.printf("%2s歳  ", j.get("age").asText());
					System.out.printf("%3s点%n", j.get("val").asText());

					temp.add(j); // 並び変えたjsonオブジェクトを作る
					break;
				}
			}
		}
		result.set("datas", temp); // 並び変えたjsonオブジェクトをdatasの配列にする
		outputFile(result, sv); // jsonファイルの出力へ
	}

	/**
	 * 受け取ったObjectNodeをファイル出力する
	 * 
	 * @param result ソートされたtest.jsonの入ったObjectNode
	 * @param sv     ソートしたい項目と順序
	 * @throws IOException jsonファイルの書き込みに失敗した場合の例外
	 */
	private static void outputFile(ObjectNode result, SortVariable sv) {

		// result.jsonの名前とパスを生成
		// ファイル名にソートした項目と順序、作成した日付を追記する機能追加
		Calendar cl = Calendar.getInstance(); // 日付
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		String name = ("result_" + sv.getItem() + "_" + sv.getOrder() + "_" + String.valueOf(sdf.format(cl.getTime()))
				+ ".json");
		String path = ("..\\..\\Downloads\\" + name);

		// ファイルを作成
		File jsonFile = new File(path);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));) {
			// resultをstringに変換
			ObjectMapper mapper = new ObjectMapper();
			String strRes = mapper.writeValueAsString(result);
			writer.write(strRes); // resultをjsonにファイルに記入
			System.out.println("\n" + name + "を、/Downloads に出力しました");

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
