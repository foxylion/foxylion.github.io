set -ve

GIT_COMMIT=$(git rev-parse HEAD)

cd dist/
git init
git config user.name "Travis CI"
git config user.email "dev@jakobjarosch.de"
git add .
git commit -m "Deploy GitHub page (${GIT_COMMIT})"
git push --force "https://${GH_TOKEN}@github.com/foxylion/foxylion.github.io.git" master:master
