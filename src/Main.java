import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
	static int n, // height
			m; // width

	private static Network constructionReseau(String inputFileName) throws IOException {
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

	/**
	 * Executes Ford-Fulkerson algorithm on an “empty” network `r`
	 * 
	 * @param r Network to fill. It must be empty.
	 * @return Maximum flow
	 */
	private static int calculFlotMax(Network r) {
		int maxFlow = 0;
		ArrayList<Edge> simplePath;
		while (true) {
			// Find a path from source to sink...
			simplePath = r.path();
			if (simplePath == null)
				break;
			// ...stop if there is no path anymore

			// Find minimal capacity as capacity of this path
			Integer pathCapacity = Integer.MAX_VALUE;
			for (Edge e : simplePath)
				if (e.capacity() - e.flow() < pathCapacity)
					pathCapacity = e.capacity() - e.flow();

			// Now we go through this path.
			Integer currentNode = r.source();
			for (Edge e : simplePath) {
				Edge currentEdge = r.getEdge(currentNode, e.destination()),
						reversedEdge = r.getEdge(e.destination(), currentNode);
				if (currentEdge == null) {
					reversedEdge.flow(reversedEdge.flow() - pathCapacity);
				} else if (reversedEdge == null) {
					currentEdge.flow(currentEdge.flow() + pathCapacity);
				} else if (reversedEdge.flow() >= pathCapacity) {
					reversedEdge.flow(reversedEdge.flow() - pathCapacity);
				} else {
					currentEdge.flow(currentEdge.flow() + pathCapacity - reversedEdge.flow());
					reversedEdge.flow(0);
				}

				// Updating the current node before moving forward
				currentNode = e.destination();
			}
		}

		for (Edge e : r.edges.get(r.source()))
			maxFlow += e.flow();

		return maxFlow;
	}

	/**
	 * Deepfirst research to find the set of nodes that are accessible
	 * from the one specified in the residual network associated to `n`.
	 * 
	 * @param n       The filled network
	 * @param set     A set to complete
	 * @param visited A functionnal array telling the visitedness of each node
	 * @param than    The node to search from
	 * @return A set of nodes accessible in the residual network
	 */
	private static HashSet<Integer> findSameSet(Network n, HashSet<Integer> set,
			Integer than) {
		set.add(than);
		for (Edge succession : n.edges.get(than)) {
			Integer successor = succession.destination();
			if (succession.flow() == succession.capacity())
				continue;
			if (!set.contains(successor)) {
				findSameSet(n, set, successor);
			}
		}
		return set;
	}

	/**
	 * Calculates a minimal cut in a _filled_ network `r`
	 * 
	 * @param r The filled network to cut
	 * @return A couple of sets of nodes
	 */
	private static List<Set<Integer>> calculCoupeMin(Network r) {
		Set<Integer> A = findSameSet(r, new HashSet<Integer>(), r.source());
		Set<Integer> B = new HashSet<Integer>(r.nodes);
		B.removeAll(A);
		return Arrays.asList(A, B);
	}

	private static List<Set<Integer>> résoudreBinIm(String inputFileName) throws IOException {
		Network r = constructionReseau(inputFileName);
		System.out.println("Network built");
		int maxFlow = calculFlotMax(r); // fills the associated network
		System.out.println("Maximal flow: " + maxFlow);
		List<Set<Integer>> cut = calculCoupeMin(r);
		cut.get(0).remove(r.source());
		cut.get(1).remove(r.sink());

		for (Integer pixel = 0; pixel < r.nodes.size() - 2; pixel++) {
			if (pixel % m == 0)
				System.out.println();
			if (cut.get(0).contains(pixel))
				System.out.print("A");
			else
				System.out.print("B");
		}

		return cut;
	}

	/**
	 * @param args Nothing
	 */
	public static void main(String[] args) throws IOException {
		// 1. Get file name
		String inputFileName;
		if (args.length != 0) {
			inputFileName = args[0];
		} else {
			BufferedReader br = null;
			System.out.print("Enter file name: ");
			Reader isr = new InputStreamReader(System.in);
			br = new BufferedReader(isr);
			inputFileName = br.readLine();
		}

		// 2. Answer the question
		résoudreBinIm(inputFileName);
	}
}
