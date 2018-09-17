/**
 * File Node.
 * @author Yanis
 *
 */

package visitorPattern;

import java.io.*;

public class FileNode implements FileSystemNode {

	public FileNode(File pFile) 
	{
		this.file = pFile;
	}
	
	public File getFile()
	{
		return file;
	}
	
	public void accept(FileSystemVisitor v)
	{
		v.visitFileNode(this);
	}
	
	private File file;

}
