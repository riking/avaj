

default:
	find . -name *.java > sources.txt
	javac -source 1.7 -target 1.7 -sourcepath . @sources.txt


