## counting code lines

basic description [here](http://codekata.com/kata/kata13-counting-code-lines)

- it expects folder/file name as a first parameter
- files filtered by pattern '*.java' 
- it counts code lines from the files
- it behaves correctly with:
  - line comments: `// this is comment`
  - block comments: `/* block */`
  - any nesting levels: `/* /* block */`
  - strings: `"abc/*"`, `"abc//"`
  - string escape characters: `"a\\"bc"`
  - unclosed string literals treated as closed 

### different counter/stripper implementation support

- counter - 3 implementations in `org.alexr.kata13.count`
- strip - 6 implementations in `org.alexr.kata13.strip`

### configuration

- all configuration in the file `Configuration.java`
- counter and stripper instances can be provided in the method `counter()`
- file filter - in the method `fileFilter()`

### implementation

based on
- proper syntax analysis
- FSM
- Immutable state management
- FP fold techniques

it actually not just count,
but strip comments from the source code and counts afterwards
  
### technical implementation details

- it uses stream processing `Stream<String> Files.lines(path)` from `java.nio`
- it doesn't load whole file in the memory
 
### state handled

- code -> string
- string -> code
- code -> block comment
- clock comment -> code
- code -> line comment
- line comment -> code

### details

- string state is handling **inside one code line**
- line comment state is handling **from current position to the enf of the line**
- block comment state **is being passed between line process iteration** 

#### to build:

`mvn clean package`

#### to run:

`mvn clean package exec:java`

#### to clean-up:

`mvn clean`

#### to do experiments:

just put file you are interested in into `resources` folder
