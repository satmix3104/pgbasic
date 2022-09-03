package JsonSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Task3 {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("並べ替えたい項目は何ですか？　(helpでアプリの説明を表示します)\n0:番号　1:クラス　2:年齢　3:点数");
		String scan  = scanner.next();	//入力待ち
		
		if("help".equals(scan)){
			System.out.println("このアプリは、JSON形式のファイルの情報を、入力キーを元に並べ替えて結果を表示します。");
			scanner.close();
			return;
		}
		
		String cant = "入力情報が不正です";	//エラーメッセージ
		int route = 1;	//tocaseの分岐に使う
		int type = tocase(scan,route);
		if(type == 9) {
			System.out.println(cant);
			scanner.close();
			return;
		}
		
		System.out.println("並替種別を選択してください\n0:昇順　1:降順");
		scan = scanner.next();	//入力待ち
		scanner.close();
		
		route = 2;	//tocaseの分岐に使う
		int up = tocase(scan,route);
		if(up == 9) {
			System.out.println(cant);
			return;
		}
		
		//Jsonファイル取得の共通部分
		String strFileName = "../pgbasic/JsonSort/src/main/resources/file/test.json";
		FileReader fr = null;
		BufferedReader br = null;
		
		try{
			// ファイル読み込み
			File file = new File(strFileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			// テキストを取得
			String strLine;
			StringBuilder sbSentence = new StringBuilder();
			while ((strLine = br.readLine()) != null) {
				sbSentence.append(strLine);
			}
			
			// JSONオブジェクトのインスタンス作成
			JSONObject jsonObj = new JSONObject(sbSentence.toString());
			// キー"datas"の値（JSON配列オブジェクト）をパース
			JSONArray items = jsonObj.getJSONArray("datas");
			//表の頭
			System.out.println("番号　名前　　　　　　クラス　年齢　点数");
			
			//種別でソート
			List<Integer> sortlist = new ArrayList<Integer>();
			sortlist = presort(items,type,sortlist);
			
			//JSON出力準備
			StringBuilder output = new StringBuilder();
			output.append("{");
			output.append("datas");
			output.append(":[");
			//昇降順でソート
			if(up == 0){
				for(int i = 0; i < items.length(); i++) {
					if(i != 0) {
						output.append(",");
					}
					output.append(lastsort(items,sortlist,i));
				}
			}else if(up == 1){
				for(int i = (items.length()-1); i >= 0; i--) {
					if(i != (items.length()-1)) {
						output.append(",");
					}
					output.append(lastsort(items,sortlist,i));
				}
			}
			output.append("]");
			output.append("}");
			
			//JSON出力
			upload(output);
			System.out.println("JSONファイルが出力されました");
			return;
			
			//キャッチ
			}catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (fr != null) {
						fr.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	//case文を隔離
	public static int tocase(String scan,int route) {
		int temp = 0;
		//並び変えたい項目
		if(route == 1) {
			switch(scan){
				case "0":
					temp = 0;
					break;
				case "1":
					temp = 1;
					break;
				case "2":
					temp = 2;
					break;
				case "3":
					temp = 3;
					break;
				default:
					temp = 9;
					break;
			}
		//並替種別
		}else if(route == 2) {
			switch(scan){
				case "0":
					temp = 0;
					break;
				case "1":
					temp = 1;
					break;
				default:
					temp = 9;
					break;
			}
		//クラスの値を数値に変換
		}else if(route == 3) {
			switch(scan){
				case "Ａ":
					temp = 1;
					break;
				case "Ｂ":
					temp = 2;
					break;
				case "Ｃ":
					temp = 3;
					break;
				case "Ｄ":
					temp = 4;
					break;
				case "Ｅ":
					temp = 5;
					break;
				case "Ｑ":
					temp = 6;
					break;
				case "Ｒ":
					temp = 7;
					break;
				case "Ｓ":
					temp = 8;
					break;
				case "Ｔ":
					temp = 9;
					break;
				case "Ｚ":
					temp = 10;
					break;
				default:
					temp = 99;
					break;
			}
		}
		return temp;
	}
	
	//種別でソートするための順番を取得する
	public static List<Integer> presort(JSONArray items,int type,List<Integer> sortlist) {
		
		//JSONから取得する値の種別で分岐して、値の配列を受け取る
		if(type == 0) {
			sortlist = nosort(items,sortlist);
		}else if(type == 1) {
			sortlist = kurasort(items,sortlist);
		}else if(type == 2) {
			String vag = "age";
			sortlist = vagsort(vag,items,sortlist);
		}else if(type == 3) {
			String vag = "val";
			sortlist = vagsort(vag,items,sortlist);
		}
		
		//元の並びをコピー
		List<Integer> originlist = new ArrayList<>(sortlist);
		//昇順ソート実行
		Collections.sort(sortlist);
		
		//ソートの順番を配列で渡す
		for(int i = 0; i < originlist.size(); i++) {
			//ソートで第i位になった値を取得
			int search = sortlist.get(i);
			//その値がオリジンリストの何番目にあるか取得
			int rank = originlist.indexOf(search);
			//ソートリストの値をjsonの何番目の配列にあるか示す値で上書き
			sortlist.set(i,rank);
		}
		return sortlist;
	}
	
	//点数順でソートしたリストを作る
	public static List<Integer> nosort(JSONArray items,List<Integer> sortlist) {
		
		for(int i = 0; i < items.length(); i++) {
			// JSONオブジェクトをパース
			JSONObject item = items.getJSONObject(i);
			//目的の値を取得
			String target = (String)(item.get("no"));
			//渡すリストに値を入れる
			sortlist.add(Integer.parseInt(target));
		}
		return sortlist;
	}
	
	//クラス順でソートしたリストを作る
	public static List<Integer> kurasort(JSONArray items,List<Integer> sortlist) {
		
		for(int i = 0; i < items.length(); i++) {
			// JSONオブジェクトをパース
			JSONObject item = items.getJSONObject(i);
			//目的の値を取得
			String scan = (String)(item.get("kurasu"));//tocaseに渡す
			int route = 3;	//tocaseの分岐に使う
			//アルファベットを数値に変換
			int target = tocase(scan,route);
			//渡すリストに値を入れる
			sortlist.add(target);
		}
		return sortlist;
	}
	
	//年齢・点数順でソートしたリストを作る
	public static List<Integer> vagsort(String vag,JSONArray items,List<Integer> sortlist) {
		
		for(int i = 0; i < items.length(); i++) {
			// JSONオブジェクトをパース
			JSONObject item = items.getJSONObject(i);
			//目的の値を取得
			int target = (Integer)(item.get(vag));
			//渡すリストに値を入れる
			sortlist.add(target);
		}
		return sortlist;
	}
	
	//表を出力する本体
	public static String lastsort(JSONArray items,List<Integer> sortlist,int i) {
		
		// JSONオブジェクトをパース
		JSONObject item = items.getJSONObject(sortlist.get(i));
		//表を作りやすいようにString化
		String no = (item.get("no").toString());
		String name = (item.get("name").toString());
		String kurasu = (item.get("kurasu").toString());
		String age = (item.get("age").toString());
		String val = (item.get("val").toString());
		//表を出力
		System.out.printf("%4s  ",no);
		System.out.print(name);
		for(int na = 8-(name.length()); na > 0; na--) {
			System.out.print("　");
		}
		System.out.printf("%-7s",kurasu);
		System.out.printf("%2s歳  ",age);
		System.out.printf("%3s点%n",val);
		//JSONファイル並び変え
		JSONObject tempput = new JSONObject();
		tempput.put("no",no);
		tempput.put("name",name);
		tempput.put("kurasu",kurasu);
		tempput.put("age",age);
		tempput.put("val",val);
		String temp = tempput.toString();
		
		return temp;
	}
	
	//JSONファイルを出力する
	public static void upload(StringBuilder output)throws IOException {
		File jsonFile = new File("C:/Users/RYO/Downloads/result.json");
		BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
		System.out.println(output.toString());
		writer.write(output.toString());
		writer.close();
	}
}
