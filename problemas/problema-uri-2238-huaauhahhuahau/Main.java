import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static boolean isVogal(char c) {
		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(System.in));
		String rs = in.readLine();
		in.close();
		StringBuffer rsV = new StringBuffer(rs.length());
		for (int i = 0; i < rs.length(); i++) {
			if (isVogal(rs.charAt(i))) {
				rsV.append(rs.charAt(i));
			}
		}
		BufferedWriter out = new BufferedWriter(
				new OutputStreamWriter(System.out));
		if (rsV.toString().equals(rsV.reverse().toString())) {
			out.write('S');
		} else {
			out.write('N');
		}
		out.newLine();
		out.close();
	}

}
