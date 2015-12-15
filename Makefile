# Serve commands
serve: gwt-superdev jekyll-serve

jekyll-serve:
	(cd jekyll && jekyll serve --host=0.0.0.0 --force_polling)

gwt-superdev:
	cp gwt-js/src/main/resources/jj-superdev.js jekyll/js/jj.js


# Build commands
build: clean-dist gwt-compile jekyll-build
	cp CNAME dist/

clean-dist:
	rm -rf dist/*

jekyll-build:
	(cd jekyll && jekyll build --destination ../dist)

gwt-compile:
	(cd gwt-js && ./gradlew compileGwt)
	cp gwt-js/build/gwt/out/jj/jj.nocache.js jekyll/js/jj.js

deploy:
	./deploy.sh
