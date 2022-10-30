package jp.co.wiss1.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Jsonoutput {

	
	public static void output(JsonNode json, ArrayList<String> sortList, final String item, final int order) {
		// jsonを出力する準備
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode result = mapper.createObjectNode();
		ArrayNode temp = mapper.createArrayNode();

		// 表の頭
		System.out.println("\n番号　名前　　　　　　クラス　年齢　点数");
		System.out.println("\n番号  名前            クラス  年齢   点数");
		// 表の中身
		for (int i = 0; i < sortList.size(); i++) { // sortListのi番目の値と一致するjsonを探す
			for (JsonNode j : json.get("datas")) { // 拡張forでjsonを1つずつ調べる
				if (sortList.get(i).equals(j.get(item).asText())) { // JsonNode j の持つ値がsortListの値と一致しているかを確認
					// jsonをprint
					System.out.printf("%4s  ", j.get("no").asText());
					System.out.print(format(j.get("name").asText(), 16));
					// for (int na = 8 - (j.get("name").asText().length()); na > 0; na--) {
					// System.out.print(" ");
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
	
	private static String format(String target, int length) {
		int byteDiff = (getByteLength(target, Charset.forName("UTF-8")) - target.length()) / 2;
		return String.format("%-" + (length - byteDiff) + "s", target);
	}
	
	private static int getByteLength(String string, Charset charset) {
		return string.getBytes(charset).length;
	}
	
	private static void outputFile(ObjectNode result, final String item, final int order) {
		// result.jsonの名前とパスを生成
		// ファイル名にソートした項目と順序、作成した日付を追記する機能追加
		
		String name = "result.json";
		String path = ("C:\\Users\\ppse0\\Documents\\" + name);
		//System.out.println(System.getProperty("user.dir"));
		// ファイルを作成
		File jsonFile = new File(path);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));) {
			// resultをstringに変換
			ObjectMapper mapper = new ObjectMapper();
			String strRes = mapper.writeValueAsString(result);
			writer.write(strRes); // resultをjsonにファイルに記入

			// 正常にjsonを出力した報告
			System.out.println("\n" + name + "を、/Documents に出力しました");

			// キャッチ
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}