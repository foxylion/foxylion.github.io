# Source for www.jakob.soy

[![Build Status](https://img.shields.io/travis/foxylion/foxylion.github.io/develop.svg?style=flat-square)](https://travis-ci.org/foxylion/foxylion.github.io)
![License](https://img.shields.io/badge/license-custom-blue.svg?style=flat-square)
![Maintenance](https://img.shields.io/maintenance/yes/2016.svg?style=flat-square)

The websites uses Jekyll for static html file generation and GWT 2.8 to create the used Javascript.

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
