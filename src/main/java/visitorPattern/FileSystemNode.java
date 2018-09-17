/**
 * The common interface for file and directory nodes.
 * @author Yanis
 *
 */

package visitorPattern;

public interface FileSystemNode {
	
	void accept(FileSystemVisitor v);

}
