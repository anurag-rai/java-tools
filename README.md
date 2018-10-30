# java-tools

The `Tail` class provides the barebone functionality of the Unix `tail` command.

## Installation

Download the source code.

Change directory to `/path/to/project/`.

Run `mvn package`. A jar will be created in `path/to/project/target/`.

The jar does not have a manifest file so the class needs to be specified.


## Usage

`java -cp <jarfile> com.anurag.cmds.Tail <filename> <integer>`

### Example 

We will be copying the jar to a temporary folder.

`cp /path/to/project/target/*.jar /tmp/`

`cd /tmp`

Create a file (default name `test`) in the directory.

`java -cp cmds-1.0-SNAPSHOT.jar com.anurag.cmds.FileCreator`

Run the `Tail` command in the created file.

`java -cp cmds-1.0-SNAPSHOT.jar com.anurag.cmds.Tail test 11`


## Learnings

### Implementation Guidelines:

- [GeeksForGeeks](https://www.geeksforgeeks.org/implement-your-own-tail-read-last-n-lines-of-a-huge-file/)
- [CodeReview](https://codereview.stackexchange.com/questions/79039/get-the-tail-of-a-file-the-last-10-lines)

### Caveats

- `byte` is unsigned 8 bits in Java
- `byte` to `char` conversion
- [RandomAccessFile](https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#writeBytes-java.lang.String-)
- Generate random numbers (`Random` vs `Math.Random`)
- `throw` vs `throws`
- Create object only if valid arguments (Proper code structure)
- Check runtime of a method (`Duration`, `Instance`)
- Maven configuration ( 5 minute maven setup) 
	- [Apache doc](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
	- [Mkyong](http://www.mkyong.com/maven/how-to-create-a-java-project-with-maven/)
- Running a jar file
