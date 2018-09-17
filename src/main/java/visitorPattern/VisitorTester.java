
package visitorPattern;


import java.io.*;

public class VisitorTester {

	public static void main(String[] args) {
		DirectoryNode node  = new DirectoryNode(new File("C:\\Users\\Yanis\\Desktop\\BackUp"));
		node.accept(new PrintVisitor());
	}

}
