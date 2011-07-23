## Compiling the code
1. Change to base dir.  
`cd decider/src/main/java`

2. Compile the necessary code files.  
    a. If you wish to merely run the code (as opposed to debug it with `jdb`), run the following command:  
    `javac com/decider/core/*.java com/decider/core/types/*.java com/decider/cli/*.java`
    b. If you wish to debug the code with `jdb`, run the following command:  
    `javac -g com/decider/core/*.java com/decider/core/types/*.java com/decider/cli/*.java`

3. Package the core classes into a jar file.  
`jar cvf decider-core.jar com/decider/core/*.class com/decider/core/types/*.class`

## Running the compiled program
1. Run the program with the following command:  
`java -cp decider-core.jar:. com.decider.cli.Decider`

## Debugging the compiled program
1. In one window run the program with the following command:  
`java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=4571 -cp decider-core.jar:. com.decider.cli.Decider`

2. In another window run the debugger with the following command:  
`jdb -connect com.sun.jdi.SocketAttach:hostname=localhost,port=4571`
