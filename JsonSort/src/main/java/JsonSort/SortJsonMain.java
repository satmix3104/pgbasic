package JsonSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * test.jsonをJavaでソートするプログラム
 * 
 * @author Harada
 * @version 1.0
 */
public class SortJsonMain {

	/**
	 * test.jsonをJavaでソートするプログラムのメインクラス
	 */
	public static void main(String[] args) {

		// itemとorderにアクセスする準備
		SortVariable sv = new SortVariable();
		Scanner scanner = new Scanner(System.in);

		// 1回目の入力ループ
		while (true) {
			try {
				// ソートする項目の選択
				System.out.println("並べ替えたい項目は何ですか？　(helpでアプリの説明を表示、endでアプリを終了します)\n0:番号　1:クラス　2:年齢　3:点数");
				// endではないなら文字列を受け取る
				String scanT = endCheck(scanner.next());

				// ヘルプの表示
				if ("help".equals(scanT)) {
					help();
					continue;
				}

				// 入力された番号をenumに渡してからdatasの項目名に復号
				ItemEnum input = ItemEnum.getByScanE(scanT);
				// キャッチされなければ脱出
				sv.setItem(input.getItemE());
				break;

				// キャッチとエラー表示
			} catch (NullPointerException e) {
				error();
				continue;
			}
		}

		// 2回目の入力ループ
		while (true) {
			try {
				// ソート順の選択
				System.out.println("並替種別を選択してください　(endでアプリを終了します)\n0:昇順　1:降順");
				// endではないなら文字列を受け取る
				String scanU = endCheck(scanner.next());

				sv.setOrder(scanU);
				// キャッチされなければ脱出
				break;

				// キャッチとエラー表示
			} catch (NumberFormatException e) {
				error();
				continue;
			}
		}
		scanner.close();

		// ソートを実行する準備
		ArrayList<String> sortList = new ArrayList<String>();
		SortItemUtil si = new SortItemUtil();

		try {
			// ファイル読み込み
			String path = "JsonSort/src/main/resources/file/test.json";
			// System.out.println(System.getProperty("user.dir"));
			File file = new File(path);

			// json登場!
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json = mapper.readTree(file);

			// ソートしたい項目を引き抜いたリストを用意する
			sortList = si.preSort(json, sortList, sv.getItem(), sv.getOrder());

			// ソートしたリストを元にして並べ替えたjsonを出力
			JsonOutputUtil.output(json, sortList, sv.getItem(), sv.getOrder());

			// キャッチ
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * 入力が"end"ならメッセージを出力して終了させる
	 * 
	 * @param scan scanTに入力された文字列
	 * @return scan scanTに入力された文字列（endでは無かったならそのまま返す）
	 */
	private static String endCheck(String scan) {
		if ("end".equals(scan)) {
			System.out.print("アプリを終了します。お疲れさまでした。");
			// 強制終了
			System.exit(0);
		}
		// endではないなら処理続行
		return scan;
	}

	/**
	 * 入力が"help"ならメッセージを出力する
	 */
	private static void help() {
		System.out.println("このアプリは、JSON形式のファイルの情報を、入力キーを元に並べ替えて結果を表示します。\n");
		return;
	}

	/**
	 * 入力が不正な値ならメッセージを出力する
	 */
	private static void error() {
		System.out.println("入力情報が不正です。\n");
		return;
	}

}