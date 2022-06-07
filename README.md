# ast.destructivewrapping
Java static code Analyzer that takes advantage of the Abstract Syntax Tree (AST) to detect and log the presence of destructive wrapping in exception handling, which is an anti-pattern that results in losing the stack trace (which is extremely important for locating and fixing bugs).


Steps to run the project:
#1: Import as a maven project
#2: Add the directory path of the java project you want to analyze in the class "StaticAnalyzer" Line #29 
#3: The project will output a log file in the main directory named "logs.log"
