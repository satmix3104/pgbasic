package jp.co.wiss1.jsonsort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class Student {
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

class jsonfile {
	List<Student> datas;

	public List<Student> getDatas() {
		return datas;
	}

	public void setDatas(List<Student> member) {
		datas = member;
	}
}

public class Jackson {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.printf("並べ替えたい項目はなんですか？" + "\n" + "[0:番号 1:クラス 2:年齢 3:点数]:");
		int num = sc.nextInt();
		if (num < 0 || 3 < num) {
			System.out.printf("入力情報が不正です。");
			sc.close();
			return;
		}
		System.out.printf("並替種別を指定してください" + "\n" + "[0:昇順 1:降順]:");
		int num2 = sc.nextInt();
		if (num2 < 0 || 1 < num2) {
			System.out.printf("入力情報が不正です。");
			sc.close();
			return;
		}

		sc.close();

		List<Student> student = new ArrayList<Student>();
		System.out.printf("番号　　　　名前　　　　クラス　　　　年齢　　　　点数" + "\n");

		try {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File("./src/main/resources/file/test.json"));

			for (JsonNode n : root.get("datas")) {
				Student member = new Student();
				member.no = n.get("no").asText();
				member.name = n.get("name").asText();
				member.kurasu = n.get("kurasu").asText();
				member.age = n.get("age").asInt();
				member.val = n.get("val").asInt();
				student.add(member);
			}

			for (int i = 0; i < student.size(); i++) {
				System.out.printf(student.get(i).no, student.get(i).kurasu, student.get(i).age, student.get(i).val);

			}
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
