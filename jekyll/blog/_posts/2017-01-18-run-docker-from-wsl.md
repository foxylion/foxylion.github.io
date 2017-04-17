---
layout: post
title:  "Using docker volume mounts on Windows host inside WSL"
date:   2017-01-18 12:00:00 +0000
---

Windows Subsystem for Linux does not natively support docker so the user has to use the docker host of Windows itself.
Docker can run on Windows using [Docker for Windows (Hyper V)](https://docs.docker.com/engine/installation/windows/)
or [Docker Toolbox (VirtualBox)](https://www.docker.com/products/docker-toolbox).

<!--more-->

In this tutorial we will use Docker Toolbox to get a running Docker instance on our machine. This tutorial may also work
using Docker for Windows (Hyper V) but is wasn't tested.

### 1. Install Docker Toolbox

The installation manual can be found [here](https://www.docker.com/products/docker-toolbox).

After the installation of Docker Toolbox you are able to call the commands `docker` and `docker-machine` on your PowerShell/CMD.
With `docker-machine env --shell bash` you'll get the command required to configure the docker client inside of WSL.

### 2. Install docker client in WSL

You've to install docker on the WSL. This is quite easy and documented in the
[official docs](https://docs.docker.com/engine/installation/linux/ubuntulinux/).
After installation `docker info` should result in an error. This is okay, the next
step will be used to configure it correctly.

### 3. Create a bash script

We'll use a bash script which overrides the `docker` command globally inside of WSL.
Inside the bash script we are able to tweak the arguments passed to docker (we will need that later).
But first of all we need to configure docker to communicate with the Docker on our Windows host.

{% highlight shell %}
sudo touch /usr/local/bin/docker
sudo chmod +x /usr/local/bin/docker
cat EOF > /usr/local/bin/docker
#!/bin/bash

export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://**IP-RETURNED-BY-DOCKER-MACHINE**:2376"
export DOCKER_CERT_PATH="/mnt/c/Users/$USER/.docker/machine/machines/default"
export DOCKER_MACHINE_NAME="default"

eval /usr/bin/docker $@
EOF
{% endhighlight %}

This script will now set all required environment variables before running the docker command.

### 4. Allow volume mounting from WSL

Bascially everyting is already set up to mount any volume inside `/mnt/c/Users`. But passing this path will result in an
error because the Docker host is not able to identify this directory correctly.

Docker Machine is mounting `C:\Users\` of your Windows to `//c` on the Docker host. This requires us to modify the path
when we want to mount any path directly from WSL.

When you replace the last line in `/usr/local/bin/docker` with the following code snippet this will be done automtically
and a command like ```docker run -v `pwd`:/tmp busybox /bin/sh``` will work and mount the folder correctly. as long as you
are currently under `/mnt/[a-z]/*`.

{% highlight shell %}
CMD=`echo -n "$@" | sed -E 's/\/mnt\/([a-z])\//\/\/\1\//g'`
eval /usr/bin/docker $CMD
{% endhighlight %}

I hope this will help you to get started with Docker inside your WSL.
