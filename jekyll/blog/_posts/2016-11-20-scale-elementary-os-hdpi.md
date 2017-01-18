---
layout: post
title:  "Scaling a 4K screen on Elementary OS 0.4 \"Loki\""
date:   2016-11-20 12:00:00 +0000
---

Elementary OS 0.4 "Loki" does support HDPi screens by default, but it is limited to double the scaling (1x, 2x, ...).
`xrandr` gives the ability to scale down the screen afterwards. This allows scaling support as seen in Windows or
MacOS (100%, 125%, 150%, ...).

<!--more-->

To get started configure Elementary OS to scale the screen up 2x. This can be done by executing the following in a terminal.


```bash
gsettings set org.gnome.desktop.interface scaling-factor 2
```

Now you can use `xrandr` to scale down the screen, in a multi screen environment this has to be done for each monitor.
It is also important to configure the position of the second screen correctly, otherwise there will be an overlap.

```bash
# List all connected screens
$ xrandr
DP-0 connected primary 3840x2160+0+0 (normal left inverted right x axis...
   3840x2160     60.00*+
   ...
DP-1 disconnected (normal left inverted right x axis y axis)
DP-2 connected 3840x2160+3840+0 (normal left inverted right x axis y...
   3840x2160     60.00*+
   ...
DP-3 disconnected (normal left inverted right x axis y axis)

# Now scale the screen as you would like (in this example 150%)
# Using "scale" will use a greater resolution for rendering and afterwards
# scale everything down to the native screen resolution
$ xrandr --output DP-0 --scale 1.5x1.5
$ xrandr --output DP-2 --pos 5760x0 --scale 1.5x1.5

# The "--pos" ensured that the screens don't overlap. Use" Screen-1_...
# In this example 3840*1.5=5760

# xrandr shows now the downscaled configuration
$ xrandr
DP-0 connected primary 5760x3240+0+0 (normal left inverted right x axis...
   3840x2160     60.00*+
   ...
DP-1 disconnected (normal left inverted right x axis y axis)
DP-2 connected 5760x3240+3840+0 (normal left inverted right x axis y...
   3840x2160     60.00*+
   ...
DP-3 disconnected (normal left inverted right x axis y axis)
```

