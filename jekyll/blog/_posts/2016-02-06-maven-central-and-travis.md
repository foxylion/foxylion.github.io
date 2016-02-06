---
layout: post
title:  "Deploy Maven Atrifacts on Maven Central with Travis CI"
date:   2016-02-06 12:00:00 +0000
---

This blog post will show the steps required to deploy a built maven artifact on Maven Central using Travis CI.

<!--more-->

To deploy an artifact to maven artifact to Maven Central we need a lot of preparations before we can actually deploy an artifact.

#### 1. Create a Sonatype account

Sonatype provides a free access to Maven Central. With an account you can access their Nexus and deploy your artifacts in their Repository which is synced to Maven Central.

The [guide](http://central.sonatype.org/pages/ossrh-guide.html) about setting up an "OSSRH" account can be found [here](http://central.sonatype.org/pages/ossrh-guide.html).

Basically the sign up process contains the following steps:

1. Create a JIRA account
2. Choose a Maven ``groupId``
3. Create a JIRA issue to gain access to the ``groupId``
4. 