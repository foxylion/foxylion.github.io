Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.network "forwarded_port", guest: 4000, host: 4000
  config.vm.provider "virtualbox" do |vb|
    vb.memory = 1024
    vb.cpus = 1
  end
  
  config.vm.provision :shell, :inline => "sudo add-apt-repository ppa:brightbox/ruby-ng && sudo apt-get update && sudo apt-get -y install build-essential ruby2.1 ruby2.1-dev zlib1g-dev"
  config.vm.provision :shell, :inline => "sudo gem install jekyll github-pages travis"
  config.vm.provision :shell, :inline => "sudo add-apt-repository ppa:openjdk-r/ppa && sudo apt-get update && sudo apt-get install -y openjdk-8-jdk"
  config.vm.provision :shell, :inline => "echo \"cd /vagrant\" > /home/vagrant/.bashrc"

  config.ssh.forward_agent = true
end
