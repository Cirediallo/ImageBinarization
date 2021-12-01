import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 
 */

/**
 * @author Mamadou
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String inputFile = null;
		BufferedReader br = null;
		System.out.println("Enter file name: ");
		try {
			Reader isr = new InputStreamReader(System.in);
			br = new BufferedReader(isr);
			
			inputFile = br.readLine();
			if(inputFile.length() == 0) {
				throw new IOException("No file name have been entered");
			}
			System.out.print("Length: "+inputFile.length());
			
		}catch(IOException e) {
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: "+e.getMessage());
				e.printStackTrace();
			}
			
		}

	}

}
