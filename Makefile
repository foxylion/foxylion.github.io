
jekyll-serve:
	docker run --rm -it -p 4000:4000 -v `pwd`/jekyll:/srv/jekyll jekyll/jekyll:pages jekyll serve --force_polling

travis-build: clean-dist jekyll-build
	cp CNAME dist/

clean-dist:
	rm -rf dist/*

jekyll-build:
	(cd jekyll && jekyll build --destination ../dist)

travis-deploy:
	./deploy.sh
