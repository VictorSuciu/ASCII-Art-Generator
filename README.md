# ASCII-Art-Generator

## Overview

This is a small application that converts image files into ASCII text.
It has a few UI features that allow for more powerful and customizable
ASCII art creation than many other tools and web apps.

## Features

#### Versatile Width/Height Adjustment 
You can adjust the width and height of the generated ASCII art 
in any way imaginable given the width and height input boxes. 
- Only input one of the two dimensions, such as only width. The
generator will automatically set the unspecified dimension to keep
the image proportional.

- Enter both width and height dimensions as any value. The generator
will stretch, shrink, compress, or warp the ASCII image to fit your
specifications.

-----

#### Target Character Limit Sizing Option

Alternatively, you can enter a 'target character number' to specify
the size of the ASCII art. This option takes a number (such as 5000),
and generates an ASCII image that contains as close to that number
of characters as possible. It always rounds down, so the ASCII image
will never have more characters than the target number. The image
 dimensions will remain proportional to the original image file.

This feature is useful for text messaging services that have a maximum
character limit, such as Discord with a max character limit of 2000.

-----

#### Positive and Negative Control

With this application, you can choose whether to generate your artwork
 on a light or dark background. The generator will draw a positive image 
on the light background, and a negative image on a dark background. This
allows you to retain the original lights and darks of the image no matter
what background color your artwork will be displayed on.

-----

#### Zooming

The UI allows you to zoom in and out on your artwork to adjust for size
within the viewing window.

-----

## Showcase and Examples

![Alt text](https://github.com/VictorSuciu/README-Assets/blob/master/ASCII/ImpossibleTriangle.png "Original Image")

> Original Image

<br/>

![Alt text](https://github.com/VictorSuciu/README-Assets/blob/master/ASCII/ASCII-App-Triangle.png "ASCII Art Inside Application")

> ASCII image generated from the original image file

-----

<br/>

![Alt text](https://github.com/VictorSuciu/README-Assets/blob/master/ASCII/DistortedEmoji-After.jpg "Original Image")

> Original Image

<br/>

![Alt text](https://github.com/VictorSuciu/README-Assets/blob/master/ASCII/ASCII-App-Smiley-Light.png "Positive Background")

> ASCII image on a positive (light) background

<br/>

![Alt text](https://github.com/VictorSuciu/README-Assets/blob/master/ASCII/ASCII-App-Smiley-Dark.png "Negative Background")

> ASCII image on a negative (dark) background