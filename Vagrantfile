Vagrant::Config.run do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.forward_port 4000, 4000
  config.vm.provision :shell, :inline => "sudo add-apt-repository ppa:brightbox/ruby-ng && sudo apt-get update && sudo apt-get -y install build-essential ruby2.1 ruby2.1-dev zlib1g-dev"
  config.vm.provision :shell, :inline => "sudo gem install jekyll github-pages"
  config.vm.provision :shell, :inline => "echo \"cd /vagrant\" > /home/vagrant/.bashrc"
  config.ssh.forward_agent = true
end
