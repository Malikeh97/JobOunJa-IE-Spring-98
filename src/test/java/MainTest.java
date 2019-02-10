import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MainTest {

	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;

	private String filePath;

	@Parameters(name = "{0}")
	public static Collection files() {
		List<String> files = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			files.add("sample" + i + ".input");
		}
		return files;
	}

	public MainTest(String filePath) {
		this.filePath = filePath;
	}

	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		System.setIn(systemIn);
		System.setOut(systemOut);
	}

	@Test
	public void test() throws IOException {
		Object[] testObject = readFile(filePath);
		setInput(testObject[0].toString());
		Main.main(new String[0]);

		assertEquals("differ", testObject[1], outContent.toString());
	}

	private Object[] readFile(String path) throws FileNotFoundException {
		StringBuilder data = new StringBuilder();
		String expectedResult = "";
		File file = new File("src/test/resources/" + path);
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String next = scanner.nextLine();
			if (next.equals("-"))
				expectedResult = scanner.nextLine() + "\n";
			else
				data.append(next).append("\n");
		}

		return new Object[]{data, expectedResult};
	}

	private void setInput(String data) {
		ByteArrayInputStream inContent = new ByteArrayInputStream(data.getBytes());
		System.setIn(inContent);
	}
}