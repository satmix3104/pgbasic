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

class Jsonfile {
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
		System.out.printf("番号　　　　名前　　　クラス　　年齢　　　 点数" + "\n");

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

			switch (num) {

			case 0:

				if (num2 == 0) {

					Collections.sort(student, new Comparator<Student>() {
						@Override
						public int compare(Student p1, Student p2) {
							return p2.getNo().compareTo((p1.getNo()));
						}
					});
				} else {
					Collections.sort(student, new Comparator<Student>() {
						@Override
						public int compare(Student p1, Student p2) {
							return p2.getNo().compareTo((p1.getNo()));
						}
					});
				}
				break;

			case 1:

				if (num2 == 0) {
					Collections.sort(student, new Comparator<Student>() {
						@Override
						public int compare(Student p1, Student p2) {
							return p1.getKurasu().compareTo((p2.getKurasu()));
						}
					});
				} else {
					Collections.sort(student, new Comparator<Student>() {
						public int compare(Student p1, Student p2) {
							return p2.getKurasu().compareTo((p1.getKurasu()));
						}
					});
				}
				break;

			case 2:

				if (num2 == 0) {
					Collections.sort(student, (p1, p2) -> p1.getAge() - p2.getAge());
				} else {
					Collections.sort(student, (p1, p2) -> p2.getAge() - p1.getAge());
				}
				break;

			case 3:

				if (num2 == 0) {
					Collections.sort(student, (p1, p2) -> p1.getVal() - p2.getVal());
				} else {
					Collections.sort(student, (p1, p2) -> p2.getVal() - p1.getVal());
				}
				break;

			default:
				break;
			}

			for (int i = 0; i < student.size(); i++) {
				System.out.printf("%s  %-8s  %s      %2d歳      %3d点\n", student.get(i).no,
						String.format("%-8s", student.get(i).name).replace(" ", " "), student.get(i).kurasu,
						student.get(i).age, student.get(i).val);

			}
			Jsonfile result = new Jsonfile();
			result.setDatas(student);
			var printer = new DefaultPrettyPrinter();
			printer.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
			mapper.writer(printer).writeValue(new File("./src/main/resources/file/result.json"), result);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
