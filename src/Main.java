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
		Integer n, m, s, t;
		try (Scanner scanner = new Scanner(Files.newInputStream(new File(inputFileName).toPath()))) {
			n = scanner.nextInt(); // Height
			m = scanner.nextInt(); // Width
			int length = n * m; // Number of pixels

			r = new Graph(length + 2);
			// Each pixel (i, j) has number i·m + j from 0 to n·m.

			s = n * m; // Source node
			t = n * m + 1; // Sink node

			// aᵢ
			for (int i = 0; i < length; i++) {
				r.addEdge(s, i, scanner.nextInt());
			}

			// bᵢ
			for (int i = 0; i < length; i++) {
				r.addEdge(i, t, scanner.nextInt());
			}

			// p horizontally
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m - 1; j++) {
					int c = scanner.nextInt();
					int lNode = i * m + j;
					int rNode = lNode + 1;
					r.addEdge(lNode, rNode, c);
					r.addEdge(rNode, lNode, c);
				}

			// p vertically
			for (int i = 0; i < n - 1; i++)
				for (int j = 0; j < m; j++) {
					int c = scanner.nextInt();
					int lNode = i * m + j;
					int rNode = lNode + m;
					r.addEdge(lNode, rNode, c);
					r.addEdge(rNode, lNode, c);
					System.out.println(c);
				}
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
