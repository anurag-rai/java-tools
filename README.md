# java-tools

The `Tail` class in this branch provides the barebone functionality of the Unix `tail -f` command and continues file read from the last active position. To store the last active position a file named "<filename>.record" is created to store the byte offset for the application.

## Installation

Download the source code.

Change directory to `/path/to/project/`.

Run `mvn package`. A jar will be created in `path/to/project/target/`.

The jar does not have a manifest file so the class needs to be specified.


## Usage

`java -cp <jarfile> com.anurag.cmds.Tail <filename>`

### Example 

We will be copying the jar to a temporary folder.

`cp /path/to/project/target/*.jar /tmp/`

`cd /tmp`

Create a file (default name `test`) in the directory.

`java -cp cmds-1.0-SNAPSHOT.jar com.anurag.cmds.FileCreator`

Run the `Tail` command in the created file.

`java -cp cmds-1.0-SNAPSHOT.jar com.anurag.cmds.Tail test`


## Learnings

### Implementation Guidelines:

- [Java Watch Service](https://docs.oracle.com/javase/tutorial/essential/io/notification.html)
- [Guide to WatchService](https://www.baeldung.com/java-nio2-watchservice)
- [Java File Handling](https://stackabuse.com/reading-and-writing-files-in-java/)

### Caveats

- Currently only supports files in the current directory where the jar is run
- Does not support Windows file directory structure
