---
layout: post
title:  "Using Docker volume mounts on Windows host inside WSL"
date:   2017-04-17 11:00:00 +0000
---

Windows Subsystem for Linux does not natively support Docker so the user has
to use the Docker host of Windows itself.
Docker can run on Windows using [Docker for Windows (Hyper V)](https://docs.docker.com/engine/installation/windows/)
or [Docker Toolbox (VirtualBox)](https://www.docker.com/products/docker-toolbox).

<!--more-->

In this tutorial we will use Docker for Windows or Docker Toolbox to get a
running Docker instance on our machine.

### 1. Installation

This tutorial is sometimes divided into two sections one for Docker for Windows
one for Docker Toolbox. I suggest you to try the Docker for Windows variant.
Use Docker Toolbox only if you really need to (e.g. no Hyper-V available).

##### Option 1: Docker for Windows

The installation manual can be found [here](https://docs.docker.com/docker-for-windows/install/).

After the installation you can already use Docker on your Windows host (e.g. in PowerShell).
To enable sharing of volumes you've to enable "Shared Drives" in the settings of Docker for Windows. (`Right click on the tray icon -> Settings -> Shared Drives`)

##### Option 2: Docker Toolbox

The installation manual can be found [here](https://www.docker.com/products/docker-toolbox).

After the installation of Docker Toolbox you are able to call the commands `docker` and `docker-machine` on your PowerShell/CMD.

### 2. Install Docker in WSL

You've to install Docker on the WSL. This is quite easy and documented in the
[official docs](https://docs.docker.com/engine/installation/linux/ubuntulinux/).
After installation `docker info` should result in an error. This is okay, the next
step will be used to configure it correctly.

### 3. Configure Docker in WSL

After completing this step you should be able to execute `docker info` successfully
in a new shell.

##### Option 1: Docker for Windows

Add the following to your `.bashrc`:

{% highlight shell %}
export DOCKER_HOST="tcp://127.0.0.1:2376"
{% endhighlight %}

##### Option 2: Docker Toolbox

Get the IP of your Docker machine using `docker-machine.exe ip` and add the
following to your `.bashrc`:

{% highlight shell %}
export DOCKER_TLS_VERIFY="0"
export DOCKER_HOST="tcp://**IP-RETURNED-BY-DOCKER-MACHINE**:2376"
{% endhighlight %}

### 4. Allow volume mounting from WSL

Basically everything is already set up to mount any volume inside `/mnt/c/Users`.
But passing this path will result in an error because the Docker host is not able
to identify the directory correctly.

Docker for Windows/Docker Machine is mounting `C:\Users\` of your Windows
to `//c` on the Docker host. This requires us to modify the path when we want
to mount any path directly from WSL.

We'll use a bash script which overrides the `docker` command globally inside of
WSL. Inside the bash script we are able to tweak the arguments passed to docker.
We will need this to modify the passed volume paths.

{% highlight shell %}
sudo touch /usr/local/bin/docker
sudo chmod +x /usr/local/bin/docker
cat EOF > /usr/local/bin/docker
#!/bin/bash
ARGS=`echo -n "$@" | sed -E 's/\/mnt\/([a-z])\//\/\/\1\//g'`
eval /usr/bin/docker $ARGS
EOF
{% endhighlight %}

Now you are able to run commands like ``docker run -v `pwd`:/var/www/html php:7.1``
inside your WSL.

I hope this will help you to get started with Docker inside your WSL.
