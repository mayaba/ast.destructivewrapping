package ca.concordia.soen6591.assignment2.utility;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileWalker {
	private String dirPath;
	
	//"C:\\Users\\Raheem\\Downloads\\guava-master\\"
	
	public FileWalker(String dirPath) {
		this.dirPath = dirPath;
	}

	public List<Path> filewalk() throws IOException {
		Path path = Paths.get(dirPath);
		List<Path> paths = findByFileExtension(path, ".java");
		return paths;
		
	}

	private List<Path> findByFileExtension(Path path, String fileExtension) throws IOException {

		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Path must be a directory!");
		}

		List<Path> result;
		try (Stream<Path> walk = Files.walk(path)) {
			result = walk.filter(Files::isRegularFile) // is a file
					.filter(p -> p.getFileName().toString().endsWith(fileExtension)).collect(Collectors.toList());
		}
		return result;

	}

}
