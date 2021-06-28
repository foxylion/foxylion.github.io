---
layout: post
title: "Parallel sign-in into multiple AWS accounts"
date: 2021-06-28 19:49:00 +0000
---

When working extensively with AWS federated sign-in you may often face the issue that your workload
is split across multiple accounts. This will require you to switch forth and back between accounts.

In a single browser windows (with a shared cookie storage) it will automatically log you out of
other existing AWS console sessions when starting the sign-in to a new account.

The following guide will give you the ability to sign into multiple AWS accounts at the same time
without having to switch browsers, using inkognito mode or relying on multiple browser profiles.
It is built upon Firefox and the multi account container extension.

<!--more-->

![image](/assets/blog-posts/2021-06-28-aws-multi-account-in-browser/aws-auto-sign-out.png)

Who hasn't seen the annoying screen that you've been logged out from the existing session because
another tab changed the credentials? This will soon be a faint memory.

**Disclaimer:** This works especially good if you're using an SSO mechanism that is relying on
[SAML based federation](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles_providers_enable-console-saml.html).
For IAM user sign-in and switch role workflows it might not work that good. You will still be able
to isolate different sign-ins but a switch role through the UI will not create another container.
The reason for this is that required cookies need to persist when switching the role,

### How the AWS sign-in works

There are some different domain patterns used when interacting with the console:

- `signin.aws.amazon.com` used to authenticate the user and pass the credentials to the console (via OAuth)
- `*.console.aws.amazon.com` used to access the console (when having valid credentials)
- `mycompany.awsapps.com` the domain of the identity provider when using (AWS) SSO (Azure, Octa, PingOne and others use different URLs, but it all works the same way)

A normal sign-in is done by having an oauth flow between `signin.aws.amazon.com` and `console.aws.amazon.com`.
Both sides use cookies to validate the authenticity of the other side.

When using AWS SSO or another third party identity provider a SAML assertion will be passed to
start the normal sign-in process. The assertion itself works like the username/password that is
entered during the normal sign-in.

### How "Multi Account Containers" helps

The "Multi Account Containers" extension will allow you to isolate tabs into a container where they
are isolated from other tabs. This means they have their own cookie/local storage (and more).

In each container you're able to sign into a different account. This itself does not really give you
a great advantage over having multiple browser profiles. But in combination with the "Temporary Containers"
extension it will begin to shine.

The "Temporary Containers" extension is able to dynamically create a new container based on
conditions like clicking a button on the mouse, or navigating to a different domain. This
will allow us to configure the containers in such away that it automatically creates a new
container for each AWS console session we initiate.

### Install Firefox and the extensions

1. Install a recent version of [Firefox](https://www.mozilla.org/en-US/firefox/new/).
2. Install the extension [Multi Account Containers](https://addons.mozilla.org/en-US/firefox/addon/multi-account-containers/).
3. Install the extension [Temporary Containers](https://addons.mozilla.org/de/firefox/addon/temporary-containers/).

### Configure the Temporary Containers extension
