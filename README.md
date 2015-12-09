# Jekyll with Vagrant quickstart

## System prerequisites

Installed [Vagrant](https://www.vagrantup.com/downloads.html) and [VirtualBox](https://www.virtualbox.org/wiki/Downloads) on your machine.

## Setup new Jekyll blog

```
vagrant up
vagrant ssh
make create
```

## Preview your Jekyll blog locally

```
vagrant up
vagrant ssh
make serve
```

Access your blog at [127.0.0.1:4000/](http://127.0.0.1:4000/).
Changes are automatically reflected on the page.
For changes on `_config.yml` you need to stop `make serve` (Ctrl-C) and restart it.