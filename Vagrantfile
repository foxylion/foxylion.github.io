Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.network "forwarded_port", guest: 4000, host: 4000
  config.vm.provider "virtualbox" do |vb|
    vb.memory = 1024
    vb.cpus = 1
  end
  config.ssh.forward_agent = true
  config.vm.provision :shell, :inline => <<SHELL
    add-apt-repository ppa:brightbox/ruby-ng
    add-apt-repository ppa:openjdk-r/ppa
    apt-get update
    apt-get -y install build-essential ruby2.2 ruby2.2-dev zlib1g-dev openjdk-8-jdk
    gem install jekyll github-pages travis
    echo "cd /vagrant" > /home/vagrant/.bashrc
SHELL
end
