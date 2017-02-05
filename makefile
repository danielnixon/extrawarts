all:
	sbt "such test"

dist:
	sbt "so clean" "such test" "very publishSigned"

.PHONY: all dist
