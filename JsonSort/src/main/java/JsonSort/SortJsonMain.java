package JsonSort;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import JsonSort.Entity.ItemEnum;
import JsonSort.Entity.OrderEnum;
import JsonSort.Entity.SortVariable;
import JsonSort.Util.JsonDaoUtil;
import JsonSort.Util.JsonLoadUtil;
import JsonSort.Util.JsonOutputUtil;
import JsonSort.Util.SortItemUtil;

/**
 * test.jsonをJavaでソートするプログラム
 * 
 * @author Harada
 * @version 1.5
 */
public class SortJsonMain {

	// ソートする項目と順番を保持
	static SortVariable sv = new SortVariable();
	// 入力してほしい内容の説明
	final static String first = ("実施する機能を選択してください　(helpでアプリの説明を表示、qでアプリを終了します)\n0:並べ替え　1:履歴参照");
	final static String second = ("並べ替えたい項目を選択してください　(qでアプリを終了します)\n0:番号　1:クラス　2:年齢　3:点数");
	final static String third = ("並べ替える順番を選択してください　(qでアプリを終了します)\n0:昇順　1:降順");

	/**
	 * test.jsonをJavaでソートするプログラムのメインクラス
	 * 
	 * @throws NumberFormatException    count=1の時に入力された値が0か1以外だった場合の例外
	 * @throws IllegalArgumentException 入力された値を持つEnumが存在しなかった場合の例外
	 * @throws InterruptedException     JsonLoadUtil.getJsonFileのhttpClient.sendの例外
	 * @throws FileNotFoundException    JsonLoadUtil.getLocalFileで指定したjsonファイルが見つからなかった場合の例外
	 * @throws JsonParseException       jsonファイルの読み込みに失敗した場合の例外
	 * @throws JsonMappingException     jsonファイルの読み込みに失敗した場合の例外
	 * @throws IOException              jsonファイルの読み込みに失敗した場合の例外
	 * @throws SQLException             SQL接続に失敗した場合の例外
	 */
	public static void main(String[] args) {

		// ループ内の処理を制御
		int count = 1;
		// 入力前に表示する説明
		String info = first;

		try (Scanner scanner = new Scanner(System.in)) {
			// 入力ループ
			while (true) {
				try {
					// 操作の説明と入力
					System.out.println(info);
					String scan = scanner.next();
					// 入力チェック
					if (count == 1) {
						checkHelpMessage(scan);
					}
					checkQuitMessage(scan);

					if (count == 1) {
						if (Integer.parseInt(scan) == 0) {
							// キャッチされなければ次へ
							count++;
							info = second;
						} else if (Integer.parseInt(scan) == 1) {
							// DBを表示する
							JsonDaoUtil.showRecord();
						} else {
							// 0か1以外の値だったら
							throw new NumberFormatException();
						}
					}

					if (count == 2) {
						setScanItem(scan);
						// キャッチされなければ次へ
						count++;
						info = third;
					}

					if (count == 3) {
						setScanOrder(scan);
						// キャッチされなければループ終了
						break;
					}

					// キャッチしたらループ
				} catch (NumberFormatException e) {
					System.out.println("入力情報が不正です。\n");
					continue;
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}

		try {
			// jsonファイルを取得
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json;
			try {
				json = mapper.readTree(JsonLoadUtil.getJsonFile());
			} catch (ConnectException e) {
				System.out.println("サーバからのJsonデータ取得に失敗しました\nローカルからデータを取得します");
				json = mapper.readTree(JsonLoadUtil.getLocalFile());
			}

			// ソートしたい項目を引き抜いたリストを用意する
			ArrayList<String> sortList = SortItemUtil.preSort(json, sv);

			// ソートしたリストを元にして並べ替えたjsonを出力
			JsonOutputUtil.output(json, sortList, sv);

			// ソートした条件をDBに登録する
			JsonDaoUtil.insertRecord(sv);

		} catch (InterruptedException e) {
			System.out.println(e);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (JsonParseException e) {
			System.out.println(e);
		} catch (JsonMappingException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 入力が"help"ならメッセージを出力する
	 * 
	 * @param scan scannerに入力された文字列
	 * @throws IllegalArgumentException 後続処理を実行させないための形式的な例外
	 */
	private static void checkHelpMessage(String scan) {
		if ("help".equalsIgnoreCase(scan)) {
			System.out.println("このアプリは、JSON形式のファイルの情報を、入力キーを元に並べ替えて結果を表示します。\nこれまでに並べ替えた履歴を、参照することも可能です。\n");
			// continueしたいので例外を呼ぶ
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 入力が"q"ならメッセージを出力して終了させる
	 * 
	 * @param scan scannerに入力された文字列
	 */
	private static void checkQuitMessage(String scan) {
		if ("q".equalsIgnoreCase(scan)) {
			System.out.println("アプリを終了します。お疲れさまでした。");
			// 強制終了
			System.exit(0);
		}
	}

	/**
	 * 入力された番号をenumに渡して項目名を取得する
	 * 
	 * @param scan scannerに入力された文字列
	 */
	private static void setScanItem(String scan) {
		ItemEnum ie = ItemEnum.getByItemNo(scan);
		sv.setItem(ie.getItem());
		sv.setItemName(ie.getItemName());
		sv.setItemNo(ie.getItemNo());
	}

	/**
	 * 入力された番号をenumに渡してソート順を取得する
	 * 
	 * @param scan scannerに入力された文字列
	 */
	private static void setScanOrder(String scan) {
		OrderEnum oe = OrderEnum.getByOrderNo(scan);
		sv.setOrder(oe.getOrder());
		sv.setOrderName(oe.getOrderName());
		sv.setOrderNo(oe.getOrderNo());
	}

}