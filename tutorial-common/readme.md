About the tutorial-common folder
================================

This folder contains resources that are used to convert the tutorial markdown into HTML. They don't have anything to do with learning about Alfresco development, so if that is your focus, ignore this directory completely.

The latest versions of the tutorials already exist as html on [ECM Architect](http://ecmarchitect.com/alfresco-developer-series).

If you want to generate the HTML yourself keep reading.

Pre-requisites
==============

You'll need to install Pandoc. Instructions are [here](http://johnmacfarlane.net/pandoc/installing.html).

Steps
=====

Assuming you have checked out the entire alfresco-developer-series project from GitHub, you should just be able to cd to the directory containing the markdown you want to transform, and then run:

    ../../tutorial-common/gen.sh

That will create an HTML file in the current directory.

If you want to alter the header, footer, CSS, or HTML page template, those all live in tutorial-common.