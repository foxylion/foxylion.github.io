
build: gwt-compile jekyll-build
	mkdir -p dist/
	rm -r dist/*
	cp CNAME dist/

jekyll-build:
	(cd jekyll && jekyll build --destination ../dist)

gwt-compile:
	(cd gwt-js && ./gradlew compileGwt)
	cp gwt-js/build/gwt/out/jj/jj.nocache.js jekyll/js/jj.js

serve: gwt-superdev jekyll-serve

jekyll-serve:
	(cd jekyll && jekyll serve --host=0.0.0.0 --force_polling)

gwt-superdev:
	cp gwt-js/src/main/resources/jj.js jekyll/js/jj.js
