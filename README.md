# Source for www.jakobjarosch.de

The websites uses Jekyll for static html file generation and GWT 2.8 to create the used Javascript.

[![Build Status](https://travis-ci.org/foxylion/foxylion.github.io.svg?branch=develop)](https://travis-ci.org/foxylion/foxylion.github.io)

## Setup you development environment

To run your development environment you require Vagrant and VirtualBox.

```bash
vagrant up
vagrant ssh
```

## Modify Jekyll files

When you only want to modify the Jekyll files you can use precompiled Javascript source.

```bash
make gwt-compile
make jekyll-serve
```

## Modify GWT files

When modifing the used Javascript you have to use the GWT SuperDevMode. Therefore run the following commands.

```bash
make gwt-superdev
make jekyll-serve
```

Now you can run the GWT SuperDevMode server inside your lcoal Eclipse.
