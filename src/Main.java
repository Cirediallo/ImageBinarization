import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Mamadou
 *
 */
public class Main {
	private static Network constructionReseau(String inputFileName) throws IOException {
		Integer n, m;
		Network r;
		try (Scanner scanner = new Scanner(Files.newInputStream(new File(inputFileName).toPath()))) {
			n = scanner.nextInt(); // Height
			m = scanner.nextInt(); // Width
			int length = n * m; // Number of pixels

			r = new Network(length);
			// Each pixel (i, j) has number i·m + j from 0 to n·m.

			// aᵢ
			for (int i = 0; i < length; i++) {
				r.addEdge(r.source(), i, scanner.nextInt());
			}

			// bᵢ
			for (int i = 0; i < length; i++) {
				r.addEdge(i, r.sink(), scanner.nextInt());
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
					int tNode = i * m + j;
					int bNode = tNode + m;
					r.addEdge(tNode, bNode, c);
					r.addEdge(bNode, tNode, c);
				}
		}
		return r;
	}

	private static int calculFlotMax(Network r) {
		int maxFlow = 0;
		Network residualNetwork = new Network(r);
		ArrayList<Edge> simplePath;
		while (true) {
			// Find a path from source to sink...
			simplePath = residualNetwork.path();
			if (simplePath == null)
				break;
			// ...stop if there is no path anymore

			// Find minimal capacity as capacity of this path
			Integer pathCapacity = Integer.MAX_VALUE;
			for (Edge e : simplePath)
				if (e.capacity() < pathCapacity)
					pathCapacity = e.capacity();

			// Now we go through this path.
			Integer currentNode = residualNetwork.source();
			for (Edge e : simplePath) {
				System.out.print(currentNode + " ");

				// 1. Updating the residual network
				if (e.capacity() == pathCapacity) {
					residualNetwork.removeEdge(currentNode, e.destination());
				} else
					e.capacity(e.capacity() - pathCapacity);

				Edge reverseEdge = residualNetwork.getEdge(e.destination(), currentNode);
				if (reverseEdge == null)
					residualNetwork.addEdge(e.destination(), currentNode, pathCapacity);
				else
					reverseEdge.capacity(reverseEdge.capacity() + pathCapacity);

				// 2. Updating the actual flow
				Edge actualEdge = r.getEdge(currentNode, e.destination());
				if (actualEdge == null) {
					actualEdge = r.getEdge(e.destination(), currentNode);
					actualEdge.flow(actualEdge.flow() - pathCapacity);
				} else {
					actualEdge.flow(actualEdge.flow() + pathCapacity);
				}

				// Updating the current node before moving forward
				currentNode = e.destination();
			}
			System.out.println();
		}

		for (Edge e : r.edges().get(r.source()))
			maxFlow += e.flow();

		return maxFlow;
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
		Network r = constructionReseau(inputFileName);
		int maxFlow = calculFlotMax(r);
		System.out.print(maxFlow);
	}
}
