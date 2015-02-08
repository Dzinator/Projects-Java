
public class PrintVisitor implements FileSystemVisitor {

	@Override
	public void visitFileNode(FileNode node) {
		for(int i = 0; i < level; i++)
		{
			System.out.print("	");
		}
		System.out.println(node.getFile().getName());

	}

	@Override
	public void visitDirectoryNode(DirectoryNode node) {
		for(int i = 0; i < level; i++)
		{
			System.out.print("	");
		}
		System.out.println(node.getDirectory().getName());
		level++;
		for(FileSystemNode c : node.getChildren())
			c.accept(this);
		level--;

	}

	
		private int level = 0;
}
