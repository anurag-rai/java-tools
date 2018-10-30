# java-tools

mvn package

jar will be created in /target/

cp *.jar /tmp/

cd /tmp

java -cp cmds-1.0-SNAPSHOT.jar com.anurag.cmds.FileCreator

A file named test will be created

java -cp cmds-1.0-SNAPSHOT.jar com.anurag.cmds.Tail test 11




Learnings:

Implementation:
https://www.geeksforgeeks.org/implement-your-own-tail-read-last-n-lines-of-a-huge-file/
https://codereview.stackexchange.com/questions/79039/get-the-tail-of-a-file-the-last-10-lines


- byte is unsigned 8 bits
- byte to char conversion
- randomaccessfile https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#writeBytes-java.lang.String-

- generate random numbers
- throw vs throws
- Create object only if valid arguments (Proper code structure)
- Check runtime of a method
- maven configuration ( 5 minute maven setup) 
	- https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
	- http://www.mkyong.com/maven/how-to-create-a-java-project-with-maven/
- running a jar file