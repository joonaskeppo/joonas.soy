GOALS := clean fetch-latest-build generate all deploy commit-and-push

.PHONY: $(GOALS)
.SILENT: $(GOALS)

.DEFAULT_GOAL := all
SHELL := /bin/bash 
REPO = https://github.com/joonaskeppo/joonas.soy.git

clean:
	mkdir -p build; \
	if [ -d build/.git ]; then \
		mkdir -p build-temp; \
		mv build/.git build-temp/; \
		rm -rf build/*; \
		mv build-temp/.git build/; \
		rm -rf build-temp; \
	else \
		rm -rf build/*; \
	fi

fetch-latest-build:
	if [ -d build/.git ]; then \
		cd build && git pull && cd ..; \
	else \
		git clone ${REPO} build build; \
	fi

commit-and-push:
	HASH=${git rev-parse HEAD}; \
	cd build; \
	git add -A; \
	git commit -m "Build from commit ${HASH}"; \
	git push -u origin build; \
	cd ..;

generate:
	clj -X:generate;

all: clean generate

deploy: fetch-latest-build clean generate commit-and-push
