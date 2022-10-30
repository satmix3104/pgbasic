package jp.co.wiss1.jsonsort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jsonソートアプリ
 * @author sakuma
 *
 */

/*
 * 生徒の情報クラス
 */
class Seito{
	String no;
	String name;
	String kurasu;
	Integer age;
	Integer val;

	public String getNo() {
		return no;
	}

	public String getName() {
		return name;
	}

	public String getKurasu() {
		return kurasu;
	}

	public int getAge() {
		return age;
	}

	public int getVal() {
		return val;
	}

}

/*
 * 出力するJsonファイルクラス
 */
class JsonFile{
	List<Seito> datas;

	public List<Seito> getDatas(){
		return datas;
	}

	public void setDatas(List<Seito> s) {
		datas = s;
	}
}

/*
 * ソート機能
 */
public class Kadai1 {
	public static void main(String[] args) {
		/*
		 * プログラムの引数がない場合、ソートを実行
		 * 引数がhelpならアプリケーションの説明を表示
		 * 引数がhelp以外なら終了
		 */
		if(args.length == 0) {	
		}
		else if(args[0].equals("help")) {
			System.out.print("アプリケーション「JsonSort」は、JSON形式のファイル内の情報を\n入力キーを元に並べ替えて結果を表示します。\n\n\n\n");
			return;
		}
		else {
			return;
		}

		/*
		 * 並び変えたい項目と昇順/降順どちらで並び変えたいか入力させる。
		 * 一回目に0～3,二回目に0～1以外の数値が入力された場合、強制終了する。
		 * 数値以外が入力された場合はjava.util.InputMismatchExceptionが表示される（エラーで強制終了）。
		 */
		Scanner scanner = new Scanner(System.in);
		System.out.printf("並び変えたい項目は何ですか？\n");
		System.out.printf("[0:番号 1:クラス 2:年齢 3:点数] > ");
		int item = scanner.nextInt();
		System.out.printf("\n");
		if(item < 0 || item > 3) {
			System.out.printf("入力情報が不正です。");
			scanner.close();
			return;
		}
		System.out.printf("並替種別を指定してください。\n");
		System.out.printf("[0:昇順 1:降順] > ");
		int sort = scanner.nextInt();
		if(sort < 0 || sort > 1) {
			System.out.printf("入力情報が不正です。");
			scanner.close();
			return;
		}
		scanner.close();
		/*
		 *JSONファイルから生徒の情報を読み込み、配列に格納する。
		 *読み込めなかった場合、後のcatch文で強制終了する。
		 */
		List<Seito> seito = new ArrayList<Seito>();
		System.out.printf("番号　名前　　　　　　　クラス　年齢　点数\n");
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File("./src/main/resources/file/test.json"));

			for (JsonNode n : root.get("datas")) {
				Seito s = new Seito();
				s.no = n.get("no").asText();
				s.name = String.format("%s",n.get("name").asText()).replace(" ", "　");
				s.kurasu = n.get("kurasu").asText();
				s.age = n.get("age").asInt();
				s.val = n.get("val").asInt();
				seito.add(s);
			}

			/*
			 * 取得した生徒の配列をソートする。
			 * 先ほど入力された数値によってソートする項目と並替種別が変化する。
			 */
			switch(item) {
			case 0:
				if(sort == 0) {
					Collections.sort(seito,new Comparator<Seito>() {
						@Override
						public int compare(Seito p1, Seito p2) {
							return p1.getNo().compareTo((p2.getNo()));
						}
					});
				}
				else {
					Collections.sort(seito,new Comparator<Seito>() {
						@Override
						public int compare(Seito p1, Seito p2) {
							return p2.getNo().compareTo((p1.getNo()));
						}
					});
				}
				break;
			case 1:
				if(sort == 0) {
					Collections.sort(seito,new Comparator<Seito>() {
						@Override
						public int compare(Seito p1, Seito p2) {
							return p1.getKurasu().compareTo((p2.getKurasu()));
						}
					});
				}
				else{
					Collections.sort(seito,new Comparator<Seito>() {
						public int compare(Seito p1, Seito p2) {
							return p2.getKurasu().compareTo((p1.getKurasu()));}
					}
							);
				}
				break;
			case 2:
				if(sort == 0) {
					Collections.sort(seito, (p1, p2) -> p1.getAge() - p2.getAge());
				}
				else {
					Collections.sort(seito, (p1, p2) -> p2.getAge() - p1.getAge());
				}
				break;
			case 3:
				if(sort == 0) {
					Collections.sort(seito, (p1, p2) -> p1.getVal() - p2.getVal());
				}
				else {
					Collections.sort(seito, (p1, p2) -> p2.getVal() - p1.getVal());
				}
				break;
			default:
				break;
			}
			/*
			 * ソートした配列の内容をコンソールに出力する。
			 */
			for(int i = 0;i < seito.size();i++) {
				System.out.printf("%s　%-8s　%s　　　%2d歳　 %3d点\n",seito.get(i).no,String.format("%-8s",seito.get(i).name).replace(" ", "　"),seito.get(i).kurasu,seito.get(i).age,seito.get(i).val);            	

			}


			/*
			 * ソートした配列の内容をJSONファイルに出力する。
			 */            
			JsonFile result = new JsonFile();
			result.setDatas(seito);
			var printer = new DefaultPrettyPrinter();
			printer.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
			mapper.writer(printer).writeValue(new File("./src/main/resources/file/result.json"),result);

			/*
			 * JSONファイルを読み込めなかった場合、エラーメッセージを表示し強制終了する。
			 */
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
