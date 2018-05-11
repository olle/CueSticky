.PHONY: run

run:
	@mvn clean package
	@java -jar target/CueSticky.jar
