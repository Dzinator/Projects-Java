/**
 * The common interface for file and directory nodes.
 * @author Yanis
 *
 */

public interface FileSystemNode {
	
	void accept(FileSystemVisitor v);

}
