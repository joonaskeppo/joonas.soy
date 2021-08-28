GOALS := clean fetch-latest-build prod dev dev/css dev/html all deploy commit-and-push watch

.PHONY: $(GOALS)
.SILENT: $(GOALS)

.DEFAULT_GOAL := all
SHELL := /bin/bash
REPO = git@github.com:joonaskeppo/joonas.soy.git

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
		git clone -b build ${REPO} build; \
	fi

commit-and-push:
	cd build; \
	git add -A; \
	git commit -m "Deployed from script"; \
	git push -u origin build; \
	cd ..;

prod:
	clj -X:prod;

dev:
	clj -X:dev/all;

dev/css:
	NODE_ENV=development npx tailwind -o build/styles.css;

dev/html:
	clj -X:dev/html;

watch:
	fswatch -o src | xargs -n1 -I{} make dev/html;

all: clean prod

deploy: fetch-latest-build clean prod commit-and-push
