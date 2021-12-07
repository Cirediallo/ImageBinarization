import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Mamadou
 *
 */
public class Main {
	private static Graph r; // Our transportation network

	private static void constructionReseau(String inputFileName) throws IOException {
		int n, m;
		try (Scanner scanner = new Scanner(Files.newInputStream(new File(inputFileName).toPath()))) {
			n = scanner.nextInt(); // Height
			m = scanner.nextInt(); // Width
		}
	}

	/**
	 * @param args Nothing
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		System.out.print("Enter file name: ");
		Reader isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		String inputFileName = br.readLine();
		constructionReseau(inputFileName);
	}
}
