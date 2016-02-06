---
layout: post
title:  "Deploy Maven Atrifacts on Maven Central with Travis CI"
date:   2016-02-06 12:00:00 +0000
---

This blog post will show the steps required to deploy a built maven artifact on
Maven central using Travis CI.

<!--more-->

To deploy an artifact to maven artifact to Maven central we need a lot of
preparations before we can actually deploy an artifact.

---

#### 1. Create a Sonatype account

Sonatype provides free access to Maven central. With an account you can access
their Nexus and deploy your artifacts in their repository which is synced to
Maven central.

The [guide](http://central.sonatype.org/pages/ossrh-guide.html) about setting up
an "OSSRH" account can be found [here](http://central.sonatype.org/pages/ossrh-guide.html).

Basically the sign up process contains the following steps:

1. Create a JIRA account
2. Choose a Maven ``groupId``
3. Create a JIRA issue to gain access to the ``groupId``
4. Wait for approvement

---

#### 2. Create a GPG key

Ensure that you're using a GPG v1 executable (you can check that with ``gpg --version``).
This is required because the GPG binary on Travis CI does not support the encrypted keys of GPG v2.

{% highlight bash %}
# Generate a new key, save passphrase in a secure location
gpg --gen-key

# List the keys and save the new key id
gpg --list-keys

# Export the private key
gpg -a --export-secret-key <Key-Id> > private-key.gpg

# Publish the key to the keyservers
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys <Key-Id>
{% endhighlight %}

Add the private key to your Git repository. No fear, it is encrypted with your
passphrase, no one can decrypt it.

---

#### 3. Configure your Maven build

To successfully deploy a Maven artifact you have to meet the following constraints:

##### Add required information to the ``pom.xml``

A Maven artifact has to provide serveral informations to be allowed for deploy in
the central repository. You can find those details
[here](http://central.sonatype.org/pages/requirements.html).

##### Add a release profile to the ``pom.xml``

Add the following lines to your ``pom.xml`` of the to be deployed artifact.
It is required to generate a source jar, a javadoc jar and sign all jar files.
The fourth plugin is used to automatically deploy to the Sonatype Nexus Repository.

{% highlight xml %}
<profiles>
  <profile>
    <id>release</id>
    <build>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-source-plugin</artifactId>
          <version>2.2.1</version>
          <executions>
            <execution>
              <id>attach-sources</id>
              <goals>
                <goal>jar-no-fork</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-javadoc-plugin</artifactId>
          <version>2.9.1</version>
          <executions>
            <execution>
              <id>attach-javadocs</id>
              <goals>
                <goal>jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-gpg-plugin</artifactId>
          <version>1.5</version>
          <executions>
            <execution>
              <id>sign-artifacts</id>
              <phase>verify</phase>
              <goals>
                <goal>sign</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        <plugin>
          <groupId>org.sonatype.plugins</groupId>
          <artifactId>nexus-staging-maven-plugin</artifactId>
          <version>1.6.3</version>
          <extensions>true</extensions>
          <configuration>
            <serverId>ossrh</serverId>
            <nexusUrl>https://oss.sonatype.org/</nexusUrl>
            <autoReleaseAfterClose>true</autoReleaseAfterClose>
          </configuration>
        </plugin>
      </plugins>
    </build>
  </profile>
</profiles>
{% endhighlight %}

##### Create a Maven ``settings.xml``

We have to use a ``settings.xml`` to provide Maven the required Sonatype user and
password and the passphrase for you gpg key.

{% highlight xml %}
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${env.MAVEN_REPO_USERNAME}</username>
      <password>${env.MAVEN_REPO_PASSWORD}</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>release</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <gpg.keyname>**YOU GPG KEY ID**</gpg.keyname>
        <gpg.passphrase>${env.MAVEN_GPG_PASSPHRASE}</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
{% endhighlight %}

Add it to your Git repository.

---

#### 4. Setup the Travis build

##### Encrypt your Sonatype credentials and gpg key passphrase

You must use the [Travis CLI](https://github.com/travis-ci/travis.rb#installation)
to encrypt your credentials required to authenticate and sign your build.

{% highlight bash %}
travis encrypt MAVEN_REPO_USERNAME=**YOUR SONATYPE USERNAME**
travis encrypt MAVEN_REPO_PASSWORD=**YOUR SONATYPE PASSWORD**
travis encrypt MAVEN_GPG_PASSPHRASE=**YOUR GPG PASSPHRASE**
{% endhighlight %}

The resulting encrypted strings you've to add in the next step.

##### Add a ``.travis.yml`` file to your git repository

This file is used for travis to build and deploy your project.

{% highlight yaml %}
language: java
jdk: oraclejdk8

env:
  global:
  - secure: ...
  - secure: ...
  - secure: ...

script: make build

deploy:
  provider: script
  script: make travis-deploy
  on:
    tags: true
{% endhighlight %}

##### Add a Makefile to build your Maven artifact

You have to change the paths to your gpg private-key and your Maven ``settings.xml``.

{% highlight makefile %}
build:
	mvn install

travis-deploy:
	gpg --import path/to/private-key.gpg
	mvn versions:set -DnewVersion=${TRAVIS_TAG}
	mvn clean deploy -P release --settings /path/to/settings.xml
{% endhighlight %}

---

#### 5. Test the build process

When you push something or a pull request is created the project is automatically
built using ``make build``. When you create a tag for the repository it will
automatically deploy an artifact to the Naven central repository with the version
set to the tag name.
